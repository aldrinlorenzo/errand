package com.errand.models;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Offers")
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private float price;

    private String description;

    @OneToOne
    @JoinColumn(name = "serviceProviderId")
    private ServiceProvider serviceProvider;

    @ManyToOne
    @JoinColumn(name = "taskId")
    private Task task;

}
