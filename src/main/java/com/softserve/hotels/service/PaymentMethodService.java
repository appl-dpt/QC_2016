package com.softserve.hotels.service;

import java.util.List;

import com.softserve.hotels.model.PaymentMethod;
import com.softserve.hotels.model.User;

public interface PaymentMethodService extends AbstractService<PaymentMethod> {
    
    void create(PaymentMethod entity);

    List<String> getAllPaymentsNames();
    
    List<String> getAllEnabledPaymentsNames();
    
    PaymentMethod findPaymentMethodByName(String name);

}
