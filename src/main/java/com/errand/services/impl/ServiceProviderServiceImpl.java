package com.errand.services.impl;

import com.errand.dto.ServiceProviderDto;
import com.errand.mapper.ServiceProviderMapper;
import com.errand.models.ServiceProvider;
import com.errand.models.Users;
import com.errand.repository.ServiceProviderRepository;
import com.errand.repository.UserRepository;
import com.errand.security.SecurityUtil;
import com.errand.services.ServiceProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ServiceProviderServiceImpl implements ServiceProviderService {

    private ServiceProviderRepository serviceProviderRepository;
    private UserRepository userRepository;
    private ServiceProviderMapper serviceProviderMapper;

    @Autowired
    public ServiceProviderServiceImpl(
            ServiceProviderRepository serviceProviderRepository,
            UserRepository userRepository,
            ServiceProviderMapper serviceProviderMapper) {
        this.serviceProviderRepository = serviceProviderRepository;
        this.userRepository = userRepository;
        this.serviceProviderMapper = serviceProviderMapper;
    }

    @Override
    public ServiceProviderDto getServiceProviderById(Long id) {
        Optional<ServiceProvider> serviceProviderOptional = serviceProviderRepository.findById(id);
        if (serviceProviderOptional.isPresent()) {
            return ServiceProviderMapper.toServiceProviderDto(serviceProviderOptional.get());
        } else {
            throw new IllegalArgumentException("Service provider with id " + id + " not found.");
        }
    }

    @Override
    public ServiceProvider findByEmail(String email) {
        return serviceProviderRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public Boolean updateServiceProviderDetails(@NonNull ServiceProviderDto serviceProviderDto, @NonNull Long id) {
        if (!serviceProviderRepository.existsById(id)) {
            throw new EntityNotFoundException("Service provider not found with ID: " + id);

        }
       serviceProviderRepository.update(serviceProviderDto.getFirstName(),
                serviceProviderDto.getLastName(), serviceProviderDto.getEmail(),
                serviceProviderDto.getContactNumber(),
                serviceProviderDto.getBusinessName(),
                id, serviceProviderDto.getProfileImageFileName());
       return  true;
    }

    @Override
    public List<ServiceProviderDto> getAllServiceProvider() {
        return serviceProviderRepository.findAll()
                .stream()
                .map(ServiceProviderMapper::toServiceProviderDto)
                .collect(Collectors.toList());
    }

    @Override
    public ServiceProviderDto getCurrentServiceProvider() {
        ServiceProvider serviceProvider = new ServiceProvider();
        String username = SecurityUtil.getSessionUser();
        if (username != null) {
            Users user = userRepository.findFirstByUsername(username);
            serviceProvider = serviceProviderRepository.findById(user.getId()).orElseThrow(() -> new RuntimeException("Client not found"));
        }
        return ServiceProviderMapper.toServiceProviderDto(serviceProvider);
    }

    @Override
    public ServiceProvider getLoggedInServiceProvider() {
        ServiceProvider serviceProvider = new ServiceProvider();
        String username = SecurityUtil.getSessionUser();
        if(username != null){
            Users user = userRepository.findFirstByUsername(username);
            Optional<ServiceProvider> optionalClient = serviceProviderRepository.findById(user.getId());
            serviceProvider = optionalClient.orElseThrow(() -> new RuntimeException("Client not found"));


        }
        return serviceProvider;
    }

    @Override
    @Transactional
    public void updateServiceProviderImage(ServiceProviderDto serviceProviderDto) {

        serviceProviderRepository.update(serviceProviderDto.getFirstName(),
                serviceProviderDto.getLastName(), serviceProviderDto.getEmail(),
                serviceProviderDto.getContactNumber(),
                serviceProviderDto.getBusinessName(),
                serviceProviderDto.getId(),
                serviceProviderDto.getProfileImageFileName()
        );

    }

    @Override
    public  List<ServiceProviderDto> findByFirstNameIgnoreCase(String name) {
        List<ServiceProvider> serviceProviders = serviceProviderRepository.findByFirstNameIgnoreCase(name);
        return serviceProviders.stream().map(ServiceProviderMapper::toServiceProviderDto).collect(Collectors.toList());
    }

    @Override
    public  List<ServiceProviderDto> findByLastNameIgnoreCase(String name) {
        List<ServiceProvider> serviceProviders = serviceProviderRepository.findByLastNameIgnoreCase(name);
        return serviceProviders.stream().map(ServiceProviderMapper::toServiceProviderDto).collect(Collectors.toList());
    }


}
