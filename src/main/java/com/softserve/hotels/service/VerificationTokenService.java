package com.softserve.hotels.service;

import com.softserve.hotels.model.User;
import com.softserve.hotels.model.VerificationToken;

public interface VerificationTokenService extends AbstractService<VerificationToken> {
    VerificationToken findByUser(User user);

    VerificationToken findByToken(String token);

    void createToken(String token, User user);
}
