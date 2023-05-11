package com.errand.dto;

import lombok.Data;

@Data
public class RatingDto {
    private float clientRating;

    private String clientRatingDescription;

    private float serviceProviderRating;

    private String serviceProviderRatingDescription;

    private TaskDto taskDto;
}
