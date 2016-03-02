package com.softserve.hotels.service;

import java.util.List;

import com.softserve.hotels.model.Apartment;
import com.softserve.hotels.model.ApartmentPayment;

public interface ApartmentPaymentService extends AbstractService<ApartmentPayment> {
    List<ApartmentPayment> getAvailableForApartment(Apartment apartment);
}
