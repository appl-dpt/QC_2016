package com.softserve.hotels.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.softserve.hotels.dao.FeedbackDao;
import com.softserve.hotels.dao.FeedbackTokenDao;
import com.softserve.hotels.model.Feedback;
import com.softserve.hotels.model.FeedbackToken;

@Service("feedbackService")
@Transactional
public class FeedbackServiceImpl extends AbstractServiceImpl<Feedback> implements FeedbackService {
    @Autowired
    private FeedbackDao feedbackDao;

    @Autowired
    private FeedbackTokenDao feedbackTokenDao;

    @Override
    public void complain(Feedback feedback) {
        FeedbackToken feedbackToken = new FeedbackToken();
        feedbackToken.setFeedback(feedback);
        feedbackTokenDao.create(feedbackToken);
    }

    @Override
    public void deleteFeedback(Feedback feedback) {
        feedbackTokenDao.deleteByFeedback(feedback);
        feedback.setEnabled(false);
        feedbackDao.update(feedback);
    }

}
