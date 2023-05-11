package com.errand.services;

import com.errand.dto.ClientDto;
import com.errand.models.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {

    Optional<Client> findById(Long id);

    List<ClientDto> findByFirstNameIgnoreCase(String firstName);

    List<ClientDto> findByLastNameIgnoreCase(String lastName);

    List<ClientDto> findAllClients();

    Client getCurrentClient();

    void updateClient(ClientDto clientDto);

}
