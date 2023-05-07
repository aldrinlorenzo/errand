package com.errand.services;

import com.errand.dto.ClientDto;
import com.errand.models.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {

    Optional<Client> findById(Long id);

    Client findByFirstName(String firstName);

    Client findByLastName(String lastName);

    List<ClientDto> findAllClients();

}
