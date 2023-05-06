package com.errand.services;

import com.errand.dto.ClientRegistrationDto;
import com.errand.dto.ServiceProviderRegistrationDto;
import com.errand.models.Users;

public interface UserService {

    void saveUserClient(ClientRegistrationDto clientRegistrationDto);

    void saveUserServiceProvider(ServiceProviderRegistrationDto serviceProviderRegistrationDto);

    Users findByUsername(String username);
}
