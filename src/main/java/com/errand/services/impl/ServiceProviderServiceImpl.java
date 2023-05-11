package com.errand.services.impl;

import com.errand.dto.ServiceProviderDto;
import com.errand.dto.ServiceProviderForUpdateDto;
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


    @Autowired
    private ServiceProviderRepository serviceProviderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ServiceProviderMapper serviceProviderMapper;

    @Override
    public ServiceProviderForUpdateDto getServiceProviderById(Long id) {
        Optional<ServiceProvider> serviceProviderOptional = serviceProviderRepository.findById(id);
        if (serviceProviderOptional.isPresent()) {
            return serviceProviderMapper.toServiceProviderForUpdateDto(serviceProviderOptional.get());
        } else {
            throw new IllegalArgumentException("Service provider with id " + id + " not found.");
        }
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
                id);
       return  true;
    }


    @Override
    public List<ServiceProviderDto> getAllServiceProvider() {
        return serviceProviderRepository.findAll()
                .stream()
                .map(serviceProviderMapper::toServiceProviderDto)
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
        return serviceProviderMapper.toServiceProviderDto(serviceProvider);
    }


    @Override
    public  List<ServiceProviderDto> findByFirstNameIgnoreCase(String name) {
        List<ServiceProvider> serviceProviders = serviceProviderRepository.findByFirstNameIgnoreCase(name);
        return serviceProviders.stream().map((serviceProvider) -> serviceProviderMapper.toServiceProviderDto(serviceProvider)).collect(Collectors.toList());
    }

    @Override
    public  List<ServiceProviderDto> findByLastNameIgnoreCase(String name) {
        List<ServiceProvider> serviceProviders = serviceProviderRepository.findByLastNameIgnoreCase(name);
        return serviceProviders.stream().map((serviceProvider) -> serviceProviderMapper.toServiceProviderDto(serviceProvider)).collect(Collectors.toList());
    }


}
