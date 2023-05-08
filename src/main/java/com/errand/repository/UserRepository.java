package com.errand.repository;

import com.errand.models.Task;
import com.errand.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface UserRepository extends JpaRepository<Users,Long> {


    @Query("SELECT u FROM Users u WHERE u.username = :userName")
    Users findByUsername(@Param("userName") String userName);



    Users findFirstByUsername(String username);

}
