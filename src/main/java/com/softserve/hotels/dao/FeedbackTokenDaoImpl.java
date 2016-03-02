package com.softserve.hotels.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.softserve.hotels.model.Apartment;
import com.softserve.hotels.model.Feedback;
import com.softserve.hotels.model.FeedbackToken;

@Repository("feedbackTokenDao")
public class FeedbackTokenDaoImpl extends AbstractDaoImpl<FeedbackToken> implements FeedbackTokenDao {

    @Override
    public List<Integer> findAllForApartment(Apartment apartment) {
        TypedQuery<Integer> query = getEntityManager().createNamedQuery(FeedbackToken.NQ_FIND_ALL_FOR_APARTMENT,
                Integer.class);
        query.setParameter("id", apartment.getId());
        return query.getResultList();
    }

    @Override
    public FeedbackToken findByFeedback(Feedback feedback) {
        List<FeedbackToken> feedbackTokens = getEntityManager()
                .createNamedQuery(FeedbackToken.NQ_FIND_BY_FEEDBACK, FeedbackToken.class)
                .setParameter("id", feedback.getId()).getResultList();
        if (feedbackTokens.size() > 0)
            return feedbackTokens.get(0);
        else
            return null;
    }

    @Override
    public void deleteByFeedback(Feedback feedback) {
        FeedbackToken feedbackToken = findByFeedback(feedback);
        if (feedbackToken != null)
            deleteById(findByFeedback(feedback).getId());
    }

}
