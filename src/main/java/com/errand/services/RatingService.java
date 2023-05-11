package com.errand.services;

import com.errand.dto.RatingDto;
import com.errand.models.Rating;
import com.errand.models.ServiceProvider;
import com.errand.models.Task;

public interface RatingService {
    Rating saveRateFromClient(RatingDto ratingDto);
}
