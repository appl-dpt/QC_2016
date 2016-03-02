package com.softserve.hotels.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.softserve.hotels.dao.FeedbackTokenDao;
import com.softserve.hotels.model.Apartment;
import com.softserve.hotels.model.FeedbackToken;

@Service("feedbackTokenService")
@Transactional
public class FeedbackTokenServiceImpl extends AbstractServiceImpl<FeedbackToken> implements FeedbackTokenService {

    @Autowired
    private FeedbackTokenDao feedbackTokenDao;

    @Override
    public List<Integer> findAllForApartment(Apartment apartment) {
        return feedbackTokenDao.findAllForApartment(apartment);
    }

}
