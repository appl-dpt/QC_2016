package com.softserve.hotels.dao;

import java.util.List;

import com.softserve.hotels.model.Apartment;
import com.softserve.hotels.model.Feedback;
import com.softserve.hotels.model.FeedbackToken;

public interface FeedbackTokenDao extends AbstractDao<FeedbackToken> {

    List<Integer> findAllForApartment(Apartment apartment);

    void deleteByFeedback(Feedback feedback);

    FeedbackToken findByFeedback(Feedback feedback);

}
