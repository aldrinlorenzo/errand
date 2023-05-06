package com.errand.models;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Clients")
public class Client{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String contactNumber;

    @OneToOne
    @JoinColumn(name = "userId")
    private Users user;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Task> tasks = new ArrayList<>();

}
