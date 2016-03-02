package com.softserve.hotels.dao;

import java.util.List;

import com.softserve.hotels.model.PaymentMethod;

public interface PaymentMethodDao extends AbstractDao<PaymentMethod> {
    
    List<String> getAllPaymentsNames();
    
    List<PaymentMethod> findAllEnabled();
    
    PaymentMethod findPaymentMethodByName(String name);

}
