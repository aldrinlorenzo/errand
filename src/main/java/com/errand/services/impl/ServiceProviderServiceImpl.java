package com.errand.services.impl;

import com.errand.dto.ServiceProviderForUpdateDto;
import com.errand.mapper.ServiceProviderMapper;
import com.errand.models.ServiceProvider;
import com.errand.repository.ServiceProviderRepository;
import com.errand.services.ServiceProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ServiceProviderServiceImpl implements ServiceProviderService {


    @Autowired
    private ServiceProviderRepository serviceProviderRepository;

    @Autowired
    private ServiceProviderMapper serviceProviderMapper;
    @Override
    public Optional<ServiceProviderForUpdateDto> getServiceProviderById(Long id) {

        Optional<ServiceProvider> serviceProviderOptional = serviceProviderRepository.findById(id);
        return serviceProviderOptional.map(serviceProviderMapper::toServiceProviderForUpdateDto);
    }
}
