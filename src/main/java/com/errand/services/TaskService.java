package com.errand.services;

import com.errand.dto.PendingTaskDto;
import com.errand.dto.TaskDto;
import com.errand.models.Client;
import com.errand.models.Task;

import java.util.List;

public interface TaskService {

    List<TaskDto> findAllTask();

    TaskDto findTaskById(Long taskId);

    List<PendingTaskDto> getPendingTask();


    List<TaskDto> getOngoingTask();

    List<TaskDto> getCompletedTaskOnAdmin();

    List<TaskDto> getCancelledTaskOnAdmin();

    Task saveTask(TaskDto taskDto);


    List<TaskDto> findTaskByServiceProvider(Long id);


    List<TaskDto> getTasksByClient(Client client);

    void updateTask(TaskDto task, Client client);

    void cancelTask(TaskDto task, Client client);

}
