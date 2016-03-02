package com.softserve.hotels.service;

import java.util.Iterator;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.softserve.hotels.dao.ActionApartmentDao;
import com.softserve.hotels.dao.ApartmentDAO;
import com.softserve.hotels.dao.LinkPhotoDAO;
import com.softserve.hotels.model.ActionApartment;
import com.softserve.hotels.model.Apartment;
import com.softserve.hotels.model.ApartmentStatus;
import com.softserve.hotels.model.LinkPhoto;
import com.softserve.hotels.utils.FileUtils;
import com.softserve.hotels.utils.ImageConverter;

@Service("photoService")
@Transactional
public class LinkPhotoServiceImpl extends AbstractServiceImpl<LinkPhoto> implements LinkPhotoService {
    @Autowired
    private LinkPhotoDAO photoDao;

    @Autowired
    private ApartmentDAO apartmentDao;

    @Autowired
    private ActionApartmentDao actionApartmentDao;

    @Override
    public void create(LinkPhoto photo) {
        Apartment apartment = apartmentDao.findById(photo.getApartmentID());
        if (apartment.getModerator() != null) {
            apartment.setAproved(false);
            apartmentDao.update(apartment);
            ActionApartment actionApartment = new ActionApartment(apartment, ApartmentStatus.CHANGED);
            actionApartmentDao.create(actionApartment);
        }
        Set<LinkPhoto> photos = apartment.getLinks();
        if (photos.isEmpty()) {
            photo.setPriority(0);
        } else {
            Iterator<LinkPhoto> iter = photos.iterator();
            int p = 0;
            while (iter.hasNext()) {
                p = iter.next().getPriority();
            }
            photo.setPriority(++p);
        }
        super.create(photo);
    }

    @Override
    public byte[] getPhotoDataById(Integer id) {
        LinkPhoto photo = photoDao.findById(id);
        return ImageConverter.toByteArray(photo);
    }

    @Override
    @PreAuthorize("principal.id == #photo.apartment.renter.id  or hasRole('ADMIN')")
    public void delete(LinkPhoto photo) {
        Apartment apartment = apartmentDao.findById(photo.getApartmentID());
        FileUtils.deleteFile(photo.getUrl());
        apartment.getLinks().remove(photo);
        super.delete(photo);
        apartmentDao.update(apartment);
    }

    @Override
    public void deleteById(Object id) {
        LinkPhoto photo = this.photoDao.findById(id);
        Apartment apartment = apartmentDao.findById(photo.getApartmentID());
        FileUtils.deleteFile(photo.getUrl());
        apartment.getLinks().remove(photo);
        super.deleteById(id);
        apartmentDao.update(apartment);
    }

    @Override
    @PreAuthorize("principal.id == #photo.apartment.renter.id  or hasRole('ADMIN')")
    public void priorityUp(LinkPhoto photo) {
        Apartment apartment = this.apartmentDao.findById(photo.getApartment().getId());
        LinkPhoto prePhoto = null;
        for (LinkPhoto tmpPhoto : apartment.getLinks()) {
            if (tmpPhoto.getPriority() == photo.getPriority()) {
                break;
            }
            prePhoto = tmpPhoto;
        }
        if (prePhoto != null) {
            int tmp = photo.getPriority();
            photo.setPriority(prePhoto.getPriority());
            prePhoto.setPriority(tmp);
            this.photoDao.update(photo);
            this.photoDao.update(prePhoto);
        }
    }

    @Override
    @PreAuthorize("principal.id == #photo.apartment.renter.id  or hasRole('ADMIN')")
    public void priorityDown(LinkPhoto photo) {
        Apartment apartment = this.apartmentDao.findById(photo.getApartment().getId());
        boolean find = false;
        LinkPhoto nextPhoto = null;
        for (LinkPhoto tmpPhoto : apartment.getLinks()) {
            nextPhoto = tmpPhoto;
            if (find)
                break;
            nextPhoto = null;
            if (tmpPhoto.getPriority() == photo.getPriority()) {
                find = true;
            }
        }
        if (nextPhoto != null) {
            int tmp = photo.getPriority();
            photo.setPriority(nextPhoto.getPriority());
            nextPhoto.setPriority(tmp);
            this.photoDao.update(photo);
            this.photoDao.update(nextPhoto);
        }
    }

}
