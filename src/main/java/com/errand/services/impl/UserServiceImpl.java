package com.errand.services.impl;

import com.errand.dto.BaseRegistrationDTO;
import com.errand.exceptions.UserNotFoundException;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ServiceProviderRepository serviceProviderRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, ClientRepository clientRepository, ServiceProviderRepository serviceProviderRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.clientRepository = clientRepository;
        this.serviceProviderRepository = serviceProviderRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void saveUserClient(BaseRegistrationDTO registrationDto) {
        Users user = new Users();
        user.setUsername(registrationDto.getUsername());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        Role role = roleRepository.findByName("CLIENT");
        user.setRoles(Arrays.asList(role));
        userRepository.save(user);

        Client client = new Client();
        client.setUser(user);
        client.setId(user.getId());
        client.setFirstName(registrationDto.getFirstName());
        client.setLastName(registrationDto.getLastName());
        client.setEmail(registrationDto.getEmail());
        client.setContactNumber(registrationDto.getContactNumber());
        clientRepository.save(client);
    }

    @Override
    @Transactional
    public void saveUserServiceProvider(BaseRegistrationDTO registrationDto) {
        Users user = new Users();
        user.setUsername(registrationDto.getUsername());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        Role role = roleRepository.findByName("SERVICE_PROVIDER");
        user.setRoles(Arrays.asList(role));
        userRepository.save(user);

        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.setUser(user);
        serviceProvider.setId(user.getId());
        serviceProvider.setFirstName(registrationDto.getFirstName());
        serviceProvider.setLastName(registrationDto.getLastName());
        serviceProvider.setEmail(registrationDto.getEmail());
        serviceProvider.setContactNumber(registrationDto.getContactNumber());
        serviceProvider.setBusinessName(registrationDto.getBusinessName());
        serviceProviderRepository.save(serviceProvider);
    }


    @Override
    public Users findByUsername(String username) throws UserNotFoundException {
        try {
            return userRepository.findFirstByUsername(username);
        }catch (Exception e){
            throw new UserNotFoundException("Can't find using that username");
        }
    }

    @Override
    public Users getCurrentUser() {
        Users user = new Users();
        String username = SecurityUtil.getSessionUser();
        if(username != null){
            user = userRepository.findFirstByUsername(username);
        }
        return user;
    }

}
