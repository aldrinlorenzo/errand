package com.errand.dto;

import com.errand.models.Label;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;

@Data
@Builder
public class TaskDto {

    private Long id;

    private String title;

    private String description;

    private BigDecimal budget;

    private String street;

    private String city;

    private BigDecimal postalCode;

    private String status;

    private Long offerId;

    private LocalDateTime targetDate;

    private LocalDateTime completedDate;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

    private Set<Label> labels;

}
