package com.errand.services.impl;

import com.errand.dto.OfferDto;
import com.errand.dto.OfferStatisticDto;
import com.errand.dto.ServiceProviderDto;
import com.errand.dto.TaskDto;
import com.errand.mapper.OfferMapper;
import com.errand.mapper.ServiceProviderMapper;
import com.errand.mapper.TaskMapper;
import com.errand.models.Offer;
import com.errand.models.Task;
import com.errand.repository.OfferRepository;
import com.errand.services.OfferService;
import com.errand.services.TaskService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.errand.mapper.TaskMapper.mapToTask;

@Service
public class OfferServiceImpl implements OfferService {

    private final OfferRepository offerRepository;
    private final TaskService taskService;

    public OfferServiceImpl(OfferRepository offerRepository, TaskService taskService) {
        this.offerRepository = offerRepository;
        this.taskService = taskService;

    }

    @Override
    public List<OfferDto> findOffersByTask(TaskDto  taskDto) {
        if(taskDto == null){
            throw new IllegalArgumentException("Task parameter cannot be null.");
        }
        try{

            List<Offer> offerList = offerRepository.findOffersByTask(TaskMapper.mapToTask(taskDto));
            return offerList.stream().map(OfferMapper::mapToOfferDto).collect(Collectors.toList());
        }
        catch(RuntimeException ex){
            throw new RuntimeException("Error occurred while retrieving list of offers");
        }


    }

    @Override
    public OfferDto findOfferByTaskIdAndServiceProviderId(Long taskId, Long serviceProviderId) {
        if (taskId == null || serviceProviderId == null) {
            throw new IllegalArgumentException("Task ID and service provider ID cannot be null.");
        }

        Offer offer = offerRepository.findOfferByTaskAndServiceProvider(taskId, serviceProviderId);
        if (offer == null) {
            throw new RuntimeException("Offer not found for the specified task and service provider.");
        }

        return OfferMapper.mapToOfferDto(offer);
    }

    @Override
    public List<OfferDto> findOfferByServiceProvider(ServiceProviderDto serviceProviderDto) {
        if (serviceProviderDto == null) {
            throw new IllegalArgumentException("ServiceProviderDto parameter cannot be null.");
        }

        List<Offer> offerList = offerRepository.findByServiceProvider(ServiceProviderMapper.toServiceProvider(serviceProviderDto));
        return offerList.stream()
                .map(OfferMapper::mapToOfferDto)
                .collect(Collectors.toList());
    }

    @Override
    public Boolean isOfferExist(Long taskId, Long serviceProviderId) {
        return offerRepository.findOfferByTaskAndServiceProvider(taskId, serviceProviderId) != null;
    }

    @Override
    public Boolean createOffer(OfferDto offerDto) {
        try {
            if (isOfferExist(offerDto.getTaskDto().getId(), offerDto.getServiceProviderDto().getId())) {
                return false; // Offer already exists
            }

            Offer offer = OfferMapper.mapToOffer(offerDto);
            offerRepository.save(offer);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean updateOffer(OfferDto offerDto) {
        try {
            Offer existingOffer = offerRepository.findOfferByTaskAndServiceProvider(offerDto.getTaskDto().getId(), offerDto.getServiceProviderDto().getId());

            if (existingOffer == null) {
                return false;
            }
            // Update the existing offer
            existingOffer.setPrice(offerDto.getPrice());
            existingOffer.setDescription(offerDto.getDescription());
            existingOffer.setStatus(offerDto.getStatus());
            offerRepository.save(existingOffer);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    @Transactional
    public Boolean deleteOffer(Long offerId) throws Exception {
        try {
            offerRepository.deleteOfferById(offerId);
            offerRepository.flush();
            System.out.println("Deleted");
            return true;
        } catch (EmptyResultDataAccessException e) {
            throw new IllegalArgumentException("Offer not found with ID: " + offerId);

        }

    }

    @Override
    public OfferStatisticDto getOfferStatistic(ServiceProviderDto serviceProviderDto) {
        List<OfferDto> offerList = findOfferByServiceProvider(serviceProviderDto);
        long acceptedTotal = offerList.stream()
                .filter(offer -> offer.getStatus().equals("ACCEPTED"))
                .count();
        long pendingTotal = offerList.stream()
                .filter(offer -> offer.getStatus().equals("OFFERED"))
                .count();
        long rejectedTotal = offerList.stream()
                .filter(offer -> offer.getStatus().equals(""))
                .count();
        OfferStatisticDto offerStatisticDto = new OfferStatisticDto();
        offerStatisticDto.setTotalAcceptedOffer(acceptedTotal);
        offerStatisticDto.setTotalRejectedOffer(rejectedTotal);
        offerStatisticDto.setTotalPendingOffer(pendingTotal);

        return offerStatisticDto;
    }

    @Override
    public OfferDto findOfferById(Long id) {
        Offer offer = offerRepository.findById(id).orElseThrow(() -> new RuntimeException("Offer not found"));
        return OfferMapper.mapToOfferDto(offer);
    }

    @Override
    public void acceptOffer(Long offerId, TaskDto taskDto) {
        Task task = mapToTask(taskDto);
        //Reject offers except accepted offer
        List<Offer> offers = offerRepository.findOffersByTask(task);
        for (Offer offer : offers) {
            if (offer.getId() != offerId) {
                rejectOffer(offer.getId());
            }
        }
        //Set Offer status to ACCEPTED
        Offer offer = offerRepository.findById(offerId).orElseThrow(() -> new RuntimeException("Offer not found"));
        offer.setStatus("ACCEPTED");
        offerRepository.save(offer);
        //Set the Task Status to Ongoing
        task.setLabels(taskDto.getLabels());
        task.setOfferId(offerId);
        taskService.setStatusToOngoing(task);
    }

    @Override
    public void rejectOffer(Long id) {
        Optional<Offer> optionalOffer = offerRepository.findById(id);
        Offer offer = optionalOffer.orElseThrow(() -> new RuntimeException("Offer not found"));
        offer.setStatus("REJECTED");
        offerRepository.save(offer);
    }

}
