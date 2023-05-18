package com.errand.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class OfferDto {

    private Long id;

    private BigDecimal price;

    private String description;

    private String status;

    private ServiceProviderDto serviceProviderDto;

    private TaskDto taskDto;

}
