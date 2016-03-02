package com.softserve.hotels.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.softserve.hotels.dto.ApartmentConveniencesDto;
import com.softserve.hotels.model.Apartment;
import com.softserve.hotels.service.ConvenienceService;

@Component
public class ConvenienceDTOBuilder {

    @Autowired
    private ConvenienceService convenienceService;

    public ApartmentConveniencesDto build(Apartment apartment) {
        ApartmentConveniencesDto acd = new ApartmentConveniencesDto();
        acd.setId(apartment.getId());
        acd.setMaxCountGuests(apartment.getMaxCountGuests());
        acd.setConveniences(convenienceService.getAllConvenienceNames());
        return acd;
    }

}
