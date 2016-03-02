package com.softserve.hotels.dao;

import java.util.List;

import com.softserve.hotels.model.PaymentToken;
import com.softserve.hotels.model.User;

public interface PaymentTokenDao extends AbstractDao<PaymentToken> {
    PaymentToken findByToken(String token);

    List<PaymentToken> findByUser(User user);
}
