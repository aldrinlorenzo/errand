package com.errand.seeder;

import com.errand.models.Label;
import com.errand.repository.LabelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(5)
public class LabelSeeder implements CommandLineRunner {

    @Autowired
    private LabelRepository labelRepository;

    @Override
    public void run(String... args) throws Exception {
        if (labelRepository.count() == 0) {
            Label softwareLabel = new Label();
            softwareLabel.setName("SOFTWARE");
            Label photographyLabel = new Label();
            photographyLabel.setName("PHOTOGRAPHY");
            Label foodLabel = new Label();
            foodLabel.setName("FOOD");
            Label repairLabel = new Label();
            repairLabel.setName("REPAIR");
            Label cleaningLabel = new Label();
            cleaningLabel.setName("CLEANING");

            labelRepository.save(softwareLabel);
            labelRepository.save(photographyLabel);
            labelRepository.save(foodLabel);
            labelRepository.save(repairLabel);
            labelRepository.save(cleaningLabel);
        }
    }

}
