package com.errand.services.impl;

import com.errand.dto.ClientDto;
import com.errand.exceptions.ClientNotFoundException;
import com.errand.exceptions.UserNotFoundException;
import com.errand.models.Client;
import com.errand.models.Users;
import com.errand.repository.ClientRepository;
import com.errand.repository.UserRepository;
import com.errand.security.SecurityUtil;
import com.errand.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Optional<Client> findById(Long id) throws ClientNotFoundException {

        try {
            return clientRepository.findById(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ClientNotFoundException("Client not found with ID: " + id, e);
        }
    }

    @Override
    public List<ClientDto> findByFirstNameIgnoreCase(String firstName) throws ClientNotFoundException {

        List<Client> clients = clientRepository.findByFirstNameIgnoreCase(firstName);

        if (clients.isEmpty()) {
            throw new ClientNotFoundException("No clients found with first name: " + firstName);
        }

        return clients.stream().map((client) -> mapToClientDto(client)).collect(Collectors.toList());
    }

    @Override
    public List<ClientDto> findByLastNameIgnoreCase(String lastName) throws ClientNotFoundException {

        List<Client> clients = clientRepository.findByLastNameIgnoreCase(lastName);

        if (clients.isEmpty()) {
            throw new ClientNotFoundException("No clients found with last name: " + lastName);
        }

        return clients.stream().map((client) -> mapToClientDto(client)).collect(Collectors.toList());
    }

    @Override
    public List<ClientDto> findAllClients() throws ClientNotFoundException {

        List<Client> clients = clientRepository.findAll();

        if (clients.isEmpty()) {
            throw new ClientNotFoundException("No clients found.");
        }

        return clients.stream().map(client -> mapToClientDto(client)).collect(Collectors.toList());
    }

    @Override
    public Client getCurrentClient() throws ClientNotFoundException, UserNotFoundException{

        String username = SecurityUtil.getSessionUser();

        if (username != null) {
            Users user = userRepository.findFirstByUsername(username);

            if (user != null) {
                Optional<Client> optionalClient = clientRepository.findById(user.getId());
                return optionalClient.orElseThrow(() -> new ClientNotFoundException("Client not found"));
            } else {
                throw new UserNotFoundException("User not found");
            }
        }

        throw new UserNotFoundException("No logged-in user");
    }

    @Override
    public void updateClient(ClientDto clientDto) {

        try {
            Client client = mapToClient(clientDto);
            client.setId(getCurrentClient().getId());
            client.setUser(getCurrentClient().getUser());
            client.setProfileImageFileName(getCurrentClient().getProfileImageFileName());
            clientRepository.save(client);
        } catch (Exception e) {
            e.printStackTrace();

            throw new RuntimeException("Failed to update client. Please try again later.");
        }
    }

    @Override
    public void updateClientImage(Client client) {

        try {
            client.setId(getCurrentClient().getId());
            client.setUser(getCurrentClient().getUser());
            clientRepository.save(client);
        } catch (Exception e) {
            e.printStackTrace();

            throw new RuntimeException("Failed to update client. Please try again later.");
        }
    }

}
