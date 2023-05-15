package com.errand.mapper;

import com.errand.dto.PendingTaskDto;
import com.errand.dto.TaskDto;
import com.errand.models.Task;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

import java.time.format.DateTimeFormatter;

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
                .status(task.getStatus())
                .offerId(task.getOfferId())
                .targetDate(LocalDate.parse(task.getTargetDate()))
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
                .createdBy(task.getClient().getUser().getUsername())
                .status(task.getStatus())
                .offerId(task.getOfferId())
                .targetDate(task.getTargetDate().format(DateTimeFormatter.ISO_DATE))
                .completedDate(task.getCompletedDate())
                .labels(task.getLabels())
                .createdDate(task.getCreatedDate())
                .modifiedDate(task.getModifiedDate())
                .rating(task.getRating())
                .build();
        return taskDto;
    }

    public static PendingTaskDto toPendingTaskDto(Task task) {
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

    public static PendingTaskDto mapToPendingTaskDto(Task task) {
        return PendingTaskDto.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .budget(task.getBudget())
                .street(task.getStreet())
                .city(task.getCity())
                .postalCode(task.getPostalCode())
                .createdBy(task.getClient().getUser().getUsername())
                .status(task.getStatus())
                .targetDate(task.getTargetDate())
                .createdDate(task.getCreatedDate())
                .targetDate(task.getTargetDate())
                .createdDate(task.getCreatedDate())
                .build();
    }

}
