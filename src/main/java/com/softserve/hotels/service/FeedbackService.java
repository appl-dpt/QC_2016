package com.softserve.hotels.service;

import com.softserve.hotels.model.Feedback;

public interface FeedbackService extends AbstractService<Feedback> {
    void complain(Feedback feedback);

    void deleteFeedback(Feedback feedback);
}
