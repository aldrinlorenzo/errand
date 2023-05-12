package com.errand.repository;

import com.errand.models.Rating;
import com.errand.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RatingRepository extends JpaRepository<Rating,Long> {

    Rating findByClientRating(float clientRating);

    Rating findByServiceProviderRating(float serviceProviderRating);

    @Query("SELECT r FROM Rating r WHERE r.task = :task")
    Rating findByTaskId(@Param("task") Task task);

}
