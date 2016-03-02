package com.softserve.hotels.dao;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.softserve.hotels.model.Apartment;
import com.softserve.hotels.model.ApartmentConveniences;

@Repository("apartmentConveniencesDao")
public class ApartmentConveniencesDaoImpl extends AbstractDaoImpl<ApartmentConveniences> 
    implements ApartmentConveniencesDao {
    
    @Override
    public ApartmentConveniences findConvenianceForApartment(Apartment apartment, String convenience) {
    	TypedQuery<ApartmentConveniences> query = getEntityManager().createNamedQuery(
    	        ApartmentConveniences.NQ_FIND_CONVENIENCE_FOR_APARTMENT, ApartmentConveniences.class);
        query.setParameter("apartmentId", apartment.getId());
        query.setParameter("convenience", convenience);
        return (ApartmentConveniences) query.getSingleResult();
    }

}
