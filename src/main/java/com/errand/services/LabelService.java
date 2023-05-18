package com.errand.services;

import com.errand.dto.LabelDto;
import com.errand.models.Label;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface LabelService {

    Set<Label> findAllLabels();

    LabelDto findbyId(Long labelId);

    Label saveLabel(LabelDto labelDto);

    void editLabel(LabelDto labelDto);

}
