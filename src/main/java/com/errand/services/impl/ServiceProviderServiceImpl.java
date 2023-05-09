package com.errand.services.impl;

import com.errand.dto.ServiceProviderForDisplayDto;
import com.errand.dto.ServiceProviderForUpdateDto;
import com.errand.mapper.ServiceProviderMapper;
import com.errand.models.ServiceProvider;
import com.errand.models.Users;
import com.errand.repository.ServiceProviderRepository;
import com.errand.repository.UserRepository;
import com.errand.security.SecurityUtil;
import com.errand.services.ServiceProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Objects;
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
    public Boolean updateServiceProviderDetails(ServiceProviderForUpdateDto serviceProviderDto, Long id) {

        Objects.requireNonNull(id, "ID cannot be null");
        Objects.requireNonNull(serviceProviderDto, "Service provider DTO cannot be null");

        if (!serviceProviderRepository.existsById(id)) {
            throw new EntityNotFoundException("Service provider not found with ID: " + id);
        }

        ServiceProvider serviceProvider = serviceProviderMapper.toServiceProvider(serviceProviderDto);
        serviceProvider.setId(id);
        serviceProviderRepository.save(serviceProvider);

        return true;
    }


    @Override
    public List<ServiceProviderForDisplayDto> getAllServiceProvider() {
        return serviceProviderRepository.findAll()
                .stream()
                .map(serviceProviderMapper::toServiceProviderForDisplayDto)
                .collect(Collectors.toList());
    }

    @Override
    public ServiceProviderForDisplayDto getCurrentServiceProvider() {
        ServiceProvider serviceProvider = new ServiceProvider();
        String username = SecurityUtil.getSessionUser();
        if (username != null) {
            Users user = userRepository.findFirstByUsername(username);
            serviceProvider = serviceProviderRepository.findById(user.getId()).orElseThrow(() -> new RuntimeException("Client not found"));
            ;

        }
        return serviceProviderMapper.toServiceProviderForDisplayDto(serviceProvider);


    }
}
