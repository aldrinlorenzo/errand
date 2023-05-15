package com.errand.services;

import com.errand.dto.PendingTaskDto;
import com.errand.dto.TaskDto;
import com.errand.models.Client;
import com.errand.models.Task;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TaskService {

    List<TaskDto> findAllTask();

    TaskDto findTaskById(Long taskId);

    List<PendingTaskDto> getPendingTask();

    List<TaskDto> getPendingTaskByClient();

    List<TaskDto> getOngoingTask();

    List<TaskDto> getOngoingTaskByClient();

    List<TaskDto> getCompletedTaskOnAdmin();

    List<TaskDto> getCompletedTaskByClient();

    List<TaskDto> getCancelledTaskOnAdmin();

    List<TaskDto> getCancelledTaskByClient();

    Task saveTask(TaskDto taskDto);


    List<TaskDto> findTaskByServiceProvider(Long id);

    List<TaskDto> findTaskByServiceProviderAndStatus(Long id, String status);

    List<TaskDto> getTasksByClient(Client client);

    void updateTask(TaskDto task, Client client);

    void completeTask(Long id);

    void cancelTask(Long id);

    void setStatusToOngoing(Task task);

}
