package com.softserve.hotels.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softserve.hotels.dao.ApartmentPaymentDao;
import com.softserve.hotels.model.Apartment;
import com.softserve.hotels.model.ApartmentPayment;

@Service("apartmentPaymentService")
@Transactional
public class ApartmentPaymentServiceImpl extends AbstractServiceImpl<ApartmentPayment>
        implements ApartmentPaymentService {

    @Autowired
    private ApartmentPaymentDao apartmentPaymentDao;

    @Override
    public List<ApartmentPayment> getAvailableForApartment(Apartment apartment) {
            return apartmentPaymentDao.getAvailableForApartment(apartment);
    }

}
