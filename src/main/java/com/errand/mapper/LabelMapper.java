package com.errand.mapper;

import com.errand.dto.LabelDto;
import com.errand.dto.TaskDto;
import com.errand.models.Label;
import com.errand.models.Task;

public class LabelMapper {

    public static LabelDto toLabelDto(Label label){
        LabelDto.LabelDtoBuilder builder = LabelDto.builder()
                .id(label.getId())
                .name(label.getName());

        if(builder.tasks(label.getTasks()) != null){
            LabelDto.builder().tasks(label.getTasks());
        }

        return builder.build();
    }

    public static Label toLabel(LabelDto labelDto){
        Label.LabelBuilder builder = Label.builder()
                .id(labelDto.getId())
                .name(labelDto.getName());

        if(builder.tasks(labelDto.getTasks()) != null){
            Label.builder().tasks(labelDto.getTasks());
        }

        return builder.build();
    }
}
