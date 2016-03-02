package com.softserve.hotels.service;

import com.softserve.hotels.model.LinkPhoto;

public interface LinkPhotoService extends AbstractService<LinkPhoto> {

    @Override
    void create(LinkPhoto photo);

    byte[] getPhotoDataById(Integer id);

    @Override
    void deleteById(Object id);

    @Override
    void delete(LinkPhoto photo);

    void priorityUp(LinkPhoto photo);

    void priorityDown(LinkPhoto photo);

}
