package com.errand.services;

import com.errand.dto.ServiceProviderForDisplayDto;
import com.errand.dto.ServiceProviderForUpdateDto;
import com.errand.models.Client;
import com.errand.models.ServiceProvider;

<<<<<<< Updated upstream
import java.util.List;
=======
import org.springframework.stereotype.Service;

>>>>>>> Stashed changes
import java.util.Optional;

@Service
public interface ServiceProviderService {

    ServiceProviderForUpdateDto getServiceProviderById(Long id);

    Boolean updateServiceProviderDetails(ServiceProviderForUpdateDto serviceProviderForUpdateDto, Long id);


    List<ServiceProviderForDisplayDto> getAllServiceProvider();

    ServiceProviderForDisplayDto getCurrentServiceProvider();


}
