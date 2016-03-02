package com.softserve.hotels.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.softserve.hotels.dao.ApartmentDAO;
import com.softserve.hotels.dao.ApartmentPaymentDao;
import com.softserve.hotels.dao.PaymentMethodDao;
import com.softserve.hotels.model.Apartment;
import com.softserve.hotels.model.ApartmentPayment;
import com.softserve.hotels.model.PaymentMethod;

@Service("paymentMethodService")
@Transactional
public class PaymentMethodServiceImpl extends AbstractServiceImpl<PaymentMethod> implements PaymentMethodService {

    @Autowired
    private PaymentMethodDao paymentMethodDao;
    
    @Autowired
    private ApartmentDAO apartmentDao;
    
    @Autowired
    private ApartmentPaymentDao apartmentPaymentDao;
    
    @Override
    public List<String> getAllPaymentsNames() {
        List<PaymentMethod> payments = paymentMethodDao.findAll();
        List<String> names = new ArrayList<>();
        for (PaymentMethod payment : payments) {
            names.add(payment.getName());
        }
        return names;
    }
    
    @Override
    public List<String> getAllEnabledPaymentsNames() {
        List<PaymentMethod> payments = paymentMethodDao.findAllEnabled();
        List<String> names = new ArrayList<>();
        for (PaymentMethod payment : payments) {
            names.add(payment.getName());
        }
        return names;
    }
    
    @Override
    public void create(PaymentMethod entity) {
        super.create(entity);
        for (Apartment apartment : apartmentDao.findAll()) {
            ApartmentPayment apPayment = new ApartmentPayment();
            apPayment.setApartment(apartment);
            apPayment.setPaymentMethod(entity);
            apPayment.setExists(false);
            apartmentPaymentDao.create(apPayment);
        }
    }

    @Override
    public PaymentMethod findPaymentMethodByName(String name) {
        return paymentMethodDao.findPaymentMethodByName(name);
    }

}
