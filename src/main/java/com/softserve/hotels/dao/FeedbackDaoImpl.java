package com.softserve.hotels.dao;

import org.springframework.stereotype.Repository;

import com.softserve.hotels.model.Feedback;

@Repository("feedbackDao")
public class FeedbackDaoImpl extends AbstractDaoImpl<Feedback> implements FeedbackDao {
    
}
