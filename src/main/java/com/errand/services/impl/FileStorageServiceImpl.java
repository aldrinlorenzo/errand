package com.errand.services.impl;

import com.errand.dto.ServiceProviderDto;
import com.errand.models.Client;
import com.errand.services.ClientService;
import com.errand.services.FileStorageService;
import com.errand.services.ServiceProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.*;

@Service
public class FileStorageServiceImpl implements FileStorageService {
    private final Path root = Paths.get("./src/main/resources/static/assets/profile-images");

    private ClientService clientService;
    private ServiceProviderService serviceProviderService;

    @Autowired
    public FileStorageServiceImpl(ClientService clientService, ServiceProviderService serviceProviderService) {
        this.clientService = clientService;
        this.serviceProviderService = serviceProviderService;
    }

    @Override
    public void saveClientImage(MultipartFile file, Client client) {
        try {
            Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
            client.setProfileImageFileName(file.getOriginalFilename());
            clientService.updateClientImage(client);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void saveServiceProviderImage(MultipartFile file, ServiceProviderDto serviceProviderDto) {
        try {
            Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
            serviceProviderDto.setProfileImageFileName(file.getOriginalFilename());
            serviceProviderService.updateServiceProviderImage(serviceProviderDto);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
