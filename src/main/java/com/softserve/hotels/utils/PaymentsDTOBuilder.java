package com.softserve.hotels.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.softserve.hotels.dto.ApartmentPaymentDto;
import com.softserve.hotels.model.Apartment;
import com.softserve.hotels.service.PaymentMethodService;

@Component
public class PaymentsDTOBuilder {
    
        @Autowired
        private PaymentMethodService paymentMethodService;

        public ApartmentPaymentDto build(Apartment apartment) {
            ApartmentPaymentDto apd = new ApartmentPaymentDto();
            apd.setId(apartment.getId());
            apd.setPrice(apartment.getPrice());
            apd.setPayments(paymentMethodService.getAllEnabledPaymentsNames());
            return apd;
        }

}
