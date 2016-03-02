package com.softserve.hotels.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.softserve.hotels.dao.VerificationTokenDao;
import com.softserve.hotels.model.User;
import com.softserve.hotels.model.VerificationToken;

@Service
@Transactional
public class VerificationTokenServiceImpl extends AbstractServiceImpl<VerificationToken>
        implements VerificationTokenService {

    @Autowired
    private VerificationTokenDao tokenDao;

    @Override
    public VerificationToken findByUser(User user) {
        return tokenDao.findByUser(user);
    }

    @Override
    public VerificationToken findByToken(String token) {
        return tokenDao.findByToken(token);
    }

    @Override
    public void createToken(String token, User user) {
        VerificationToken verificationToken = new VerificationToken(user, token);
        tokenDao.create(verificationToken);
    }

}
