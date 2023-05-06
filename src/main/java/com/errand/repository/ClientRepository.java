package com.errand.repository;

import com.errand.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client,Long> {

    Client findByFirstName(String firstName);

    Client findByLastName(String lastName);

}
