package com.errand.dto;

import com.errand.models.Client;
import com.errand.models.Label;
import com.errand.models.Rating;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
public class TaskDto {

    private Long id;

    @NotNull
    @Pattern(regexp = "^(?!\\s*$)[a-zA-Z0-9!@#\\$%\\^\\&*\\)\\(+=._ -]+$", message = "Invalid Title")
    private String title;
    @NotNull
    @Pattern(regexp = "^(?!\\s*$)[a-zA-Z0-9!@#\\$%\\^\\&*\\)\\(+=._ -]+$", message = "Invalid Description")
    private String description;

    private BigDecimal budget;

    @NotNull
    @NotBlank
    private String street;

    @NotNull
    @NotBlank
    private String city;

    @NotNull
    private BigDecimal postalCode;

    private String status;

    private Long offerId;

    private OfferDto offerDto;

    @NotNull
    @NotBlank
    private String targetDate;

    private LocalDateTime completedDate;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

    @NotNull
    private Set<Label> labels;

    private String createdBy;

    private Rating rating;

    private Client client;


}
