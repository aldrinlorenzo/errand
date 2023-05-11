package com.errand.services.impl;

import com.errand.models.Label;
import com.errand.repository.ClientRepository;
import com.errand.repository.LabelRepository;
import com.errand.services.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class LabelServiceImpl implements LabelService {

    private LabelRepository labelRepository;

    @Autowired
    public LabelServiceImpl(LabelRepository labelRepository) {
        this.labelRepository = labelRepository;
    }

    @Override
    public Set<Label> findAllLabels() {
        return new HashSet<>(labelRepository.findAll());
    }

}
