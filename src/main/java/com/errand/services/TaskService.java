package com.errand.services;

import com.errand.dto.PendingTaskDto;
import com.errand.dto.TaskDto;
import com.errand.models.Task;

import java.util.List;

public interface TaskService {

    List<TaskDto> findAllTask();

    List<PendingTaskDto> getPendingTask();

    Task saveTask(TaskDto taskDto);

}
