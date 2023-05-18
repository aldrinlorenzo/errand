package com.errand.dto;

import com.errand.models.Label;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
public class PendingTaskDto {

    private Long id;

    private String createdBy;

    private String title;

    private String description;

    private BigDecimal budget;

    private String street;

    private String city;

    private String status;

    private BigDecimal postalCode;

    private LocalDate targetDate;
    @NotNull
    private Set<Label> labels;

    private LocalDateTime createdDate;

}
