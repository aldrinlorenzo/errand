package com.errand;

import com.errand.mapper.ServiceProviderMapper;
import com.errand.models.ServiceProvider;
import com.errand.repository.ServiceProviderRepository;
import com.errand.repository.UserRepository;
import com.errand.services.ServiceProviderService;
import com.errand.services.impl.ServiceProviderServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ServiceProviderTests {
    @Mock
    private ServiceProviderRepository serviceProviderRepository;
    @Mock
    private UserRepository userRepository;
    private ServiceProviderMapper serviceProviderMapper = new ServiceProviderMapper();

    private ServiceProviderService serviceProviderService;

    @BeforeEach
    void initTests() {
        serviceProviderService = new ServiceProviderServiceImpl(
                serviceProviderRepository,
                userRepository,
                serviceProviderMapper);
    }

    @Test
    public void testGetById() {
        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.setId(1L);
        serviceProvider.setFirstName("firstname");
        serviceProvider.setLastName("lastname");
        serviceProvider.setEmail("email@domain.com");
        serviceProvider.setContactNumber("0987654321");
        serviceProvider.setBusinessName("business");
        Optional<ServiceProvider> serviceProviderOptional = Optional.of(serviceProvider);
        when(serviceProviderRepository.findById(any())).thenReturn(serviceProviderOptional);
        assert(ServiceProviderMapper.toServiceProviderDto(serviceProvider).
                equals(serviceProviderService.getServiceProviderById(1L)));
    }

}
