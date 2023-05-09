package com.errand.services.impl;

import com.errand.dto.ServiceProviderForDisplayDto;
import com.errand.dto.ServiceProviderForUpdateDto;
import com.errand.mapper.ServiceProviderMapper;
import com.errand.models.ServiceProvider;
import com.errand.repository.ServiceProviderRepository;
import com.errand.services.ServiceProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Override
    public List<ServiceProviderForDisplayDto> getAllServiceProvider() {
        return serviceProviderRepository.findAll()
                .stream()
                .map(serviceProviderMapper::toServiceProviderForDisplayDto)
                .collect(Collectors.toList());
    }

    public ServiceProviderForDisplayDto toServiceProviderForDisplayDto(ServiceProvider serviceProvider){
        return ServiceProviderForDisplayDto.builder()
                .id(serviceProvider.getId())
                .firstName(serviceProvider.getFirstName())
                .lastName(serviceProvider.getLastName())
                .email(serviceProvider.getEmail())
                .contactNumber(serviceProvider.getContactNumber())
                .businessName(serviceProvider.getBusinessName())
                .build();
    }


}
