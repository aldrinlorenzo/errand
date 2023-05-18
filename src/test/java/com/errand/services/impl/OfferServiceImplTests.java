package com.errand.services.impl;

import java.lang.reflect.Executable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import com.errand.dto.OfferDto;
import com.errand.dto.OfferStatisticDto;
import com.errand.dto.ServiceProviderDto;
import com.errand.dto.TaskDto;
import com.errand.mapper.OfferMapper;
import com.errand.mapper.ServiceProviderMapper;
import com.errand.mapper.TaskMapper;
import com.errand.models.*;
import com.errand.repository.ClientRepository;
import com.errand.repository.OfferRepository;
import com.errand.repository.TaskRepository;
import com.errand.repository.UserRepository;
import com.errand.security.SecurityUtil;
import com.errand.services.ClientService;
import com.errand.services.OfferService;
import com.errand.services.TaskService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.persistence.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class OfferServiceImplTests {
    @InjectMocks
    private OfferServiceImpl offerService;

    @Mock
    private OfferRepository offerRepository;
    @Mock
    private TaskService taskService;
    @Mock
    private TaskMapper taskMapper;
    @Mock
    private OfferMapper offerMapper;
    @Mock
    private OfferDto offerDto;
    @Mock
    private TaskDto taskDto;
    @Mock
    private TaskRepository taskRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ClientRepository clientRepository;
    @Mock
    private ClientService clientService;

    @BeforeEach
    void setUp() {
        taskService = new TaskServiceImpl(
                taskRepository,
                userRepository,
                clientRepository,
                clientService,
                taskMapper
        );
        MockitoAnnotations.openMocks(this);
        offerService = new OfferServiceImpl(offerRepository, taskService);
        Set<Label> labels = new HashSet<>();
        TaskDto taskDto = TaskDto.builder().
                id(1L).title("Fix Pc")
                .description("Hotdog")
                .budget(new BigDecimal("100.00"))
                .street("Street").city("City")
                .postalCode(new BigDecimal("12345"))
                .status("Status")
                .offerId(2L)
                .offerDto(null)
                .targetDate("2023-05-16")
                .completedDate(LocalDateTime.now())
                .createdDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now())
                .labels(labels).createdBy("User")
                .rating(null)
                .build();

    }

    @Test
    public void findOffersByTask_NotNullTask_ShouldReturnAList() {
        Users user = new Users();
        Client client = new Client();
        client.setUser(user);
        ServiceProvider serviceProvider = new ServiceProvider();
        Task task = new Task();
        task.setTargetDate(LocalDate.now());
        task.setClient(client);
        Offer offer = new Offer();
        offer.setTask(task);
        offer.setServiceProvider(serviceProvider);
        List<Offer> offerList = new ArrayList<>();
        offerList.add(offer);
        when(offerRepository.findOffersByTask(any(Task.class))).thenReturn(offerList);

        //Act
        List<OfferDto> actual = offerService.findOffersByTask(TaskMapper.mapToTaskDto(task));

        // Assert
        Assertions.assertNotNull(actual);
        Assertions.assertTrue(actual.stream().allMatch(Objects::nonNull));
    }

    @Test
    public void findOffersByTask_NullTask_ShouldThrowAnIllegalArgumentException() {
        //Arrange
        TaskDto taskDto = null;

        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            offerService.findOffersByTask(taskDto);
        });
        String expectedMessage = "Task parameter cannot be null.";
        String actualMessage = exception.getMessage();


        //Assert
        assertTrue(actualMessage.contains(expectedMessage));


    }

    @Test
    public void findOffersByTask_validTask_ThrowsRunTimeException() {

        when(offerRepository.findOffersByTask(any(Task.class))).thenThrow(new RuntimeException("Test Exception"));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> {
            offerService.findOffersByTask(taskDto);
        });
    }

    @Test
    public void findOffersByTaskIdAndServiceProviderId_ValidTaskIdAndValidServiceProviderId_ReturnOfferDto() {
        Long taskId = 1L;
        Long serviceProviderId = 1L;
        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.setId(1L);

        Task task = new Task();
        task.setId(1L);
        try (MockedStatic<TaskMapper> mockedStatic = mockStatic(TaskMapper.class)) {
            Offer expectedOffer = new Offer(1L, new BigDecimal(12), "I can do it", "OFFERED", serviceProvider, task);
            when(offerRepository.findOfferByTaskAndServiceProvider(taskId, serviceProviderId)).thenReturn(expectedOffer);

            //Act
            OfferDto result = offerService.findOfferByTaskIdAndServiceProviderId(taskId, serviceProviderId);

            //Assert

            assertNotNull(offerDto);
        }
        ;


    }

    @Test
    public void findOffersByTaskIdAndServiceProviderId_InvalidParameters_ThrowIllegalArgumentException() {


        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            offerService.findOfferByTaskIdAndServiceProviderId(null, null);
        });
        String expectedMessage = "Task ID and service provider ID cannot be null.";
        String actualMessage = exception.getMessage();


        assertTrue(expectedMessage.contains(actualMessage));
    }

    @Test
    public void findOfferByTaskIdAndServiceProviderId_validParametersButNoOffer_ThrowRunTimeException() {

        when(offerRepository.findOfferByTaskAndServiceProvider(anyLong(), anyLong())).thenReturn(null);

        // Act and Assert
        assertThrows(RuntimeException.class, () -> {
            offerService.findOfferByTaskIdAndServiceProviderId(anyLong(), anyLong());
        });


    }

    @Test
    public void findOfferByServiceProvider_NotNullServiceProvider_ReturnOfferDtoList() {

        ServiceProviderDto serviceProviderDto = ServiceProviderDto.builder()
                .id(1L)
                .firstName("firstName")
                .lastName("lastName")
                .email("email")
                .contactNumber("contactNumber")
                .businessName("businessName").build();

        try (MockedStatic<ServiceProviderMapper> mockedStatic = mockStatic(ServiceProviderMapper.class)) {
            List<Offer> expectedOfferList = new ArrayList<>();
            when(offerRepository.findByServiceProvider(any(ServiceProvider.class)))
                    .thenReturn(expectedOfferList);

            List<OfferDto> result = offerService.findOfferByServiceProvider(serviceProviderDto);


            assertNotNull(result);
            assertEquals(expectedOfferList.size(), result.size());
        }

    }

    @Test
    public void findOfferByServiceProvider_NullServiceProvider_ThrowIllegalArgumentException() {


        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            offerService.findOfferByServiceProvider(null);
        });

        String expectedMessage = "ServiceProviderDto parameter cannot be null.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }


    @Test
    public void isOfferExist_ValidParameters_ReturnTrue() {
        long taskId = 1L;
        long serviceProviderId = 1L;


        when(offerRepository.findOfferByTaskAndServiceProvider(taskId, serviceProviderId))
                .thenReturn(mock(Offer.class));

        // Act
        boolean result = offerService.isOfferExist(taskId, serviceProviderId);

        // Assert
        assertTrue(result);
    }

    @Test
    public void isOfferExist_ValidParameters_ReturnFalse() {
        long taskId = 1L;
        long serviceProviderId = 1L;


        when(offerRepository.findOfferByTaskAndServiceProvider(taskId, serviceProviderId))
                .thenReturn(null);

        // Act
        boolean result = offerService.isOfferExist(taskId, serviceProviderId);

        // Assert
        assertFalse(result);
    }

    @Test
    public void createOffer_ValidOfferDto_ReturnTrue() {
        ServiceProviderDto serviceProviderDto = ServiceProviderDto.builder()
                .id(1L)
                .firstName("firstName")
                .lastName("lastName")
                .email("email")
                .contactNumber("contactNumber")
                .businessName("businessName").build();

        OfferDto offerDto = OfferDto.builder()
                .id(1L)
                .price(new BigDecimal(23))
                .description("Hello")
                .status("OFFERED")
                .taskDto(taskDto)
                .serviceProviderDto(serviceProviderDto)
                .build();
        when(offerRepository.findOfferByTaskAndServiceProvider(
                offerDto.getTaskDto().getId(), offerDto.getServiceProviderDto().getId()))
                .thenReturn(new Offer());
        // Act
        boolean result = offerService.createOffer(offerDto);

        // Assert
        assertFalse(result);
    }


    @Test
    public void updateOffer_ValidOfferDto_ReturnTrue() {
        ServiceProviderDto serviceProviderDto = ServiceProviderDto.builder()
                .id(1L)
                .firstName("firstName")
                .lastName("lastName")
                .email("email")
                .contactNumber("contactNumber")
                .businessName("businessName").build();


        OfferDto offerDto = OfferDto.builder()
                .id(1L)
                .price(new BigDecimal(23))
                .description("Hello")
                .status("OFFERED")
                .taskDto(taskDto)
                .serviceProviderDto(serviceProviderDto)
                .build();
        Offer existingOfferMock = mock(Offer.class);


        when(offerRepository.findOfferByTaskAndServiceProvider(offerDto.getTaskDto().getId(), offerDto.getServiceProviderDto().getId()))
                .thenReturn(existingOfferMock);


        boolean result = offerService.updateOffer(offerDto);

        verify(offerRepository).findOfferByTaskAndServiceProvider(offerDto.getTaskDto().getId(), offerDto.getServiceProviderDto().getId());
        verify(existingOfferMock).setPrice(offerDto.getPrice());
        verify(existingOfferMock).setDescription(offerDto.getDescription());
        verify(existingOfferMock).setStatus(offerDto.getStatus());
        verify(offerRepository).save(existingOfferMock);


        assertTrue(result);
    }

    @Test
    public void deleteOffer_ValidId_ReturnTrue() throws Exception {

        Long offerId = 1L;

        doNothing().when(offerRepository).deleteOfferById(offerId);

        boolean result = offerService.deleteOffer(offerId);
        verify(offerRepository).deleteOfferById(offerId);


        verify(offerRepository).flush();

        // Assert that the result is true
        assertTrue(result);
    }

    @Test
    public void deleteOffer_InvalidId_ThrowEmptyResultDataAccessException() throws Exception {
        doThrow(EmptyResultDataAccessException.class).when(offerRepository).deleteOfferById(anyLong());

        assertThrows(IllegalArgumentException.class, () -> {
            offerService.deleteOffer(anyLong());
        });
    }

    @Test
    public void getOfferStatistic_NoParameter_ReturnCorrectStatistic() {
        ServiceProviderDto serviceProviderDto = ServiceProviderDto.builder()
                .id(1L)
                .firstName("firstName")
                .lastName("lastName")
                .email("email")
                .contactNumber("contactNumber")
                .businessName("businessName").build();

        when(offerService.findOfferByServiceProvider(Mockito.mock(ServiceProviderDto.class))).thenReturn(Collections.emptyList());

        // Call the method under test
        OfferStatisticDto result = offerService.getOfferStatistic(serviceProviderDto);

        assertEquals(0, result.getTotalAcceptedOffer());
        assertEquals(0, result.getTotalPendingOffer());
        assertEquals(0, result.getTotalRejectedOffer());
    }

    @Test
    public void findbyOfferId_ValidParameters_ReturnOfferDto() {
        Long offerId = 1L;
        Offer offer = new Offer();
        offer.setId(offerId);
        try (MockedStatic<OfferMapper> mockedStatic = mockStatic(OfferMapper.class)) {
            OfferDto expectedOfferDto = OfferMapper.mapToOfferDto(offer);
            when(offerRepository.findById(offerId)).thenReturn(Optional.of(offer));

            // Call the method under test
            OfferDto result = offerService.findOfferById(offerId);

            // Verify the result
            assertEquals(expectedOfferDto, result);
        }


    }

    @Test
    public void acceptOffer_ValidId() {
        Long offerId = 1L;
        Offer offer = new Offer();
        offer.setId(offerId);
        when(offerRepository.findById(offerId)).thenReturn(Optional.of(offer));
        List<Offer> offers = new ArrayList<>();
        offers.add(offer);
        Task task = new Task();
        when(offerRepository.findOffersByTask(any(Task.class))).thenReturn(offers);

        try (MockedStatic<TaskMapper> mockedStatic = mockStatic(TaskMapper.class)) {

            mockedStatic.when(() -> TaskMapper.mapToTask(any(TaskDto.class))).thenReturn(task);


            offerService.acceptOffer(offerId, taskDto);


            verify(offerRepository).save(offer);
            assertEquals("ACCEPTED", offer.getStatus());

            verify(taskService).setStatusToOngoing(task);
        }
    }
    @Test
    public void rejecttOffer_ValidId() {
        Long offerId = 1L;
        Offer offer = new Offer();
        offer.setId(offerId);
        when(offerRepository.findById(offerId)).thenReturn(Optional.of(offer));


        offerService.rejectOffer(offerId);


        verify(offerRepository).save(offer);
        assertEquals("REJECTED", offer.getStatus());
    }
}





