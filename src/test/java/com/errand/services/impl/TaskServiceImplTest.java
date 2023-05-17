package com.errand.services.impl;

import com.errand.dto.PendingTaskDto;
import com.errand.dto.TaskDto;
import com.errand.mapper.ServiceProviderMapper;
import com.errand.mapper.TaskMapper;
import com.errand.models.*;
import com.errand.repository.ClientRepository;
import com.errand.repository.TaskRepository;
import com.errand.repository.UserRepository;
import com.errand.security.SecurityUtil;
import com.errand.services.ClientService;
import com.errand.services.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

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
    private List<Task> taskList;
    private List<TaskDto> taskDtoList;
    private TaskDto taskDto;
    private List<PendingTaskDto> pendingTaskDtoList;
    private Client client;

    private ServiceProvider serviceProvider;
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
        Set<Label> labels = new HashSet<>();
        taskDto = TaskDto.builder().
                id(1L).title("Fix Pc")
                .description("Hotdog")
                .budget(new BigDecimal("100.00"))
                .street("Street").city("City")
                .postalCode(new BigDecimal("12345"))
                .status("PENDING")
                .offerId(2L)
                .offerDto(null)
                .targetDate("2023-05-16")
                .completedDate(LocalDateTime.now())
                .createdDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now())
                .labels(labels).createdBy("User")
                .rating(null)
                .build();
        serviceProvider = new ServiceProvider();
        serviceProvider.setId(1L);

        user.setUsername("username");

        client.setUser(user);

        task.setClient(client);
        task.setTargetDate(LocalDate.now());

        taskList = new ArrayList<>();
        taskDtoList = new ArrayList<>();
        pendingTaskDtoList = new ArrayList<>();

        taskList.add(task);
        taskDtoList.add(TaskMapper.mapToTaskDto(task));
        pendingTaskDtoList.add(TaskMapper.mapToPendingTaskDto(task));
    }

    @Test
    public void testFindAllTask() {
        when(taskRepository.findAll()).thenReturn(taskList);
        assert (taskDtoList.equals(taskService.findAllTask()));
    }

    @Test
    public void testFindTaskById() {
        TaskDto taskDto = TaskMapper.mapToTaskDto(task);
        when(taskRepository.findById(any())).thenReturn(Optional.of(task));
        assert (taskDto.equals(taskService.findTaskById(1L)));
    }

    @Test
    public void testGetPendingTask() {
        when(taskRepository.searchTasksByStatus(any())).thenReturn(taskList);
        assert (pendingTaskDtoList.equals(taskService.getPendingTask()));
    }

    @Test
    public void testGetPendingTaskByClient() {
        ;
        when(taskRepository.searchTasksByClientAndStatus(any(), any())).thenReturn(taskList);
        assert (taskDtoList.equals(taskService.getPendingTaskByClient()));
    }

    @Test
    public void testGetOngoingTask() {
        ;
        when(taskRepository.searchTasksByStatus(any())).thenReturn(taskList);
        assert (taskDtoList.equals(taskService.getOngoingTask()));
    }

    @Test
    public void testGetOngoingTaskByClient() {
        when(taskRepository.searchTasksByClientAndStatus(any(), any())).thenReturn(taskList);
        assert (taskDtoList.equals(taskService.getOngoingTaskByClient()));
    }

    @Test
    public void testGetCompletedTaskOnAdmin() {
        when(taskRepository.searchTasksByStatus(any())).thenReturn(taskList);
        assert (taskDtoList.equals(taskService.getCompletedTaskOnAdmin()));
    }

    @Test
    public void testGetCompletedTaskByClient() {
        when(taskRepository.searchTasksByClientAndStatus(any(Client.class), eq("COMPLETED"))).thenReturn(taskList);
        when(clientService.getCurrentClient()).thenReturn(client);
        List<TaskDto> actual = taskService.getCompletedTaskByClient();
        assertEquals(taskDtoList, actual);
    }

    @Test
    public void testGetCancelledTaskOnAdmin() {
        when(taskRepository.searchTasksByStatus("CANCELLED")).thenReturn(taskList);
        assert (taskDtoList.equals(taskService.getCancelledTaskOnAdmin()));
        verify(taskRepository).searchTasksByStatus("CANCELLED");
    }

    @Test
    public void testGetCancelledTaskByClient() {
        when(taskRepository.searchTasksByClientAndStatus(any(Client.class), eq("CANCELLED"))).thenReturn(taskList);
        when(clientService.getCurrentClient()).thenReturn(client);
        List<TaskDto> actual = taskService.getCancelledTaskByClient();
        assertEquals(taskDtoList, actual);
    }

    @Test
    public void testSaveTask() {
        try (MockedStatic<SecurityUtil> mockedStatic = mockStatic(SecurityUtil.class)) {

            when(userRepository.findByUsername(any())).thenReturn(user);
            when(clientRepository.findById(user.getId())).thenReturn(Optional.of(client));
            try (MockedStatic<TaskMapper> mapperMockedStatic = mockStatic(TaskMapper.class)) {
                when(taskRepository.save(any(Task.class))).thenReturn(task);
                when(TaskMapper.mapToTask(taskDto)).thenReturn(task);
                Task result = taskService.saveTask(taskDto);

                assertEquals("PENDING", result.getStatus());
                assertEquals(client, result.getClient());
                assertEquals(taskDto.getLabels(), result.getLabels());


            }


        }


    }

    @Test
    public void testFindTaskByServiceProvider() {


        try (MockedStatic<TaskMapper> mockedStatic = mockStatic(TaskMapper.class)) {
            when(taskRepository.searchTaskByServiceProviderId(anyLong())).thenReturn(taskList);
            when(TaskMapper.mapToTaskDto(any(Task.class))).thenReturn(any(TaskDto.class));
            List<TaskDto> result = taskService.findTaskByServiceProvider(serviceProvider.getId());
            assertEquals(taskList.size(), result.size());
        }
    }

    @Test
    public void testFindTaskByServiceProviderAndStatus() {
        try (MockedStatic<TaskMapper> mockedStatic = mockStatic(TaskMapper.class)) {
            when(taskRepository.searchTaskByServiceProviderIdAndStatus(any(), any()))
                    .thenReturn(taskList);
            List<TaskDto> result = taskService.findTaskByServiceProviderAndStatus(serviceProvider.getId(), "COMPLETED");
            assertEquals(taskList.size(), result.size());
        }
    }

    @Test
    public void testGetTaskByClient() {
        when(taskRepository.getTasksByClient(any(Client.class))).thenReturn(taskList);

        try (MockedStatic<TaskMapper> mockedStatic = mockStatic(TaskMapper.class)) {

            when(TaskMapper.mapToTaskDto(any(Task.class))).thenReturn(any(TaskDto.class));

            List<TaskDto> result = taskService.getTasksByClient(client);

            assertEquals(taskList.size(),result.size());
        }
    }

    @Test
    public void testUpdateTask() {
        try (MockedStatic<TaskMapper> mockedStatic = mockStatic(TaskMapper.class)){
            when(TaskMapper.mapToTask(any(TaskDto.class))).thenReturn(task);
            taskService.updateTask(taskDto, client);
            Task expectedTask = new Task();
            expectedTask.setClient(client);
            verify(taskRepository).save(any(Task.class));

        }


    }
    @Test
    public void testCompleteTask(){
        when(taskRepository.findById(any())).thenReturn(Optional.of(task));
        taskService.completeTask(anyLong());
        Task expectedTask = new Task();
        expectedTask.setStatus("COMPLETED");

        assertEquals(task.getStatus(), expectedTask.getStatus());
        verify(taskRepository).save(any(Task.class));
    }

    @Test
    public void testCancelTask(){
        when(taskRepository.findById(any())).thenReturn(Optional.of(task));
        taskService.cancelTask(anyLong());
        Task expectedTask = new Task();
        expectedTask.setStatus("CANCELLED");

        assertEquals(task.getStatus(), expectedTask.getStatus());
        verify(taskRepository).save(any(Task.class));
    }

    @Test
    public void testsetStatusOngoing(){
        when(clientService.getCurrentClient()).thenReturn(client);

        taskService.setStatusToOngoing(task);
        Task expectedTask = new Task();
        expectedTask.setStatus("ONGOING");

        assertEquals(task.getStatus(), expectedTask.getStatus());
        verify(taskRepository).save(any(Task.class));

    }
}



