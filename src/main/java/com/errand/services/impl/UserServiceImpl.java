package com.errand.services.impl;

import com.errand.dto.ClientRegistrationDto;
import com.errand.dto.ServiceProviderRegistrationDto;
import com.errand.models.Client;
import com.errand.models.Role;
import com.errand.models.ServiceProvider;
import com.errand.models.Users;
import com.errand.repository.ClientRepository;
import com.errand.repository.RoleRepository;
import com.errand.repository.ServiceProviderRepository;
import com.errand.repository.UserRepository;
import com.errand.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private ClientRepository clientRepository;
    private ServiceProviderRepository serviceProviderRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           ClientRepository clientRepository,
                           ServiceProviderRepository serviceProviderRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.clientRepository = clientRepository;
        this.serviceProviderRepository = serviceProviderRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    @Transactional
    public void saveUserClient(ClientRegistrationDto registrationDto) {
        Users user = new Users();
        user.setUsername(registrationDto.getUsername());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        Role role = roleRepository.findByName("CLIENT");
        user.setRoles(Arrays.asList(role));
        userRepository.save(user);

        Client client = new Client();
        client.setUser(user);
        client.setFirstName(registrationDto.getClient().getFirstName());
        client.setLastName(registrationDto.getClient().getLastName());
        client.setEmail(registrationDto.getClient().getEmail());
        client.setContactNumber(registrationDto.getClient().getContactNumber());
        clientRepository.save(client);
    }


    @Override
    @Transactional
    public void saveUserServiceProvider(ServiceProviderRegistrationDto registrationDto) {
        Users user = new Users();
        user.setUsername(registrationDto.getUsername());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        Role role = roleRepository.findByName("SERVICE_PROVIDER");
        user.setRoles(Arrays.asList(role));
        userRepository.save(user);

        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.setUser(user);
        serviceProvider.setFirstName(registrationDto.getServiceProvider().getFirstName());
        serviceProvider.setLastName(registrationDto.getServiceProvider().getLastName());
        serviceProvider.setEmail(registrationDto.getServiceProvider().getEmail());
        serviceProvider.setContactNumber(registrationDto.getServiceProvider().getContactNumber());
        serviceProviderRepository.save(serviceProvider);
    }


    @Override
    public Users findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
