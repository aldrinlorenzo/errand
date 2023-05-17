package com.errand.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ServiceProviderDto {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String contactNumber;

    private String businessName;

    private String profileImageFileName;

}
