package com.softserve.hotels.service;

import java.util.List;

import com.softserve.hotels.model.Convenience;

public interface ConvenienceService extends AbstractService<Convenience> {

    void create(Convenience entity);

    List<String> getAllConvenienceNames();

}
