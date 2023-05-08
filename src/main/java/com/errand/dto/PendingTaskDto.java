package com.errand.dto;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
@Builder
public class PendingTaskDto {

    private String title;

    private String description;

    private BigDecimal budget;

    private String street;

    private String city;

    private int postalCode;

    private LocalDateTime targetDate;

    private LocalDateTime createdDate;
}
