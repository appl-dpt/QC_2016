package com.softserve.hotels.dao;

import com.softserve.hotels.model.Apartment;
import com.softserve.hotels.model.ApartmentConveniences;

public interface ApartmentConveniencesDao extends AbstractDao<ApartmentConveniences> {
	
	ApartmentConveniences findConvenianceForApartment(Apartment apartment, String convenience);

}
