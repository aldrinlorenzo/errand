package com.errand.services.impl;

import com.errand.models.Offer;
import com.errand.models.Task;
import com.errand.repository.OfferRepository;
import com.errand.services.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfferServiceImpl implements OfferService {

    private OfferRepository offerRepository;

    @Autowired
    public OfferServiceImpl(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    @Override
    public List<Offer> findOffersByTask(Task task) {
        return offerRepository.findOffersByTask(task);
    }
}
