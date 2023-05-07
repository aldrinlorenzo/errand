package com.errand.services;

import com.errand.dto.BaseRegistrationDTO;
import com.errand.dto.ClientRegistrationDto;
import com.errand.dto.ServiceProviderRegistrationDto;
import com.errand.models.Users;

public interface UserService {

    void saveUserClient(BaseRegistrationDTO clientRegistrationDto);

    void saveUserServiceProvider(BaseRegistrationDTO serviceProviderRegistrationDto);

    Users findByUsername(String username);
}
