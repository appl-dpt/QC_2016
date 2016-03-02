package com.softserve.hotels.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.softserve.hotels.model.Convenience;

@Repository("convenienceDao")
public class ConvenienceDaoImpl extends AbstractDaoImpl<Convenience> implements ConvenienceDao {

	@Override
	public List<String> getAllConvenienceNames() {
		TypedQuery<String> query = getEntityManager().createNamedQuery(
				Convenience.NQ_ALL_CONVENIENCE_NAMES, String.class);
		return query.getResultList(); 
	}

}
