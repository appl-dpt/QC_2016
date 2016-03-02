package com.softserve.hotels.service;

import com.softserve.hotels.model.PhotoExtention;

public interface PhotoExtentionService extends AbstractService<PhotoExtention> {
    @Override
    void create(PhotoExtention entity);

}
