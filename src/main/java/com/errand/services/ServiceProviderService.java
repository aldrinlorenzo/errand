package com.errand.services;

import com.errand.dto.ServiceProviderForDisplayDto;
import com.errand.dto.ServiceProviderForUpdateDto;

import java.util.List;
import java.util.Optional;

public interface ServiceProviderService {

    Optional<ServiceProviderForUpdateDto> getServiceProviderById(Long id);


    List<ServiceProviderForDisplayDto> getAllServiceProvider();
}
