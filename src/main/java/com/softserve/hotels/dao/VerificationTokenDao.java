package com.softserve.hotels.dao;

import com.softserve.hotels.model.User;
import com.softserve.hotels.model.VerificationToken;

public interface VerificationTokenDao extends AbstractDao<VerificationToken> {
    VerificationToken findByUser(User user);

    VerificationToken findByToken(String token);
}
