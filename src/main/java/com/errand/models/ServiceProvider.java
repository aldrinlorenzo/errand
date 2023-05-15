package com.errand.models;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ServiceProviders")
public class ServiceProvider {

    @Id
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String contactNumber;

    private String businessName;

    @OneToOne
    @JoinColumn(name = "userId")
    private Users user;

    @OneToOne(mappedBy = "serviceProvider", cascade = CascadeType.ALL)
    private Offer offer;

    @OneToOne(mappedBy = "serviceProvider", cascade = CascadeType.ALL)
    private Rating rating;

}
