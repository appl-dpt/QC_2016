package com.softserve.hotels.dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.softserve.hotels.model.PaymentMethod;

@Repository("paymentMethodDao")
public class PaymentMethodDaoImpl extends AbstractDaoImpl<PaymentMethod> implements PaymentMethodDao {
    
    public static final Logger LOG = LogManager.getLogger(UserDaoImpl.class);

    @Override
    public List<String> getAllPaymentsNames() {
        TypedQuery<String> query = getEntityManager().createNamedQuery(
                PaymentMethod.NQ_ALL_PAYMENTS_NAMES, String.class);
        return query.getResultList(); 
    }
    
    @Override
    public List<PaymentMethod> findAllEnabled() {
        TypedQuery<PaymentMethod> query = getEntityManager().createNamedQuery(
                PaymentMethod.NQ_ALL_ENABLED, PaymentMethod.class);
        return query.getResultList();
    }

    @Override
    public PaymentMethod findPaymentMethodByName(String name) {
        LOG.debug("Start");
        try {
            Query query = getEntityManager().createQuery("from PaymentMethod WHERE name = :name");
            query.setParameter("name", name);
            return (PaymentMethod) query.getSingleResult();
        } catch (NoResultException e) {
            LOG.info("Such paymentMethod is not found");
            LOG.debug(e);
            return null;
        }
    }

}
