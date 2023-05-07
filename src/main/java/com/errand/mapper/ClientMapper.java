package com.errand.mapper;

import com.errand.dto.ClientDto;
import com.errand.models.Client;

public class ClientMapper {

    public static Client mapToClient(ClientDto client){
        Client clientDto = Client.builder()
                .id(client.getId())
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .email(client.getEmail())
                .contactNumber(client.getContactNumber())
                .user(client.getUser())
                .build();
        return clientDto;
    }

    public static ClientDto mapToClientDto(Client client){
        ClientDto clientDto = ClientDto.builder()
                .id(client.getId())
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .email(client.getEmail())
                .contactNumber(client.getContactNumber())
                .user(client.getUser())
                .build();
        return clientDto;
    }

}
