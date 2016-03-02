package com.softserve.hotels.dao;

import java.util.List;

import com.softserve.hotels.model.Convenience;

public interface ConvenienceDao extends AbstractDao<Convenience> {
	
	List<String> getAllConvenienceNames();

}
