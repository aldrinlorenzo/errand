package com.errand.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Data
public class BaseRegistrationDTO {

    private Long id;

    @Pattern(regexp = "^[a-zA-Z0-9!@#\\$%\\^\\&*\\)\\(+=._-]+$", message = "Username should contain only letters, numbers and special characters")
    @NotEmpty
    private String username;

    @Pattern(regexp = "^[a-zA-Z0-9!@#\\$%\\^\\&*\\)\\(+=._-]+$", message = "Invalid Email format")
    @NotEmpty
    private String email;

    @NotEmpty
    private String password;

    @Pattern(regexp = "^[ a-zA-Z]+$", message = "First Name should contain only letters")
    private String firstName;

    @Pattern(regexp = "^[ a-zA-Z]+$", message = "Last Name should contain only letters")
    private String lastName;

    private String contactNumber;

    @Pattern(regexp = "^[a-zA-Z0-9!@#\\$%\\^\\&*\\)\\(+=._-]+$", message = "Business Name should contain only letters, numbers and special characters")
    private String businessName;

    private String license;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

}
