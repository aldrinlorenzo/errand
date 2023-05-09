package com.errand.mapper;

import com.errand.dto.ServiceProviderForDisplayDto;
import com.errand.dto.ServiceProviderForUpdateDto;
import com.errand.models.ServiceProvider;
import org.springframework.stereotype.Component;

@Component
public class ServiceProviderMapper {

    public ServiceProviderForUpdateDto toServiceProviderForUpdateDto(ServiceProvider serviceProvider) {
        return ServiceProviderForUpdateDto.builder()
                .firstName(serviceProvider.getFirstName())
                .lastName(serviceProvider.getLastName())
                .email(serviceProvider.getEmail())
                .contactNumber(serviceProvider.getContactNumber())
                .businessName(serviceProvider.getBusinessName())
                .build();
    }

}
