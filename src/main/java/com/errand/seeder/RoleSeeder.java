package com.errand.seeder;

import com.errand.models.Role;
import com.errand.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class RoleSeeder implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.count() == 0) {
            Role adminRole = new Role();
            adminRole.setName("ADMIN");
            Role clientRole = new Role();
            clientRole.setName("CLIENT");
            Role serviceProviderRole = new Role();
            serviceProviderRole.setName("SERVICE_PROVIDER");

            roleRepository.save(adminRole);
            roleRepository.save(clientRole);
            roleRepository.save(serviceProviderRole);
        }
    }
}
