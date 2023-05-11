package com.errand.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ServiceProviderForUpdateDto {

    private String firstName;

    private String lastName;

    private String email;

    private String contactNumber;

    private String businessName;


}
