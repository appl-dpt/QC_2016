package com.softserve.hotels.dao;

import java.util.List;

import com.softserve.hotels.model.Apartment;
import com.softserve.hotels.model.ApartmentPayment;

public interface ApartmentPaymentDao extends AbstractDao<ApartmentPayment> {
    
    List<ApartmentPayment> getAvailableForApartment(Apartment apartment);

}
