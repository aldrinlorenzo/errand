package com.errand.services;

import com.errand.dto.OfferDto;
import com.errand.dto.TaskDto;
import com.errand.models.Offer;
import com.errand.models.Task;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OfferService {

    List<Offer> findOffersByTask(Task task);

    Boolean isOfferExist(Long taskId , Long serviceProviderId);

    Boolean createOffer(OfferDto offerDto);

    Boolean updateOffer(OfferDto offerDto);

    void acceptOffer(Long offerId, TaskDto taskDto);

    void rejectOffer(Long id);

}
