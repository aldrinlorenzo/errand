package com.errand.services;

import com.errand.models.Client;
import com.errand.models.Label;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface LabelService {

    Set<Label> findAllLabels();
}
