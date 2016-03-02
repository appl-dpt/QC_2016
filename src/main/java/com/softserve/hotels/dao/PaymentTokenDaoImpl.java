package com.softserve.hotels.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.softserve.hotels.model.PaymentToken;
import com.softserve.hotels.model.User;

@Repository("paymentTokenDao")
public class PaymentTokenDaoImpl extends AbstractDaoImpl<PaymentToken> implements PaymentTokenDao {

    @Override
    public PaymentToken findByToken(String token) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<PaymentToken> query = criteriaBuilder.createQuery(PaymentToken.class);
        Root<PaymentToken> root = query.from(PaymentToken.class);
        query.select(root);
        query.where(criteriaBuilder.equal(root.<PaymentToken>get("token"), token));
        return getEntityManager().createQuery(query).getSingleResult();
    }

    @Override
    public List<PaymentToken> findByUser(User user) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<PaymentToken> query = criteriaBuilder.createQuery(PaymentToken.class);
        Root<PaymentToken> root = query.from(PaymentToken.class);
        query.select(root);
        Predicate predicate = criteriaBuilder.equal(root.<User>get("reserved").<User>get("tenant"), user);
        query.where(predicate);
        return getEntityManager().createQuery(query).getResultList();
    }

}
