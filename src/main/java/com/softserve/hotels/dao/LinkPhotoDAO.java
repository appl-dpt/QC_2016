package com.softserve.hotels.dao;

import com.softserve.hotels.model.LinkPhoto;

public interface LinkPhotoDAO extends AbstractDao<LinkPhoto> {

    LinkPhoto getLinkPhotoById(int id);

    void removeLinkPhoto(int id);

}
