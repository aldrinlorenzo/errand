package com.errand.models;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private BigDecimal budget;

    private String street;

    private String city;

    private BigDecimal postalCode;

    private String status;

    private Long offerId;

    private LocalDateTime targetDate;

    private LocalDateTime completedDate;

    @CreationTimestamp
    private LocalDateTime createdDate;

    @UpdateTimestamp
    private LocalDateTime modifiedDate;

    @ManyToOne
    @JoinColumn(name = "clientId")
    private Client client;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    private List<Offer> offers = new ArrayList<>();

    @OneToOne(mappedBy = "task", cascade = CascadeType.ALL)
    private Rating rating;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "Task_Labels",
            joinColumns = {@JoinColumn(name ="task_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "label_id", referencedColumnName = "id")}
    )
    private Set<Label> labels = new HashSet<>();

}
