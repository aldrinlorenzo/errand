package com.errand.services.impl;

import com.errand.dto.PendingTaskDto;
import com.errand.dto.TaskDto;
import com.errand.mapper.TaskMapper;
import com.errand.models.Client;
import com.errand.models.Task;
import com.errand.models.Users;
import com.errand.repository.ClientRepository;
import com.errand.repository.TaskRepository;
import com.errand.repository.UserRepository;
import com.errand.security.SecurityUtil;
import com.errand.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.errand.mapper.TaskMapper.mapToTask;
import static com.errand.mapper.TaskMapper.mapToTaskDto;

@Service
public class TaskServiceImpl implements TaskService {

    private TaskRepository taskRepository;
    private UserRepository userRepository;
    private ClientRepository clientRepository;

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, UserRepository userRepository, ClientRepository clientRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public List<TaskDto> findAllTask() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream().map((task) -> mapToTaskDto(task)).collect(Collectors.toList());
    }

    @Override
    public List<PendingTaskDto> getPendingTask() {
        List<Task> pendingTasks = taskRepository.searchTasksByStatus("PENDING");
        return pendingTasks.stream().map((pendingTask) ->
                taskMapper.mapToPendingTaskDto(pendingTask))
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskDto> getOngoingTask() {
        List<Task> ongoingTasks = taskRepository.searchTasksByStatus("ONGOING");
        return ongoingTasks.stream().map((tasks) ->
                        taskMapper.mapToTaskDto(tasks))
                .collect(Collectors.toList());
    }

    public List<TaskDto> getCompletedTask() {
        List<Task> completedTasks = taskRepository.searchTasksByStatus("COMPLETED");
        return completedTasks.stream().map((tasks) ->
                        taskMapper.mapToTaskDto(tasks))
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskDto> getCancelledTask() {
        List<Task> cancelledTasks = taskRepository.searchTasksByStatus("CANCELLED");
        return cancelledTasks.stream().map((tasks) ->
                        taskMapper.mapToTaskDto(tasks))
                .collect(Collectors.toList());
    }

    @Override
    public Task saveTask(TaskDto taskDto){
        String username = SecurityUtil.getSessionUser();
        Users user = userRepository.findByUsername(username);
        Optional<Client> optionalClient = clientRepository.findById(user.getId());
        Client client = optionalClient.orElseThrow(() -> new RuntimeException("Client not found"));
        Task task = mapToTask(taskDto);
        task.setStatus("PENDING");
        task.setClient(client);
        return taskRepository.save(task);
    }

    @Override
    public List<TaskDto> getCompletedTask() {
        List<Task> completedTasks = taskRepository.searchTasksByStatus("COMPLETED");

        return completedTasks.stream().map(TaskMapper::mapToTaskDto).collect(Collectors.toList());
    }

    @Override
    public List<PendingTaskDto> getCancelledTask() {
        List<Task> cancelledTasks = taskRepository.searchTasksByStatus("CANCELLED");

        return cancelledTasks.stream().map(taskMapper::toPendingTaskDto).collect(Collectors.toList());
    }


}
