package com.errand.services;

import com.errand.dto.TaskDto;
import com.errand.models.Offer;
import com.errand.models.Task;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OfferService {

    List<Offer> findOffersByTask(Task task);

    void acceptOffer(Long offerId, TaskDto taskDto);

    void rejectOffer(Long id);

}
