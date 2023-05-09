package com.errand.seeder;

import com.errand.models.*;
import com.errand.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.math.BigDecimal;

@Component
@Order(4)
public class TaskSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (taskRepository.count() == 0) {

            //task created by client1 Last Name Depp
            Task taskByClient = new Task();

            taskByClient.setClient(clientRepository.findByLastName("Depp"));

            taskByClient.setBudget(new BigDecimal(1000));
            taskByClient.setCity("Taguig");
            taskByClient.setDescription("Fix Pc");
            taskByClient.setPostalCode(new BigDecimal(123));
            taskByClient.setStatus("PENDING");
            taskByClient.setStreet("41 Block 17");
            taskByClient.setTitle("Fix PC Urgent");

            //task created by client2 LastName Rizal
            Task taskByClient2 = new Task();

            taskByClient2.setClient(clientRepository.findByLastName("Rizal"));

            taskByClient2.setBudget(new BigDecimal(1000));
            taskByClient2.setCity("Makati");
            taskByClient2.setDescription("Plumbing");
            taskByClient2.setPostalCode(new BigDecimal(123));
            taskByClient2.setStatus("PENDING");
            taskByClient2.setStreet("434 Block 234");
            taskByClient2.setTitle("Fix Plumbing");
            
            taskRepository.save(taskByClient);

            taskRepository.save(taskByClient2);

            }
        }
    }

