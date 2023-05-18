package com.errand.repository;

import com.errand.models.Offer;
import com.errand.models.ServiceProvider;
import com.errand.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OfferRepository extends JpaRepository<Offer,Long> {

    Offer findByPrice(float price);

    @Query("SELECT o FROM Offer o WHERE o.task = :task")
    List<Offer> findOffersByTask(@Param("task") Task task);

    @Query("SELECT o FROM Offer o WHERE o.task.id = :taskId AND o.serviceProvider.id = :serviceProviderId")
    Offer findOfferByTaskAndServiceProvider(@Param("taskId") Long taskId, @Param("serviceProviderId") Long serviceProviderId);

    @Query("SELECT o FROM Offer o WHERE o.serviceProvider = :serviceProvider")
    List<Offer> findByServiceProvider(@Param("serviceProvider") ServiceProvider serviceProvider);
    @Modifying
    @Query("DELETE FROM Offer o WHERE o.id = :offerId")
    void deleteOfferById(@Param("offerId") Long offerId);

}
