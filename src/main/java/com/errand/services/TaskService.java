package com.errand.services;

import com.errand.dto.TaskDto;

import java.util.List;

public interface TaskService {
    List<TaskDto> findAllTask();
}
