package com.errand.repository;

import com.errand.models.Client;
import com.errand.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {

    @Query("SELECT t FROM Task t WHERE t.status = :status")
    List<Task> searchTasksByStatus(@Param("status") String status);

    @Query("SELECT t FROM Task t WHERE t.client = :client  AND t.status = :status")
    List<Task> searchTasksByClientAndStatus(@Param("client") Client client, @Param("status") String status);

    @Query("SELECT t FROM Task t, Offer o WHERE t.offerId = o.id AND o.serviceProvider.id = :serviceProviderId")
    List<Task> searchTaskByServiceProviderId(@Param("serviceProviderId") Long serviceProviderId);

    @Query("SELECT t " +
            "FROM Task t, Offer o " +
            "WHERE t.offerId = o.id AND o.serviceProvider.id = :serviceProviderId " +
            "AND t.status = :status")
    List<Task> searchTaskByServiceProviderIdAndStatus(@Param("serviceProviderId") Long id, @Param("status") String status);

    @Query("SELECT t FROM Task t WHERE t.client = :client")
    List<Task> getTasksByClient(@Param("client") Client client);

}
