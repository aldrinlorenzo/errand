package com.errand.mapper;

import com.errand.dto.RatingDto;
import com.errand.models.Client;
import com.errand.models.Rating;
import com.errand.models.Task;

import static com.errand.mapper.ClientMapper.mapToClient;
import static com.errand.mapper.ServiceProviderMapper.toServiceProvider;
import static com.errand.mapper.TaskMapper.mapToTask;

public class RatingMapper {

    public static Rating maptoRatingFromClient(RatingDto ratingDto){

        Rating rating = Rating.builder()
                .serviceProviderRating(ratingDto.getServiceProviderRating())
                .serviceProviderRatingDescription(ratingDto.getServiceProviderRatingDescription())
                .task(mapToTask(ratingDto.getTaskDto()))
                .client(mapToClient(ratingDto.getClientDto()))
                .build();
        return rating;
    }

    public static Rating mapToRatingFromServiceProvider(RatingDto ratingDto){

        Rating rating = Rating.builder()
                .clientRatingDescription(ratingDto.getClientRatingDescription())
                .clientRating(ratingDto.getClientRating())
                .task(mapToTask(ratingDto.getTaskDto()))
                .serviceProvider(toServiceProvider(ratingDto.getServiceProviderDto()))
                .build();
        return rating;
    }
}
