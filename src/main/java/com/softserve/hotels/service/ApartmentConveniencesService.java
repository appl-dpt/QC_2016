package com.softserve.hotels.service;

import com.softserve.hotels.model.Apartment;
import com.softserve.hotels.model.ApartmentConveniences;

public interface ApartmentConveniencesService extends AbstractService<ApartmentConveniences> {

    ApartmentConveniences findConvenianceForApartment(Apartment apartment, String convenience);

    void resetAllConveniencesForApartment(Apartment apartment);

}
