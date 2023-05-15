package com.errand.dto;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class OfferStatisticDto {

    private long totalPendingOffer;

    private long totalAcceptedOffer;

    private long totalRejectedOffer;
}
