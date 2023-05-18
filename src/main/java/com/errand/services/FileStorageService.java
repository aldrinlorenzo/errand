package com.errand.services;

import com.errand.dto.ServiceProviderDto;
import com.errand.models.Client;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

    public void saveClientImage(MultipartFile file, Client client);

    public void saveServiceProviderImage(MultipartFile file, ServiceProviderDto serviceProviderDto);

}
