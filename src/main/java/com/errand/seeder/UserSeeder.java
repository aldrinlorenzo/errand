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
            Users serviceProvider_1 = new Users();
            serviceProvider_1.setUsername("service_provider_1");
            serviceProvider_1.setPassword(encoder.encode("service_provider_1"));
            serviceProvider_1.setRoles(Collections.singletonList(serviceProviderRole));


            Users serviceProvider_2 = new Users();
            serviceProvider_2 .setUsername("service_provider_2");
            serviceProvider_2 .setPassword(encoder.encode("service_provider_2"));
            serviceProvider_2 .setRoles(Collections.singletonList(serviceProviderRole));


            Users serviceProvider_3 = new Users();
            serviceProvider_3.setUsername("service_provider_3");
            serviceProvider_3.setPassword(encoder.encode("service_provider_3"));
            serviceProvider_3.setRoles(Collections.singletonList(serviceProviderRole));



            Users serviceProvider_4 = new Users();
            serviceProvider_4 .setUsername("service_provider_4");
            serviceProvider_4 .setPassword(encoder.encode("service_provider_4"));
            serviceProvider_4 .setRoles(Collections.singletonList(serviceProviderRole));


            Users serviceProvider_5 = new Users();
            serviceProvider_5 .setUsername("service_provider_5");
            serviceProvider_5 .setPassword(encoder.encode("service_provider_5"));
            serviceProvider_5 .setRoles(Collections.singletonList(serviceProviderRole ));


            userRepository.save(serviceProvider_1);
            userRepository.save(serviceProvider_2);
            userRepository.save(serviceProvider_3);
            userRepository.save(serviceProvider_4);
            userRepository.save(serviceProvider_5);

            //client
            Role clientProviderRole = roleRepository.findByName("CLIENT");
            Users client_1 = new Users();
            client_1.setUsername("client_1");
            client_1.setPassword(encoder.encode("client_1"));
            client_1.setRoles(Collections.singletonList(clientProviderRole));


            Users client_2 = new Users();
            client_2.setUsername("client_2");
            client_2.setPassword(encoder.encode("client_2"));
            client_2.setRoles(Collections.singletonList(clientProviderRole));


            Users client_3 = new Users();
            client_3 .setUsername("client_3");
            client_3 .setPassword(encoder.encode("client_3"));
            client_3 .setRoles(Collections.singletonList(clientProviderRole));


            Users client_4 = new Users();
            client_4 .setUsername("client_4");
            client_4 .setPassword(encoder.encode("client_4"));
            client_4 .setRoles(Collections.singletonList(clientProviderRole));

            Users client_5 = new Users();
            client_5.setUsername("client_5");
            client_5.setPassword(encoder.encode("client_5"));
            client_5.setRoles(Collections.singletonList(clientProviderRole));



            userRepository.save(client_1);
            userRepository.save(client_2);
            userRepository.save(client_3);
            userRepository.save(client_4);
            userRepository.save(client_5);



            Client newClient_1 = new Client();
            newClient_1.setId(client_1.getId());
            newClient_1.setUser(client_1);
            newClient_1.setContactNumber("0912345679");
            newClient_1.setEmail("client@gmail.com");
            newClient_1.setFirstName("Johny");
            newClient_1.setLastName("Depp");

            Client newClient_2 = new Client();
            newClient_2.setId(client_2.getId());
            newClient_2.setUser(client_2);
            newClient_2.setContactNumber("0912345679");
            newClient_2.setEmail("client2@gmail.com");
            newClient_2.setFirstName("Maria");
            newClient_2.setLastName("Rizal");

            Client newClient_3 = new Client();
            newClient_3.setId(client_3.getId());
            newClient_3.setUser(client_3);
            newClient_3.setContactNumber("09123432423");
            newClient_3.setEmail("client3@gmail.com");
            newClient_3.setFirstName("Aldrin");
            newClient_3.setLastName("Reyes");


            Client newClient_4 = new Client();
            newClient_4.setId(client_4.getId());
            newClient_4.setUser(client_4);
            newClient_4.setContactNumber("0912341234");
            newClient_4.setEmail("client4@gmail.com");
            newClient_4.setFirstName("Raine");
            newClient_4.setLastName("Wayne");


            Client newClient_5 = new Client();
            newClient_5.setId(client_5.getId());
            newClient_5.setUser(client_5);
            newClient_5.setContactNumber("0912342423");
            newClient_5.setEmail("client5@gmail.com");
            newClient_5.setFirstName("May");
            newClient_5.setLastName("Batis");




            clientRepository.save(newClient_1);
            clientRepository.save(newClient_2);
            clientRepository.save(newClient_3);
            clientRepository.save(newClient_4);
            clientRepository.save(newClient_5);

            ServiceProvider newServiceProvider_1 = new ServiceProvider();
            newServiceProvider_1.setId(serviceProvider_1.getId());
            newServiceProvider_1.setUser(serviceProvider_1);
            newServiceProvider_1.setBusinessName("Jon's Catering Services");
            newServiceProvider_1.setContactNumber("0912345678");
            newServiceProvider_1.setEmail("jonscatering@gmail.com");
            newServiceProvider_1.setFirstName("Jon");
            newServiceProvider_1.setLastName("Doe");

            ServiceProvider newServiceProvider_2 = new ServiceProvider();
            newServiceProvider_2.setId(serviceProvider_2.getId());
            newServiceProvider_2.setUser(serviceProvider_2);
            newServiceProvider_2.setBusinessName("Mike's Plumbing Services");
            newServiceProvider_2.setContactNumber("0911232132");
            newServiceProvider_2.setEmail("mikesplumbing@gmail.com");
            newServiceProvider_2.setFirstName("Mike");
            newServiceProvider_2.setLastName("Smith");

            ServiceProvider newServiceProvider_3 = new ServiceProvider();
            newServiceProvider_3.setId(serviceProvider_3.getId());
            newServiceProvider_3.setUser(serviceProvider_3);
            newServiceProvider_3.setBusinessName("Jane's Photography Services");
            newServiceProvider_3.setContactNumber("0918765432");
            newServiceProvider_3.setEmail("janesphotography@gmail.com");
            newServiceProvider_3.setFirstName("Jane");
            newServiceProvider_3.setLastName("Doe");

            ServiceProvider newServiceProvider_4 = new ServiceProvider();
            newServiceProvider_4.setId(serviceProvider_4.getId());
            newServiceProvider_4.setUser(serviceProvider_4);
            newServiceProvider_4.setBusinessName("Alex's Car Wash Services");
            newServiceProvider_4.setContactNumber("0914567890");
            newServiceProvider_4.setEmail("alexscarwash@gmail.com");
            newServiceProvider_4.setFirstName("Alex");
            newServiceProvider_4.setLastName("Garcia");

            ServiceProvider newServiceProvider_5 = new ServiceProvider();
            newServiceProvider_5.setId(serviceProvider_5.getId());
            newServiceProvider_5.setUser(serviceProvider_5);
            newServiceProvider_5.setBusinessName("Alex's Car Wash Services");
            newServiceProvider_5.setContactNumber("0914567890");
            newServiceProvider_5.setEmail("alexscarwash@gmail.com");
            newServiceProvider_5.setFirstName("Alex");
            newServiceProvider_5.setLastName("Garcia");





            serviceProviderRepository.save(newServiceProvider_1);
            serviceProviderRepository.save(newServiceProvider_2);
            serviceProviderRepository.save(newServiceProvider_3);
            serviceProviderRepository.save(newServiceProvider_4);
            serviceProviderRepository.save(newServiceProvider_5);

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
