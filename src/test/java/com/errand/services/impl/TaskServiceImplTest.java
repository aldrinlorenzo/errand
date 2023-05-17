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
import com.errand.services.ClientService;
import com.errand.services.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskServiceImplTest {
    @Mock
    private TaskRepository taskRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ClientRepository clientRepository;
    @Mock
    private ClientService clientService;
    private TaskMapper taskMapper = new TaskMapper();
    private TaskService taskService;
    private Task task;
    private Client client;
    private Users user;

    @BeforeEach
    void initTests() {
        taskService = new TaskServiceImpl(
                taskRepository,
                userRepository,
                clientRepository,
                clientService,
                taskMapper
        );

        task = new Task();
        client = new Client();
        user = new Users();

        user.setUsername("username");

        client.setUser(user);

        task.setClient(client);
        task.setTargetDate(LocalDate.now());
    }

    @Test
    public void testFindAllTask() {
        List<Task> taskList = new ArrayList<>();
        taskList.add(task);
        List<TaskDto> taskDtoList = new ArrayList<>();
        taskDtoList.add(TaskMapper.mapToTaskDto(task));
        when(taskRepository.findAll()).thenReturn(taskList);
        assert(taskDtoList.equals(taskService.findAllTask()));
    }

    @Test
    public void testFindTaskById() {
        TaskDto taskDto = TaskMapper.mapToTaskDto(task);
        when(taskRepository.findById(any())).thenReturn(Optional.of(task));
        assert(taskDto.equals(taskService.findTaskById(1L)));
    }

    @Test
    public void testGetPendingTask() {
        List<Task> taskList = new ArrayList<>();
        taskList.add(task);
        List<PendingTaskDto> pendingTaskDtoList = new ArrayList<>();
        pendingTaskDtoList.add(TaskMapper.mapToPendingTaskDto(task));
        when(taskRepository.searchTasksByStatus(any())).thenReturn(taskList);
        assert(pendingTaskDtoList.equals(taskService.getPendingTask()));
    }

    @Test
    public void testGetPendingTaskByClient() {
        List<Task> taskList = new ArrayList<>();
        taskList.add(task);
        List<TaskDto> taskDtoList = new ArrayList<>();
        taskDtoList.add(TaskMapper.mapToTaskDto(task));
        when(taskRepository.searchTasksByClientAndStatus(any(), any())).thenReturn(taskList);
        assert(taskDtoList.equals(taskService.getPendingTaskByClient()));
    }

    @Test
    public void testGetOngoingTask() {
        List<Task> taskList = new ArrayList<>();
        taskList.add(task);
        List<TaskDto> taskDtoList = new ArrayList<>();
        taskDtoList.add(TaskMapper.mapToTaskDto(task));
        when(taskRepository.searchTasksByStatus(any())).thenReturn(taskList);
        assert(taskDtoList.equals(taskService.getOngoingTask()));
    }
}
