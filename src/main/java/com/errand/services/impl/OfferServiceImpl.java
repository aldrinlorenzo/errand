package com.errand.services.impl;

import com.errand.dto.TaskDto;
import com.errand.models.Offer;
import com.errand.models.Task;
import com.errand.repository.OfferRepository;
import com.errand.services.OfferService;
import com.errand.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.errand.mapper.TaskMapper.mapToTask;

@Service
public class OfferServiceImpl implements OfferService {

    private OfferRepository offerRepository;
    private TaskService taskService;

    @Autowired
    public OfferServiceImpl(OfferRepository offerRepository, TaskService taskService) {
        this.offerRepository = offerRepository;
        this.taskService = taskService;
    }

    @Override
    public List<Offer> findOffersByTask(Task task) {
        return offerRepository.findOffersByTask(task);
    }

    @Override
    public void acceptOffer(Long offerId, TaskDto taskDto) {
        Task task = mapToTask(taskDto);
        //Reject offers except accepted offer
        List<Offer> offers = offerRepository.findOffersByTask(task);
        for (Offer offer : offers) {
            if (offer.getId() != offerId){
                rejectOffer(offer.getId());
            }
        }
        //Set Offer status to ACCEPTED
        Offer offer = offerRepository.findById(offerId).orElseThrow(() -> new RuntimeException("Offer not found"));
        offer.setStatus("ACCEPTED");
        offerRepository.save(offer);

        //Set the Task Status to Ongoing
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
