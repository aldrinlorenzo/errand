package com.errand.services.impl;

import com.errand.dto.ClientDto;
import com.errand.models.Client;
import com.errand.repository.ClientRepository;
import com.errand.repository.UserRepository;
import org.junit.After;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

class ClientServiceImplTest {

    @Mock
    private ClientRepository mockClientRepository;

    @Mock
    private UserRepository mockUserRepository;

    private ClientServiceImpl clientService;

    @BeforeEach
    void setUp() {
        mockClientRepository = mock(ClientRepository.class);
        mockUserRepository = mock(UserRepository.class);
        clientService = new ClientServiceImpl(mockClientRepository, mockUserRepository);
    }

    @After
    void tearDown() {
    }

    @Test
    void testFindById() {
        Long clientId = 1L;
        Client expectedClient = Client.builder()
                .id(clientId)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .contactNumber("091234567890")
                .build();

        when(mockClientRepository.findById(clientId)).thenReturn(Optional.of(expectedClient));

        Optional<Client> result = clientService.findById(clientId);

        assertTrue(result.isPresent());
        assertEquals(expectedClient, result.get());
        verify(mockClientRepository, times(1)).findById(clientId);
    }

    @Test
    void findByFirstNameIgnoreCase() {

        String firstName = "John";
        Client client1 = Client.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .contactNumber("1234567890")
                .build();
        Client client2 = Client.builder()
                .id(2L)
                .firstName("John")
                .lastName("Smith")
                .email("john.smith@example.com")
                .contactNumber("9876543210")
                .build();
        List<Client> clients = new ArrayList<>();
        clients.add(client1);
        clients.add(client2);

        when(mockClientRepository.findByFirstNameIgnoreCase(firstName)).thenReturn(clients);

        List<ClientDto> result = clientService.findByFirstNameIgnoreCase(firstName);

        assertEquals(2, result.size());
        assertEquals(client1.getFirstName(), result.get(0).getFirstName());
        assertEquals(client1.getLastName(), result.get(0).getLastName());
        assertEquals(client1.getEmail(), result.get(0).getEmail());
        assertEquals(client1.getContactNumber(), result.get(0).getContactNumber());
        assertEquals(client2.getFirstName(), result.get(1).getFirstName());
        assertEquals(client2.getLastName(), result.get(1).getLastName());
        assertEquals(client2.getEmail(), result.get(1).getEmail());
        assertEquals(client2.getContactNumber(), result.get(1).getContactNumber());
        verify(mockClientRepository, times(1)).findByFirstNameIgnoreCase(firstName);
    }

    @Test
    void findByLastNameIgnoreCase() {
    }

    @Test
    void findAllClients() {
    }

    @Test
    void getCurrentClient() {
    }

    @Test
    void updateClient() {
    }
}