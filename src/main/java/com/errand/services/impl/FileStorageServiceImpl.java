package com.errand.services.impl;

import com.errand.models.Client;
import com.errand.services.ClientService;
import com.errand.services.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.*;

@Service
public class FileStorageServiceImpl implements FileStorageService {
    private final Path root = Paths.get("./src/main/resources/static/assets/profile-images");

    private ClientService clientService;

    @Autowired
    public FileStorageServiceImpl(ClientService clientService) {
        this.clientService = clientService;
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

}
