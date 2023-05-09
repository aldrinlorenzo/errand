package com.errand.services;

import com.errand.dto.ServiceProviderForDisplayDto;
import com.errand.dto.ServiceProviderForUpdateDto;
import com.errand.models.Client;
import com.errand.models.ServiceProvider;

import java.util.List;
import java.util.Optional;

public interface ServiceProviderService {

    ServiceProviderForUpdateDto getServiceProviderById(Long id);

    Boolean updateServiceProviderDetails(ServiceProviderForUpdateDto serviceProviderForUpdateDto, Long id);


    List<ServiceProviderForDisplayDto> getAllServiceProvider();

    ServiceProviderForDisplayDto getCurrentServiceProvider();




}
