package com.errand.services;

import com.errand.dto.ServiceProviderForUpdateDto;

import java.util.Optional;

public interface ServiceProviderService {

    Optional<ServiceProviderForUpdateDto> getServiceProviderById(Long id);
}
