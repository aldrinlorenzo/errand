package com.errand.services.impl;

import com.errand.dto.ClientDto;
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
    public Optional<Client> findById(Long id) {
        try {
            return clientRepository.findById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public List<ClientDto> findByFirstNameIgnoreCase(String firstName) {
        List<Client> clients = clientRepository.findByFirstNameIgnoreCase(firstName);
        return clients.stream().map((client) -> mapToClientDto(client)).collect(Collectors.toList());
    }

    @Override
    public List<ClientDto> findByLastNameIgnoreCase(String lastName) {
        List<Client> clients = clientRepository.findByLastNameIgnoreCase(lastName);
        return clients.stream().map((client) -> mapToClientDto(client)).collect(Collectors.toList());
    }

    @Override
    public List<ClientDto> findAllClients() {
        List<Client> clients = clientRepository.findAll();
        return clients.stream().map((client) -> mapToClientDto(client)).collect(Collectors.toList());
    }

    @Override
    public Client getCurrentClient() {
        Client client = new Client();
        String username = SecurityUtil.getSessionUser();
        if(username != null){
            Users user = userRepository.findFirstByUsername(username);
            Optional<Client> optionalClient = clientRepository.findById(user.getId());
            client = optionalClient.orElseThrow(() -> new RuntimeException("Client not found"));

        }
        return client;
    }

    @Override
    public void updateClient(ClientDto clientDto) {
        Client client = mapToClient(clientDto);
        client.setId(getCurrentClient().getId());
        client.setUser(getCurrentClient().getUser());
        clientRepository.save(client);
    }

}
