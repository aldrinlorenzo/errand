package com.errand.services.impl;

import com.errand.dto.RatingDto;
import com.errand.models.*;
import com.errand.repository.ClientRepository;
import com.errand.repository.RatingRepository;
import com.errand.repository.UserRepository;
import com.errand.security.SecurityUtil;
import com.errand.services.RatingService;
import com.errand.mapper.RatingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.errand.mapper.RatingMapper.maptoRatingFromClient;

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
}
