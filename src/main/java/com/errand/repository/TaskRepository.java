package com.errand.repository;

import com.errand.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;



@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {

//    Optional<Task> findByTaskTitle(String title);
//
//    @Query("SELECT t FROM Tasks t WHERE t.taskTitle LIKE CONCAT('%', :query, '%' ) ")
//    List<Task> searchTasks(String query);

    @Query("SELECT t FROM Task t WHERE t.status = :status")
    List<Task> searchTasksByStatus(@Param("status") String status);

}
