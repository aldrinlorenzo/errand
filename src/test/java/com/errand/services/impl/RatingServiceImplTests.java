package com.errand.services.impl;

import com.errand.dto.RatingDto;
import com.errand.mapper.RatingMapper;
import com.errand.mapper.ServiceProviderMapper;
import com.errand.models.*;
import com.errand.repository.ClientRepository;
import com.errand.repository.RatingRepository;
import com.errand.repository.UserRepository;
import com.errand.security.SecurityUtil;
import com.errand.services.RatingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RatingServiceImplTests {
    @Mock
    private UserRepository userRepository;
    @Mock
    private ClientRepository clientRepository;
    @Mock
    private RatingRepository ratingRepository;

    private RatingService ratingService;

    private Rating rating;

    private Task task;

    private Client client;

    private ServiceProvider serviceProvider;

    private Users user;

    private Label label;

    @BeforeEach
    void initTests() {
        ratingService = new RatingServiceImpl(
                userRepository,
                clientRepository,
                ratingRepository
        );
        rating = new Rating();

        user = new Users();
        user.setId(1L);
        user.setUsername("username");

        client = new Client();
        client.setUser(user);
        client.setId(1L);

        serviceProvider = new ServiceProvider();
        serviceProvider.setUser(user);
        serviceProvider.setId(1L);

        label = new Label();
        label.setName("label");
        Set<Label> labelList = new HashSet<>();
        labelList.add(label);

        task = new Task();
        task.setId(1L);
        task.setTitle("title");
        task.setDescription("description");
        task.setBudget(new BigDecimal(1000));
        task.setStreet("street");
        task.setCity("city");
        task.setPostalCode(new BigDecimal(1000));
        task.setClient(client);
        task.setStatus("COMPLETED");
        task.setOfferId(1L);
        task.setTargetDate(LocalDate.now());
        task.setCompletedDate(LocalDateTime.now());
        task.setLabels(labelList);
        task.setCreatedDate(LocalDateTime.now());
        task.setModifiedDate(LocalDateTime.now());

        rating.setServiceProviderRating(5F);
        rating.setServiceProviderRatingDescription("description");
        rating.setTask(task);
        rating.setClient(client);
        rating.setServiceProvider(serviceProvider);
    }

    @Test
    public void testSaveRateFromClient() {
        when(userRepository.findByUsername(any())).thenReturn(user);
        when(ratingRepository.save(any())).thenReturn(rating);
        try(MockedStatic<SecurityUtil> mockedStatic = mockStatic(SecurityUtil.class)) {
            mockedStatic.when(SecurityUtil::getSessionUser).thenReturn("username");
            assert(rating.equals(ratingService
                    .saveRateFromClient(RatingMapper.mapToRatingDtoFromClient(rating))));
        }
    }

    @Test
    public void testSaveRateFromServiceProvider() {
        when(userRepository.findByUsername(any())).thenReturn(user);
        when(ratingRepository.save(any())).thenReturn(rating);
        try(MockedStatic<SecurityUtil> mockedStatic = mockStatic(SecurityUtil.class)) {
            mockedStatic.when(SecurityUtil::getSessionUser).thenReturn("username");
            assert(rating.equals(ratingService
                    .saveRateFromServiceProvider(RatingMapper.maptoRatingDtoFromServiceProvider(rating))));
        }
    }

    @Test
    public void testGetRatingByTask() {
        when(ratingRepository.findByTaskId(any())).thenReturn(rating);
        assert(rating.equals(ratingService.getRatingByTask(task)));
    }

    @Test
    public void testGetRatingByServiceProvider() {
        List<Rating> ratingList = new ArrayList<>();
        ratingList.add(rating);
        List<RatingDto> ratingDtoList = new ArrayList<>();
        ratingDtoList.add(RatingMapper.mapToRatingDtoFromClient(rating));
        when(ratingRepository.findByServiceProvider(any())).thenReturn(ratingList);
        when(ratingRepository.findRatingsByServiceProvider(any())).thenReturn(ratingList);
        assert(ratingList
                .equals(
                    ratingService.getRatingByServiceProvider(ServiceProviderMapper.toServiceProviderDto(serviceProvider))
                ));
        assert(ratingDtoList
                .equals(
                        ratingService.getRatingsByServiceProvider(serviceProvider)
                ));

    }

    @Test
    public void testGetRatingByClient() {
        List<Rating> ratingList = new ArrayList<>();
        List<RatingDto> ratingDtoList = new ArrayList<>();
        ratingList.add(rating);
        ratingDtoList.add(RatingMapper.maptoRatingDtoFromServiceProvider(rating));
        when(ratingRepository.findRatingsByClient(any())).thenReturn(ratingList);
        assert(ratingDtoList
                .equals(
                        ratingService.getRatingsByClient(client)
                ));
    }
}
