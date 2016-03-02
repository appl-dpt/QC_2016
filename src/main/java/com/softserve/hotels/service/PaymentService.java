package com.softserve.hotels.service;

import java.util.List;

import com.softserve.hotels.model.PaymentToken;
import com.softserve.hotels.model.User;

public interface PaymentService extends AbstractService<PaymentToken> {
    @Override
    void create(PaymentToken entity);

    PaymentToken findByToken(String token);

    List<PaymentToken> findByUser(User user);
}
