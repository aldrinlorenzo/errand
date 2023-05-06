package com.errand.repository;

import com.errand.models.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating,Long> {

    Rating findByClientRating(float clientRating);

    Rating findByServiceProviderRating(float serviceProviderRating);
}
