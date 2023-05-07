package com.errand.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Data
public class BaseRegistrationDTO {
    private Long id;
    @NotEmpty
    private String username;
    @NotEmpty
    private String email;
    @NotEmpty
    private String password;
    private String firstName;
    private String lastName;
    private String contactNumber;
    private String businessName;
    private String license;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
}
