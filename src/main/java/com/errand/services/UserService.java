package com.errand.services;

import com.errand.dto.BaseRegistrationDTO;
import com.errand.models.Client;
import com.errand.models.Users;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    void saveUserClient(BaseRegistrationDTO clientRegistrationDto);

    void saveUserServiceProvider(BaseRegistrationDTO serviceProviderRegistrationDto);

    Users findByUsername(String username);

    Users getCurrentUser();

}
