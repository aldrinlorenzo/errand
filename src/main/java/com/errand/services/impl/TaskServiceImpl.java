package com.errand.services.impl;

import com.errand.dto.PendingTaskDto;
import com.errand.dto.TaskDto;
import com.errand.mapper.TaskMapper;
import com.errand.models.Task;
import com.errand.repository.TaskRepository;
import com.errand.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.errand.mapper.TaskMapper.mapToTaskDto;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    TaskRepository taskRepository;


    @Autowired
    private TaskMapper taskMapper;

    @Override
    public List<TaskDto> findAllTask() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream().map((task) -> mapToTaskDto(task)).collect(Collectors.toList());
    }

    @Override
    public List<PendingTaskDto> getPendingTask() {
        List<Task> pendingTasks = taskRepository.searchTasksByStatus("PENDING");

        return pendingTasks.stream()
                .map(taskMapper::toPendingTaskDto)
                .collect(Collectors.toList());

    }

    @Override
    public List<TaskDto> getCompletedTask() {
        List<Task> completedTasks = taskRepository.searchTasksByStatus("COMPLETED");

        return completedTasks.stream().map((task) -> mapToTaskDto(task)).collect(Collectors.toList());
    }
}
