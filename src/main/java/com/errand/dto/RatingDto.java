package com.errand.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.lang.Nullable;

@Data
@Builder
public class RatingDto {

    @Nullable
    private float clientRating;

    private String clientRatingDescription;

    @Nullable
    private float serviceProviderRating;

    private String serviceProviderRatingDescription;

    @Nullable
    private TaskDto taskDto;

    @Nullable
    private ServiceProviderDto serviceProviderDto;

    @Nullable
    private ClientDto clientDto;

}
