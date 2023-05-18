package com.errand.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OfferStatisticDto {

    private long totalPendingOffer;

    private long totalAcceptedOffer;

    private long totalRejectedOffer;

}
