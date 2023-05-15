package com.errand.models;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Ratings")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private float clientRating;

    private String clientRatingDescription;

    private float serviceProviderRating;

    private String serviceProviderRatingDescription;

    @OneToOne
    @JoinColumn(name = "taskId")
    private Task task;

    @OneToOne
    @JoinColumn(name = "clientId")
    private Client client;

    @OneToOne
    @JoinColumn(name = "serviceProviderId")
    private ServiceProvider serviceProvider;

}
