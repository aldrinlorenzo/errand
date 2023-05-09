package com.errand.mapper;

import com.errand.dto.PendingTaskDto;
import com.errand.dto.TaskDto;
import com.errand.models.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    public static Task mapToTask(TaskDto task){
        Task taskDto = Task.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .budget(task.getBudget())
                .street(task.getStreet())
                .city(task.getCity())
                .postalCode(task.getPostalCode())
                .createdby(String.valueOf(task.getClient().getUser().getUsername()))
                .status(task.getStatus())
                .offerId(task.getOfferId())
                .targetDate(task.getTargetDate())
                .completedDate(task.getCompletedDate())
                .createdDate(task.getCreatedDate())
                .modifiedDate(task.getModifiedDate())
                .build();
        return taskDto;
    }

    public static TaskDto mapToTaskDto(Task task){
        TaskDto taskDto = TaskDto.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .budget(task.getBudget())
                .street(task.getStreet())
                .city(task.getCity())
                .postalCode(task.getPostalCode())
                .status(task.getStatus())
                .offerId(task.getOfferId())
                .targetDate(task.getTargetDate())
                .completedDate(task.getCompletedDate())
                .createdDate(task.getCreatedDate())
                .modifiedDate(task.getModifiedDate())
                .build();
        return taskDto;
    }

    public PendingTaskDto toPendingTaskDto(Task task) {
        return PendingTaskDto.builder()
                .title(task.getTitle())
                .description(task.getDescription())
                .budget(task.getBudget())
                .street(task.getStreet())
                .city(task.getCity())
                .postalCode(task.getPostalCode())
                .targetDate(task.getTargetDate())
                .createdDate(task.getCreatedDate())
                .targetDate(task.getTargetDate())
                .createdDate(task.getCreatedDate())
                .build();
    }

    public PendingTaskDto mapToPendingTaskDto(Task task) {
        return PendingTaskDto.builder()
                .title(task.getTitle())
                .description(task.getDescription())
                .budget(task.getBudget())
                .street(task.getStreet())
                .city(task.getCity())
                .postalCode(task.getPostalCode())
                .targetDate(task.getTargetDate())
                .createdDate(task.getCreatedDate())
                .targetDate(task.getTargetDate())
                .createdDate(task.getCreatedDate())
                .build();
    }

}
