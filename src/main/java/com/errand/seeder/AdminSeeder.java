package com.errand.seeder;

import com.errand.models.Role;
import com.errand.models.Users;
import com.errand.repository.RoleRepository;
import com.errand.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Collections;

@Component
@Order(2)
public class AdminSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            Role adminRole = roleRepository.findByName("ADMIN");
            Users adminUser = new Users();

            adminUser.setUsername("admin");
            adminUser.setPassword(encoder.encode("admin"));
            adminUser.setRoles(Collections.singletonList(adminRole));
            userRepository.save(adminUser);
        }
    }

}
