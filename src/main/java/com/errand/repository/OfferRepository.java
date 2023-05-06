package com.errand.repository;

import com.errand.models.Offer;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OfferRepository extends JpaRepository<Offer,Long> {

    Offer findByPrice(float price);

}
