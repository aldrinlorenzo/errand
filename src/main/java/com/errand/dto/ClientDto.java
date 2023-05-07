package com.errand.dto;

import com.errand.models.Users;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
public class ClientDto {

    private Long id;

    @NotEmpty(message="First Name should not be empty")
    private String firstName;

    @NotEmpty(message="Last Name should not be empty")
    private String lastName;

    @NotEmpty(message="Email should not be empty")
    private String email;

    @NotEmpty(message="Contact Number should not be empty")
    private String contactNumber;

    private Users user;

}
