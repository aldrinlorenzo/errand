package com.errand.dto;

import lombok.Data;
import org.springframework.lang.Nullable;

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

    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "First Name should contain only letters and spaces")
    private String firstName;

    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Last Name should contain only letters and spaces")
    private String lastName;

    @Pattern(regexp = "^[0-9+]*$", message = "Contact Number should contain only numbers and '+' (if present)")
    private String contactNumber;

    //@Pattern(regexp = "^[a-zA-Z0-9!@#\\$%\\^\\&*\\)\\(+=._\\-\\s]+$", message = "Business Name should contain only letters, numbers, spaces, and special characters")
    @Nullable
    private String businessName;

    private String license;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

}
