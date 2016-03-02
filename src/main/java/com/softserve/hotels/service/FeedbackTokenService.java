package com.softserve.hotels.service;

import java.util.List;

import com.softserve.hotels.model.Apartment;
import com.softserve.hotels.model.FeedbackToken;

public interface FeedbackTokenService extends AbstractService<FeedbackToken> {

    List<Integer> findAllForApartment(Apartment apartment);

}
