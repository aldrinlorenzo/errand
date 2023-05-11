package com.errand.mapper;

import com.errand.dto.ServiceProviderDto;
import com.errand.dto.ServiceProviderForUpdateDto;
import com.errand.models.ServiceProvider;
import org.springframework.stereotype.Component;

@Component
public class ServiceProviderMapper {

    public static ServiceProviderForUpdateDto toServiceProviderForUpdateDto(ServiceProvider serviceProvider) {
        return ServiceProviderForUpdateDto.builder()
                .firstName(serviceProvider.getFirstName())
                .lastName(serviceProvider.getLastName())
                .email(serviceProvider.getEmail())
                .contactNumber(serviceProvider.getContactNumber())
                .businessName(serviceProvider.getBusinessName())
                .build();
    }

    public static  ServiceProvider toServiceProvider(ServiceProviderDto serviceProviderDto) {
        return ServiceProvider.builder()
                .id(serviceProviderDto.getId())
                .firstName(serviceProviderDto.getFirstName())
                .lastName(serviceProviderDto.getLastName())
                .email(serviceProviderDto.getEmail())
                .contactNumber(serviceProviderDto.getContactNumber())
                .businessName(serviceProviderDto.getBusinessName())
                .build();
    }

    public static ServiceProviderDto toServiceProviderDto(ServiceProvider serviceProvider){
        return ServiceProviderDto.builder()
                .id(serviceProvider.getId())
                .firstName(serviceProvider.getFirstName())
                .lastName(serviceProvider.getLastName())
                .email(serviceProvider.getEmail())
                .contactNumber(serviceProvider.getContactNumber())
                .businessName(serviceProvider.getBusinessName())
                .build();
    }


}
