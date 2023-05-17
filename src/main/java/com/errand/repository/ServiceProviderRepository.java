package com.errand.repository;

import com.errand.dto.ServiceProviderDto;
import com.errand.models.Client;
import com.errand.models.ServiceProvider;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ServiceProviderRepository extends JpaRepository<ServiceProvider, Long> {

    List<ServiceProvider> findByFirstNameIgnoreCase(String firstName);


    ServiceProvider findByLastName(String lastName);

    @Modifying
    @Query("UPDATE ServiceProvider SET firstName = :firstName, lastName = :lastName , email = :email, contactNumber = :contactNumber , businessName = :businessName , profileImageFileName = :profileImageFileName where id = :serviceProviderId ")
    void update(@Param("firstName") String firstName, @Param("lastName") String lastName,
                           @Param("email") String email, @Param("contactNumber") String contactNumber,
                           @Param("businessName") String businessName,
                           @Param("serviceProviderId") Long id,
                           @Param("profileImageFileName") String profileImageFileName);
    List<ServiceProvider> findByLastNameIgnoreCase(String lastName);
}
