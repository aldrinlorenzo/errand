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
import com.errand.services.ClientService;
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
    private ClientService clientService;

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository,
                           UserRepository userRepository,
                           ClientRepository clientRepository,
                           ClientService clientService) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.clientRepository = clientRepository;
        this.clientService = clientService;
    }

    @Override
    public List<TaskDto> findAllTask() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream().map((task) -> mapToTaskDto(task)).collect(Collectors.toList());
    }

    @Override
    public TaskDto findTaskById(Long taskId) {
        Task task = taskRepository.findById(taskId).get();
        return mapToTaskDto(task);
    }

    @Override
    public List<PendingTaskDto> getPendingTask() {
        List<Task> pendingTasks = taskRepository.searchTasksByStatus("PENDING");
        return pendingTasks.stream().map((pendingTask) ->
                taskMapper.mapToPendingTaskDto(pendingTask))
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskDto> getPendingTaskByClient() {
        List<Task> pendingTasks = taskRepository.searchTasksByClientAndStatus(clientService.getCurrentClient(), "PENDING");
        return pendingTasks.stream().map((pendingTask) ->
                        taskMapper.mapToTaskDto(pendingTask))
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskDto> getOngoingTask() {
        List<Task> ongoingTasks = taskRepository.searchTasksByStatus("ONGOING");
        return ongoingTasks.stream().map((tasks) ->
                        taskMapper.mapToTaskDto(tasks))
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskDto> getOngoingTaskByClient() {
        List<Task> ongoingTasks = taskRepository.searchTasksByClientAndStatus(clientService.getCurrentClient(), "ONGOING");
        return ongoingTasks.stream()
                .map((ongoingTask) -> taskMapper.mapToTaskDto(ongoingTask))
                .collect(Collectors.toList());
    }

    public List<TaskDto> getCompletedTaskOnAdmin() {
        List<Task> completedTasks = taskRepository.searchTasksByStatus("COMPLETED");
        return completedTasks.stream().map((tasks) ->
                        taskMapper.mapToTaskDto(tasks))
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskDto> getCompletedTaskByClient() {
        List<Task> completedTasks = taskRepository.searchTasksByClientAndStatus(clientService.getCurrentClient(), "COMPLETED");
        return completedTasks.stream()
                .map((completedTask) -> taskMapper.mapToTaskDto(completedTask))
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskDto> getCancelledTaskOnAdmin() {
        List<Task> cancelledTasks = taskRepository.searchTasksByStatus("CANCELLED");
        return cancelledTasks.stream().map((tasks) ->
                        taskMapper.mapToTaskDto(tasks))
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskDto> getCancelledTaskByClient() {
        List<Task> cancelledTasks = taskRepository.searchTasksByClientAndStatus(clientService.getCurrentClient(), "CANCELLED");
        return cancelledTasks.stream()
                .map((cancelledTask) -> taskMapper.mapToTaskDto(cancelledTask))
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
        task.setLabels(taskDto.getLabels());
        return taskRepository.save(task);
    }

    @Override
    public List<TaskDto> findTaskByServiceProvider(Long id) {
        List<Task> tasks = taskRepository.searchTaskByServiceProviderId(id);
        return tasks.stream().map(TaskMapper::mapToTaskDto).collect(Collectors.toList());
    }

    @Override
    public List<TaskDto> getTasksByClient(Client client) {
        List<Task> tasks = taskRepository.getTasksByClient(client);
        return tasks.stream().map((task) -> mapToTaskDto(task)).collect(Collectors.toList());
    }

    @Override
    public void updateTask(TaskDto taskDto, Client client) {
        Task task = mapToTask(taskDto);
        task.setClient(client);
        task.setLabels(taskDto.getLabels());
        taskRepository.save(task);
    }

    @Override
    public void cancelTask(Long id) {
        Optional<Task> optionalTask =taskRepository.findById(id);
        Task task = optionalTask.orElseThrow(() -> new RuntimeException("Task not found"));
        task.setStatus("CANCELLED");
        taskRepository.save(task);
    }

    @Override
    public void setStatusToOngoing(Task task) {
        task.setClient(clientService.getCurrentClient());
        task.setStatus("ONGOING");
        taskRepository.save(task);
    }

}
