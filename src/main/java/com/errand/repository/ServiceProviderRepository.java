package com.errand.repository;

import com.errand.models.ServiceProvider;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface ServiceProviderRepository extends JpaRepository<ServiceProvider, Long> {

    ServiceProvider findByFirstName(String firstName);

    ServiceProvider findByLastName(String lastName);


    @Query("UPDATE Service_Providers SET firstNAME = :firstName, lastName = :lastName , email = :email, contactNumber = :contactNumber , businessName = :businessName  where id = :serviceProviderId ")
    ServiceProvider update(@Param("firstName") String firstName, @Param("lastName") String lastName,
                           @Param("email") String email, @Param("contactNumber") String contactNumber,
                           @Param("businessName") String businessName,
                           @Param("serviceProviderId") String serviceProviderId);
}
