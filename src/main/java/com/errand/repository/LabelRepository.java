package com.errand.repository;

import com.errand.models.Label;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LabelRepository extends JpaRepository<Label,Long> {

    Label findByName(String name);

}
