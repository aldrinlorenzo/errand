package com.errand.dto;

import com.errand.models.Task;
import lombok.Builder;
import lombok.Data;

import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Builder
@Data
public class LabelDto {

    private Long id;
    private String name;
    private List<Task> tasks;
}
