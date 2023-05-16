package com.errand.services.impl;

import com.errand.dto.ClientDto;
import com.errand.exceptions.ClientNotFoundException;
import com.errand.models.Client;
import com.errand.models.Users;
import com.errand.repository.ClientRepository;
import com.errand.repository.UserRepository;
import com.errand.security.SecurityUtil;
import com.errand.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.errand.mapper.ClientMapper.mapToClient;
import static com.errand.mapper.ClientMapper.mapToClientDto;

@Service
public class ClientServiceImpl implements ClientService {

    private ClientRepository clientRepository;
    private UserRepository userRepository;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, UserRepository userRepository) {
        this.clientRepository = clientRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Optional<Client> findById(Long id) {
        try {
            return clientRepository.findById(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ClientNotFoundException("Client not found with ID: " + id, e);
        }
    }

    @Override
    public List<ClientDto> findByFirstNameIgnoreCase(String firstName) {
        try {
            List<Client> clients = clientRepository.findByFirstNameIgnoreCase(firstName);

            if (clients.isEmpty()) {
                throw new ClientNotFoundException("No clients found with first name: " + firstName);
            }

            return clients.stream().map((client) -> mapToClientDto(client)).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public List<ClientDto> findByLastNameIgnoreCase(String lastName) {
        try {
            List<Client> clients = clientRepository.findByLastNameIgnoreCase(lastName);

            if (clients.isEmpty()) {
                throw new ClientNotFoundException("No clients found with last name: " + lastName);
            }

            return clients.stream().map((client) -> mapToClientDto(client)).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public List<ClientDto> findAllClients() {
        try {
            List<Client> clients = clientRepository.findAll();

            if (clients.isEmpty()) {
                throw new ClientNotFoundException("No clients found.");
            }

            return clients.stream().map((client) -> mapToClientDto(client)).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();

            return Collections.emptyList();
        }
    }

    @Override
    public Client getCurrentClient() {
        try {
            Client client = new Client();
            String username = SecurityUtil.getSessionUser();

            if (username != null) {
                Users user = userRepository.findFirstByUsername(username);

                if (user != null) {
                    Optional<Client> optionalClient = clientRepository.findById(user.getId());
                    client = optionalClient.orElseThrow(() -> new ClientNotFoundException("Client not found"));
                } else {
                    throw new RuntimeException("User not found");
                }
            }

            return client;
        } catch (Exception e) {
            e.printStackTrace();
            return new Client();
        }
    }

    @Override
    public void updateClient(ClientDto clientDto) {
        try {
            Client client = mapToClient(clientDto);
            client.setId(getCurrentClient().getId());
            client.setUser(getCurrentClient().getUser());
            clientRepository.save(client);
        } catch (Exception e) {
            e.printStackTrace();

            throw new RuntimeException("Failed to update client. Please try again later.");
        }
    }

}
