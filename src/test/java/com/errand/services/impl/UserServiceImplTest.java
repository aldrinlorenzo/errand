package com.errand.services.impl;

import com.errand.dto.BaseRegistrationDTO;
import com.errand.models.Client;
import com.errand.models.Role;
import com.errand.models.ServiceProvider;
import com.errand.models.Users;
import com.errand.repository.ClientRepository;
import com.errand.repository.RoleRepository;
import com.errand.repository.ServiceProviderRepository;
import com.errand.repository.UserRepository;
import com.errand.security.SecurityUtil;
import com.errand.services.UserService;
import org.junit.After;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class UserServiceImplTest {

    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private ClientRepository mockClientRepository;
    @Mock
    private ServiceProviderRepository mockServiceProviderRepository;
    @Mock
    private RoleRepository mockRoleRepository;
    @Mock
    private PasswordEncoder mockPasswordEncoder;

    private UserServiceImpl userService;


    @BeforeEach
    void setUp(){
        mockUserRepository = mock(UserRepository.class);
        mockClientRepository = mock(ClientRepository.class);
        mockServiceProviderRepository = mock(ServiceProviderRepository.class);
        mockRoleRepository = mock(RoleRepository.class);
        mockPasswordEncoder = mock(PasswordEncoder.class);
        userService = new UserServiceImpl(mockUserRepository, mockRoleRepository,
                mockClientRepository, mockServiceProviderRepository,
                mockPasswordEncoder);
    }

    @After
    void tearDown(){

    }

    @Test
    void saveUserClient() {
        BaseRegistrationDTO baseRegistrationDTO = new BaseRegistrationDTO();
        baseRegistrationDTO.setId(1L);
        baseRegistrationDTO.setUsername("ClientUsername");
        baseRegistrationDTO.setPassword("ClientPassword");
        baseRegistrationDTO.setFirstName("ClientFirstname");
        baseRegistrationDTO.setLastName("ClientLastname");
        baseRegistrationDTO.setEmail("Client@email");
        baseRegistrationDTO.setContactNumber("09000000000");

        Users users = new Users();
        users.setUsername(baseRegistrationDTO.getUsername());
        users.setPassword("EncodedClientPassword");
        Role role = new Role();
        role.setName("CLIENT");
        when(mockRoleRepository.findByName("CLIENT")).thenReturn(role);
        when(mockPasswordEncoder.encode(baseRegistrationDTO.getPassword())).thenReturn("EncodedClientPassword");
        when(mockUserRepository.save(any(Users.class))).thenReturn(users);

        Client client = new Client();
        client.setFirstName(baseRegistrationDTO.getFirstName());
        client.setLastName(baseRegistrationDTO.getLastName());
        client.setEmail(baseRegistrationDTO.getEmail());
        client.setContactNumber(baseRegistrationDTO.getContactNumber());
        client.setId(baseRegistrationDTO.getId());
        when(mockClientRepository.save(any(Client.class))).thenReturn(client);

        userService.saveUserClient(baseRegistrationDTO);

        verify(mockUserRepository).save(any(Users.class));
        verify(mockRoleRepository).findByName("CLIENT");
        verify(mockPasswordEncoder).encode(baseRegistrationDTO.getPassword());
        verify(mockClientRepository).save(any(Client.class));
    }

    @Test
    void saveUserServiceProvider() {
        BaseRegistrationDTO baseRegistrationDTO = new BaseRegistrationDTO();
        baseRegistrationDTO.setId(1L);
        baseRegistrationDTO.setUsername("ServiceProviderUsername");
        baseRegistrationDTO.setPassword("ServiceProviderPassword");
        baseRegistrationDTO.setFirstName("ServiceProviderFirstname");
        baseRegistrationDTO.setLastName("ServiceProviderLastname");
        baseRegistrationDTO.setEmail("ServiceProvider@email");
        baseRegistrationDTO.setContactNumber("09000000000");

        Users users = new Users();
        users.setUsername(baseRegistrationDTO.getUsername());
        users.setPassword("EncodedServiceProviderPassword");
        Role role = new Role();
        role.setName("SERVICE_PROVIDER");
        when(mockRoleRepository.findByName("SERVICE_PROVIDER")).thenReturn(role);
        when(mockPasswordEncoder.encode(baseRegistrationDTO.getPassword())).thenReturn("EncodedServiceProviderPassword");
        when(mockUserRepository.save(any(Users.class))).thenReturn(users);

        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.setFirstName(baseRegistrationDTO.getFirstName());
        serviceProvider.setLastName(baseRegistrationDTO.getLastName());
        serviceProvider.setEmail(baseRegistrationDTO.getEmail());
        serviceProvider.setContactNumber(baseRegistrationDTO.getContactNumber());
        serviceProvider.setId(baseRegistrationDTO.getId());
        when(mockServiceProviderRepository.save(any(ServiceProvider.class))).thenReturn(serviceProvider);

        userService.saveUserServiceProvider(baseRegistrationDTO);

        verify(mockUserRepository).save(any(Users.class));
        verify(mockRoleRepository).findByName("SERVICE_PROVIDER");
        verify(mockPasswordEncoder).encode(baseRegistrationDTO.getPassword());
        verify(mockServiceProviderRepository).save(any(ServiceProvider.class));
    }

    @Test
    void findByUsername() {
        Users users = new Users();
        users.setUsername("Username");
        when(mockUserRepository.findFirstByUsername("Username")).thenReturn(users);

        Users result = userService.findByUsername("Username");
        verify(mockUserRepository).findFirstByUsername("Username");

        assertEquals(users.getUsername(), result.getUsername());
    }

    @Test
    void getCurrentUser() {
        String username = "john.doe";
        Users user = new Users();
        user.setId(1L);
        user.setUsername(username);

        when(mockUserRepository.findFirstByUsername(username)).thenReturn(user);

        try(MockedStatic<SecurityUtil> mockedStatic = mockStatic(SecurityUtil.class)) {
            mockedStatic.when(SecurityUtil::getSessionUser).thenReturn(username);

            Users currentUser  = userService.getCurrentUser();

            assertEquals(user,currentUser);

            mockedStatic.verify(SecurityUtil::getSessionUser, times(1));
            verify(mockUserRepository, times(1)).findFirstByUsername(username);
        }
    }
}