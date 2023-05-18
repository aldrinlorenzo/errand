package com.errand.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
@Builder
public class ServiceProviderForUpdateDto {

    @Pattern(regexp = "^[ a-zA-Z]+$", message = "First Name should contain only letters")
    @NotEmpty(message="First Name should not be empty")
    private String firstName;

    @Pattern(regexp = "^[ a-zA-Z]+$", message = "Last Name should contain only letters")
    @NotEmpty(message="Last Name should not be empty")
    private String lastName;

    @Pattern(regexp = "^[a-zA-Z0-9!@#\\$%\\^\\&*\\)\\(+=._-]+$", message = "Invalid Email format")
    @NotEmpty(message="Email should not be empty")
    private String email;

    private String contactNumber;

    private String businessName;

}
