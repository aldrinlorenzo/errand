package com.errand.services.impl;

import com.errand.dto.ClientDto;
import com.errand.exceptions.ClientNotFoundException;
import com.errand.exceptions.UserNotFoundException;
import com.errand.mapper.ServiceProviderMapper;
import com.errand.models.Client;
import com.errand.models.Users;
import com.errand.repository.ClientRepository;
import com.errand.repository.UserRepository;
import com.errand.security.SecurityUtil;
import org.junit.After;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;

import java.util.*;

import static com.errand.mapper.ClientMapper.mapToClient;
import static org.junit.jupiter.api.Assertions.*;
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

        Client client1 = createClient(1L,
                "John",
                "Doe",
                "john.doe@example.com",
                "09123456789");

        when(mockClientRepository.findById(1L)).thenReturn(Optional.of(client1));

        Optional<Client> result = clientService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(client1, result.get());
        verify(mockClientRepository, times(1)).findById(1L);

    }

    @Test
    void findByFirstNameIgnoreCase_WithExistingClients_ShouldReturnClientDtoList() {

        String firstName = "John" ;
        Client client1 = createClient(1L,
                "John",
                "Doe",
                "john.doe@example.com",
                "09123456789");
        Client client2 = createClient(2L,
                "John",
                "Smith",
                "john.smith@example.com",
                "0987654321");
        List<Client> expectedClients = Arrays.asList(client1, client2);

        when(mockClientRepository.findByFirstNameIgnoreCase(firstName)).thenReturn(expectedClients);

        List<ClientDto> result = clientService.findByFirstNameIgnoreCase(firstName);

        assertEquals(expectedClients.size(), result.size());
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
    void findByFirstNameIgnoreCase_WithNoExistingClients_ShouldThrowException() {

        String firstName = "Jane";

        when(mockClientRepository.findByFirstNameIgnoreCase(anyString())).thenReturn(Collections.emptyList());

        assertThrows(ClientNotFoundException.class, () -> clientService.findByFirstNameIgnoreCase(firstName));

        verify(mockClientRepository, times(1)).findByFirstNameIgnoreCase(anyString());

    }

    @Test
    void findByLastNameIgnoreCase_WithExistingClients_ShouldReturnClientDtoList() {

        String lastName = "Doe";
        Client client1 = createClient(1L,
                "John",
                "Doe",
                "john.doe@example.com",
                "09123456789");
        Client client2 = createClient(2L,
                "Jane",
                "Doe",
                "jane.doe@example.com",
                "0987654321");
        List<Client> expectedClients = Arrays.asList(client1, client2);

        when(mockClientRepository.findByLastNameIgnoreCase(lastName)).thenReturn(expectedClients);

        List<ClientDto> result = clientService.findByLastNameIgnoreCase(lastName);

        assertEquals(expectedClients.size(), result.size());

        verify(mockClientRepository, times(1)).findByLastNameIgnoreCase(lastName);

    }

    @Test
    void findByLastNameIgnoreCase_WithNoExistingClients_ShouldThrowException() {

        String lastName = "Smith";

        when(mockClientRepository.findByLastNameIgnoreCase(lastName)).thenReturn(Collections.emptyList());

        assertThrows(ClientNotFoundException.class, () -> clientService.findByLastNameIgnoreCase(lastName));

        verify(mockClientRepository, times(1)).findByLastNameIgnoreCase(lastName);

    }

    @Test
    void findAllClients_WithExistingClients_ShouldReturnClientDtoList() {

        Client client1 = createClient(1L,
                "John",
                "Doe",
                "john.doe@example.com",
                "09123456789");
        Client client2 = createClient(2L,
                "Jane",
                "Doe",
                "jane.doe@example.com",
                "0987654321");
        List<Client> expectedClients = Arrays.asList(client1, client2);

        when(mockClientRepository.findAll()).thenReturn(expectedClients);

        List<ClientDto> result = clientService.findAllClients();

        assertEquals(expectedClients.size(), result.size());
        assertEquals(client1.getFirstName(), result.get(0).getFirstName());
        assertEquals(client1.getLastName(), result.get(0).getLastName());
        assertEquals(client1.getEmail(), result.get(0).getEmail());
        assertEquals(client1.getContactNumber(), result.get(0).getContactNumber());
        assertEquals(client2.getFirstName(), result.get(1).getFirstName());
        assertEquals(client2.getLastName(), result.get(1).getLastName());
        assertEquals(client2.getEmail(), result.get(1).getEmail());
        assertEquals(client2.getContactNumber(), result.get(1).getContactNumber());

        verify(mockClientRepository, times(1)).findAll();

    }

    @Test
    void findAllClients_WithNoExistingClients_ShouldThrowException() {

        List<Client> expectedClients = Collections.emptyList();

        when(mockClientRepository.findAll()).thenReturn(expectedClients);

        assertThrows(ClientNotFoundException.class, () -> clientService.findAllClients());

        verify(mockClientRepository, times(1)).findAll();

    }

    @Test
    void getCurrentClient_WhenClientExists_ReturnsClient() {

        String username = "john.doe";
        Users user = new Users();
        user.setId(1L);
        user.setUsername(username);

        Client expectedClient = new Client();
        expectedClient.setId(1L);
        expectedClient.setFirstName("John");
        expectedClient.setLastName("Doe");

        when(mockUserRepository.findFirstByUsername(username)).thenReturn(user);
        when(mockClientRepository.findById(user.getId())).thenReturn(Optional.of(expectedClient));

        try(MockedStatic<SecurityUtil> mockedStatic = mockStatic(SecurityUtil.class)){
            mockedStatic.when(SecurityUtil::getSessionUser).thenReturn(username);

            Client result = clientService.getCurrentClient();

            assertEquals(expectedClient, result);

            mockedStatic.verify(SecurityUtil::getSessionUser, times(1));
            verify(mockUserRepository, times(1)).findFirstByUsername(username);
            verify(mockClientRepository, times(1)).findById(user.getId());
        }

    }

    @Test
    void getCurrentClient_WhenUserNotFound_ThrowsUserNotFoundException() {

        String username = "john.doe";

        when(mockUserRepository.findFirstByUsername(username)).thenReturn(null);

        try(MockedStatic<SecurityUtil> mockedStatic = mockStatic(SecurityUtil.class)) {
            mockedStatic.when(SecurityUtil::getSessionUser).thenReturn(username);
            assertThrows(UserNotFoundException.class, () -> clientService.getCurrentClient());
            verify(mockUserRepository, times(1)).findFirstByUsername(username);
            verify(mockClientRepository, never()).findById(anyLong());
        }

    }

    @Test
    void getCurrentClient_WhenNoLoggedInUser_ThrowsUserNotFoundException() {

        try(MockedStatic<SecurityUtil> mockedStatic = mockStatic(SecurityUtil.class)){
            mockedStatic.when(SecurityUtil::getSessionUser).thenReturn(null);
            assertThrows(UserNotFoundException.class, () -> clientService.getCurrentClient());
            verify(mockUserRepository, never()).findFirstByUsername(anyString());
            verify(mockClientRepository, never()).findById(anyLong());
        }

    }

    @Test
    void getCurrentClient_WhenClientNotFound_ThrowsClientNotFoundException() {

        String username = "john.doe";
        Users user = new Users();
        user.setId(1L);
        user.setUsername(username);

        when(mockUserRepository.findFirstByUsername(username)).thenReturn(user);
        when(mockClientRepository.findById(user.getId())).thenReturn(Optional.empty());

        try(MockedStatic<SecurityUtil> mockedStatic = mockStatic(SecurityUtil.class)) {
            mockedStatic.when(SecurityUtil::getSessionUser).thenReturn(username);

            assertThrows(ClientNotFoundException.class, () -> clientService.getCurrentClient());
            verify(mockUserRepository, times(1)).findFirstByUsername(username);
            verify(mockClientRepository, times(1)).findById(user.getId());
        }

    }

    @Test
    void updateClient_SuccessfullyUpdatesClient() {
        String username = "john.doe";
        ClientDto clientDto = ClientDto.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .contactNumber("1234567890")
                .user(new Users())
                .build();

        Client client = Client.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .contactNumber("1234567890")
                .user(Users.builder().id(1L).username(username).build())
                .build();

        Users user = Users.builder()
                .id(1L)
                .username("john.doe")
                .build();

        try(MockedStatic<SecurityUtil> mockedStatic = mockStatic(SecurityUtil.class)) {
            mockedStatic.when(SecurityUtil::getSessionUser).thenReturn(username);
            
            when(mockUserRepository.findFirstByUsername(username)).thenReturn(user);
            when(mockClientRepository.findById(user.getId())).thenReturn(Optional.of(client));

            assertDoesNotThrow(() -> clientService.updateClient(clientDto));

        }
    }

    private Client createClient(Long id, String firstName, String lastName, String email, String contactNumber) {
        return Client.builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .contactNumber(contactNumber)
                .build();
    }
}