package com.errand.repository;

import com.errand.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<Users,Long> {

    Users findByUsername(String username);

    Users findFirstByUsername(String username);

}
