package com.errand.services;

import com.errand.dto.RatingDto;
import com.errand.dto.ServiceProviderDto;
import com.errand.models.Client;
import com.errand.models.Rating;
import com.errand.models.ServiceProvider;
import com.errand.models.Task;

import java.util.List;

public interface RatingService {
    Rating saveRateFromClient(RatingDto ratingDto);

    Rating saveRateFromServiceProvider(RatingDto ratingDto);

    void updateRatingFromClient(Rating rating, RatingDto ratingDto);

    void updateRatingFromServiceProvider(Rating rating, RatingDto ratingDto);

    Rating getRatingByTask(Task task);

    List<Rating> getRatingByServiceProvider(ServiceProviderDto serviceProviderDto);

    List<RatingDto> getRatingsByClient(Client client);

    List<RatingDto> getRatingsByServiceProvider(ServiceProvider serviceProvider);

}
