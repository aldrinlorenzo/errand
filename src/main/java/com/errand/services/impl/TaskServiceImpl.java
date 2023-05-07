package com.errand.services.impl;

import com.errand.dto.TaskDto;
import com.errand.models.Task;
import com.errand.repository.TaskRepository;
import com.errand.services.TaskService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.errand.mapper.TaskMapper.mapToTaskDto;

@Service
public class TaskServiceImpl implements TaskService {

    TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public List<TaskDto> findAllTask() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream().map((task) -> mapToTaskDto(task)).collect(Collectors.toList());
    }
}
