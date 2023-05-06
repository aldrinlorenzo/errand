package com.errand.dto;

import com.errand.models.ServiceProvider;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Data
public class ServiceProviderRegistrationDto {

    private ServiceProvider serviceProvider;

    private Long id;

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;
}
