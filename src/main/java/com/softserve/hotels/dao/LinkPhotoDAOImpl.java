package com.softserve.hotels.dao;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.softserve.hotels.model.LinkPhoto;

@Repository("photoDAO")
public class LinkPhotoDAOImpl extends AbstractDaoImpl<LinkPhoto> implements LinkPhotoDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(LinkPhoto.class);

    @Override
    public LinkPhoto getLinkPhotoById(int id) {
        LinkPhoto photo = (LinkPhoto) getEntityManager().find(LinkPhoto.class, new Integer(id));
        LOGGER.info("photo loaded successfully, photo details=" + photo);
        return photo;
    }

    @Override
    public void removeLinkPhoto(int id) {
        LinkPhoto photo = (LinkPhoto) getEntityManager().find(LinkPhoto.class, new Integer(id));
        if (null != photo) {
            getEntityManager().remove(photo);
        }
        LOGGER.info("photo deleted successfully, photo details=" + photo);
    }

}
