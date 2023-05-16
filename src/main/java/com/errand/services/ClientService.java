package com.errand.services;

import com.errand.dto.ClientDto;
import com.errand.exceptions.ClientNotFoundException;
import com.errand.exceptions.UserNotFoundException;
import com.errand.models.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {

    Optional<Client> findById(Long id) throws ClientNotFoundException;

    List<ClientDto> findByFirstNameIgnoreCase(String firstName) throws ClientNotFoundException;

    List<ClientDto> findByLastNameIgnoreCase(String lastName) throws ClientNotFoundException;

    List<ClientDto> findAllClients() throws ClientNotFoundException, UserNotFoundException;

    Client getCurrentClient();

    void updateClient(ClientDto clientDto);

}
