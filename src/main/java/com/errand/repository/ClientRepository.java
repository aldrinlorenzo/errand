package com.errand.repository;

import com.errand.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client,Long> {

    List<Client> findByFirstNameIgnoreCase(String firstName);

    List<Client> findByLastNameIgnoreCase(String lastName);

    Client findByFirstName(String firstName);

    Client findByLastName(String lastName);

    @Query("SELECT c FROM Client c WHERE c.email = :email")
    Client findByEmail(@Param("email") String email);

}
