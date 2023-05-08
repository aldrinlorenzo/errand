package com.errand.seeder;

import com.errand.models.Client;
import com.errand.models.Role;
import com.errand.models.ServiceProvider;
import com.errand.models.Users;
import com.errand.repository.ClientRepository;
import com.errand.repository.RoleRepository;
import com.errand.repository.ServiceProviderRepository;
import com.errand.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

@Component
@Order(3)
public class UserSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ServiceProviderRepository serviceProviderRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (serviceProviderRepository.count() == 0 || clientRepository.count() == 0) {

            //serviceProvider

            Role serviceProviderRole = roleRepository.findByName("SERVICE_PROVIDER");
            Users serviceProvider = new Users();
            serviceProvider.setUsername("service_provider_1");
            serviceProvider.setPassword(encoder.encode("service_provider_1"));
            serviceProvider.setRoles(Collections.singletonList(serviceProviderRole));

            //client
            Role clientProviderRole = roleRepository.findByName("CLIENT");
            Users client = new Users();
            client.setUsername("client_1");
            client.setPassword(encoder.encode("client_1"));
            client.setRoles(Collections.singletonList(clientProviderRole));


            Users client_2 = new Users();
            client_2.setUsername("client_2");
            client_2.setPassword(encoder.encode("client_2"));
            client_2.setRoles(Collections.singletonList(clientProviderRole));

            userRepository.save(serviceProvider);
            userRepository.save(client);
            userRepository.save(client_2);

            ServiceProvider newServiceProvider = new ServiceProvider();
            newServiceProvider.setId(serviceProvider.getId());
            newServiceProvider.setUser(serviceProvider);
            newServiceProvider.setBusinessName("service_business_name");
            newServiceProvider.setContactNumber("0912345678");
            newServiceProvider.setEmail("service_business@gmail.com");
            newServiceProvider.setFirstName("Jon");
            newServiceProvider.setLastName("Hopkins");

            Client newClient = new Client();
            newClient.setId(client.getId());
            newClient.setUser(client);
            newClient.setContactNumber("0912345679");
            newClient.setEmail("client@gmail.com");
            newClient.setFirstName("Johny");
            newClient.setLastName("Depp");

            Client newClient2 = new Client();
            newClient2.setId(client_2.getId());
            newClient2.setUser(client_2);
            newClient2.setContactNumber("0912345679");
            newClient2.setEmail("client2@gmail.com");
            newClient2.setFirstName("Maria");
            newClient2.setLastName("Rizal");


            clientRepository.save(newClient);
            clientRepository.save(newClient2);
            serviceProviderRepository.save(newServiceProvider);



            List<Users> users = userRepository.findAll();

            for (Users user : users) {
                List<Role> roles = user.getRoles();
                for (Role role : roles) {
                    String sql = "INSERT INTO user_roles (user_id, role_id) VALUES (:userId, :roleId)";
                    Query query = entityManager.createNativeQuery(sql);
                    query.setParameter("userId", user.getId());
                    query.setParameter("roleId", role.getId());
                    query.executeUpdate();
                }
            }
        }
    }
}
