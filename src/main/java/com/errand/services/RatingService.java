package com.errand.services;

import com.errand.dto.RatingDto;
import com.errand.dto.TaskDto;
import com.errand.models.Rating;
import com.errand.models.ServiceProvider;
import com.errand.models.Task;

import java.util.List;
import java.util.Optional;

public interface RatingService {
    Rating saveRateFromClient(RatingDto ratingDto);

    Rating saveRateFromServiceProvider(RatingDto ratingDto);

    void updateRatingFromClient(Rating rating, RatingDto ratingDto);

    void updateRatingFromServiceProvider(Rating rating, RatingDto ratingDto);

    Rating getRatingByTask(Task task);

}
