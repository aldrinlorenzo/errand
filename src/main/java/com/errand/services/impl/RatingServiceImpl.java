package com.errand.services.impl;

import com.errand.dto.RatingDto;
import com.errand.dto.TaskDto;
import com.errand.models.*;
import com.errand.repository.ClientRepository;
import com.errand.repository.RatingRepository;
import com.errand.repository.UserRepository;
import com.errand.security.SecurityUtil;
import com.errand.services.RatingService;
import com.errand.mapper.RatingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.errand.mapper.ClientMapper.mapToClient;
import static com.errand.mapper.RatingMapper.mapToRatingFromServiceProvider;
import static com.errand.mapper.RatingMapper.maptoRatingFromClient;
import static com.errand.mapper.ServiceProviderMapper.toServiceProvider;
import static com.errand.mapper.TaskMapper.mapToTask;

@Service
public class RatingServiceImpl implements RatingService {
    UserRepository userRepository;
    ClientRepository clientRepository;
    RatingRepository ratingRepository;

    @Autowired
    public RatingServiceImpl(UserRepository userRepository, ClientRepository clientRepository, RatingRepository ratingRepository) {
        this.userRepository = userRepository;
        this.clientRepository = clientRepository;
        this.ratingRepository = ratingRepository;
    }

    @Override
    public Rating saveRateFromClient(RatingDto ratingDto) {
        String username = SecurityUtil.getSessionUser();
        Users user = userRepository.findByUsername(username);
        Rating rating = maptoRatingFromClient(ratingDto);
        return ratingRepository.save(rating);
    }

    @Override
    public Rating saveRateFromServiceProvider(RatingDto ratingDto) {
        String username = SecurityUtil.getSessionUser();
        Users user = userRepository.findByUsername(username);
        Rating rating = mapToRatingFromServiceProvider(ratingDto);
        return ratingRepository.save(rating);
    }

    @Override
    public void updateRatingFromClient(Rating rating, RatingDto ratingDto) {
        ratingDto.setClientRating(rating.getClientRating());
        ratingDto.setClientRatingDescription(rating.getClientRatingDescription());
        rating.setServiceProviderRating(ratingDto.getServiceProviderRating());
        rating.setServiceProviderRatingDescription(ratingDto.getServiceProviderRatingDescription());
        rating.setClient(mapToClient(ratingDto.getClientDto()));
        ratingRepository.save(rating);
    }

    @Override
    public void updateRatingFromServiceProvider(Rating rating, RatingDto ratingDto) {
        ratingDto.setServiceProviderRating(rating.getServiceProviderRating());
        ratingDto.setServiceProviderRatingDescription(rating.getServiceProviderRatingDescription());
        rating.setClientRating(ratingDto.getClientRating());
        rating.setClientRatingDescription(ratingDto.getClientRatingDescription());
        rating.setServiceProvider(toServiceProvider(ratingDto.getServiceProviderDto()));
        ratingRepository.save(rating);
    }

    @Override
    public Rating getRatingByTask(Task task) {
       Rating rating =  ratingRepository.findByTaskId(task);
        return rating;
    }

}
