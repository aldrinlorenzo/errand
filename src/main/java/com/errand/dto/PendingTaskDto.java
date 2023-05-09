package com.errand.dto;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    private LocalDateTime createdDate;
}
