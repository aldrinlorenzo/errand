package com.errand.services;

import com.errand.dto.ServiceProviderDto;
import com.errand.dto.ServiceProviderForUpdateDto;


import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface ServiceProviderService {

    ServiceProviderForUpdateDto getServiceProviderById(Long id);

    Boolean updateServiceProviderDetails(ServiceProviderDto serviceProviderDto, Long id);


    List<ServiceProviderDto> getAllServiceProvider();

    ServiceProviderDto getCurrentServiceProvider();




}
