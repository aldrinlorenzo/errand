package com.errand.dto;

import com.errand.models.Task;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class LabelDto {

    private Long id;

    private String name;

    private List<Task> tasks;

}
