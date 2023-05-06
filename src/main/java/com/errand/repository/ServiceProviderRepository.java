package com.errand.repository;

import com.errand.models.ServiceProvider;

import org.springframework.data.jpa.repository.JpaRepository;


public interface ServiceProviderRepository extends JpaRepository<ServiceProvider,Long> {

    ServiceProvider findByFirstName(String firstName);

    ServiceProvider findByLastName(String lastName);
}
