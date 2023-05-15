package com.errand.repository;

import com.errand.models.Rating;
import com.errand.models.ServiceProvider;
import com.errand.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating,Long> {

    Rating findByClientRating(float clientRating);

    Rating findByServiceProviderRating(float serviceProviderRating);

    @Query("SELECT r FROM Rating r WHERE r.task = :task")
    Rating findByTaskId(@Param("task") Task task);

    @Query("SELECT r FROM Rating r WHERE r.serviceProvider = :serviceProvider")
    List<Rating> findByServiceProvider(@Param("serviceProvider") ServiceProvider serviceProvider);

}
