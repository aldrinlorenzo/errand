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
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String contactNumber;

    private String profileImageFileName;

    @OneToOne
    @JoinColumn(name = "userId")
    private Users user;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Task> tasks = new ArrayList<>();

    @OneToOne(mappedBy = "client", cascade = CascadeType.ALL)
    private Rating rating;

}
