package com.errand.services;

import com.errand.dto.ClientDto;
import com.errand.models.Client;

import java.util.List;

public interface ClientService {

    Client findByFirstName(String firstName);

    Client findByLastName(String lastName);

    List<ClientDto> findAllClients();



}
