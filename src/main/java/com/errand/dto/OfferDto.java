package com.errand.dto;

import com.errand.models.ServiceProvider;
import com.errand.models.Task;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Builder
public class OfferDto {


    private BigDecimal price;

    private String description;

    private String status;

    private ServiceProviderDto serviceProviderDto;

    private TaskDto taskDto;
}
