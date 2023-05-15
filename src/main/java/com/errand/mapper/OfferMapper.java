package com.errand.mapper;

import com.errand.dto.OfferDto;
import com.errand.models.Offer;
import org.springframework.stereotype.Component;

@Component
public class OfferMapper {


    public static Offer mapToOffer(OfferDto offerDto){
        return Offer.builder()
                .price(offerDto.getPrice())
                .description(offerDto.getDescription())
                .status(offerDto.getStatus())
                .task(TaskMapper.mapToTask(offerDto.getTaskDto()))
                .serviceProvider(ServiceProviderMapper.toServiceProvider(offerDto.getServiceProviderDto()))
                .build();
    }

    public static OfferDto mapToOfferDto(Offer offer){
        return OfferDto.builder()
                .price(offer.getPrice())
                .description(offer.getDescription())
                .status(offer.getStatus())
                .taskDto(TaskMapper.mapToTaskDto(offer.getTask()))
                .serviceProviderDto(ServiceProviderMapper.toServiceProviderDto(offer.getServiceProvider()))
                .build();
    }




}
