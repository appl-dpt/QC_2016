package com.softserve.hotels.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.softserve.hotels.model.Apartment;
import com.softserve.hotels.model.ApartmentPayment;

@Repository("apartmentPaymentDao")
public class ApartmentPaymentDaoImpl extends AbstractDaoImpl<ApartmentPayment> implements ApartmentPaymentDao {

    @Override
    public List<ApartmentPayment> getAvailableForApartment(Apartment apartment) {
        TypedQuery<ApartmentPayment> query = getEntityManager().createNamedQuery(ApartmentPayment.NQ_GET_AVAILABLE,
                ApartmentPayment.class);
        query.setParameter("apartmentId", apartment.getId());
        return query.getResultList();
      
    }

}
