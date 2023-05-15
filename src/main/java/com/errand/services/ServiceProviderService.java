package com.errand.services;

import com.errand.dto.ServiceProviderDto;
import java.util.List;

import com.errand.models.ServiceProvider;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.springframework.stereotype.Service;


@Service
public interface ServiceProviderService {

    ServiceProviderDto getServiceProviderById(Long id);

    Boolean updateServiceProviderDetails(ServiceProviderDto serviceProviderDto, Long id);

    List<ServiceProviderDto> findByFirstNameIgnoreCase(String name);

    List<ServiceProviderDto> findByLastNameIgnoreCase(String name);

    List<ServiceProviderDto> getAllServiceProvider();

    ServiceProviderDto getCurrentServiceProvider();

    ServiceProvider getLoggedInServiceProvider();




}
