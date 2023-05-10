package com.errand.services;

import com.errand.dto.ServiceProviderForDisplayDto;
import com.errand.dto.ServiceProviderForUpdateDto;

import java.util.List;

import org.springframework.stereotype.Service;


@Service
public interface ServiceProviderService {

    ServiceProviderForUpdateDto getServiceProviderById(Long id);

    Boolean updateServiceProviderDetails(ServiceProviderForUpdateDto serviceProviderForUpdateDto, Long id);


    List<ServiceProviderForDisplayDto> getAllServiceProvider();

    ServiceProviderForDisplayDto getCurrentServiceProvider();


}
