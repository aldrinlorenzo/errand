package com.errand.services.impl;

import com.errand.dto.LabelDto;
import com.errand.models.Label;
import com.errand.repository.LabelRepository;
import com.errand.services.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

import static com.errand.mapper.LabelMapper.toLabel;
import static com.errand.mapper.LabelMapper.toLabelDto;

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

    @Override
    public LabelDto findbyId(Long labelId) {
        Label label = labelRepository.findById(labelId).get();
        return toLabelDto(label);
    }

    @Override
    public Label saveLabel(LabelDto labelDto) {
        Label label = toLabel(labelDto);
        return labelRepository.save(label);
    }

    @Override
    public void editLabel(LabelDto labelDto) {
        Label label = toLabel(labelDto);
        labelRepository.save(label);
    }

}
