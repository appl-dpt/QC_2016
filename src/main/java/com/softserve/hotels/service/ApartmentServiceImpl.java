package com.softserve.hotels.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maxmind.geoip.Location;
import com.softserve.hotels.dao.ActionApartmentDao;
import com.softserve.hotels.dao.ApartmentConveniencesDao;
import com.softserve.hotels.dao.ApartmentDAO;
import com.softserve.hotels.dao.ApartmentPaymentDao;
import com.softserve.hotels.dao.ConvenienceDao;
import com.softserve.hotels.dao.PaymentMethodDao;
import com.softserve.hotels.dto.ApartmentConveniencesDto;
import com.softserve.hotels.dto.ApartmentPaymentDto;
import com.softserve.hotels.dto.PaginationInfoDto;
import com.softserve.hotels.dto.ApartmentOrders;
import com.softserve.hotels.model.ActionApartment;
import com.softserve.hotels.model.Apartment;
import com.softserve.hotels.model.ApartmentConveniences;
import com.softserve.hotels.model.ApartmentPayment;
import com.softserve.hotels.model.ApartmentStatus;
import com.softserve.hotels.model.Convenience;
import com.softserve.hotels.model.LinkPhoto;
import com.softserve.hotels.model.PaymentMethod;
import com.softserve.hotels.model.User;
import com.softserve.hotels.utils.FileUtils;
import com.softserve.hotels.utils.GoogleMapsUtils;

@Service("apartmentService")
@Transactional
public class ApartmentServiceImpl extends AbstractServiceImpl<Apartment> implements ApartmentService {

    @Autowired
    private ApartmentDAO apartmentDao;

    @Autowired
    private ConvenienceDao convenienceDao;
    
    @Autowired
    private PaymentMethodDao paymentMethodDao;

    @Autowired
    private ApartmentConveniencesDao apartmentConveniencesDao;
    
    @Autowired
    private ApartmentPaymentDao apartmentPaymentDao;

    @Autowired
    private ActionApartmentDao actionApartmentDao;

    private static final double MAX_DISTANCE = 0.25;

    @Override
    public void create(Apartment entity) {
        entity.setStatus(ApartmentStatus.ENABLED);
        super.create(entity);
        List<Convenience> conveniences = convenienceDao.findAll();
        for (Convenience convenience : conveniences) {
            ApartmentConveniences apconvenience = new ApartmentConveniences();
            apconvenience.setApartment(entity);
            apconvenience.setConvenience(convenience);
            apconvenience.setExists(false);
            apartmentConveniencesDao.create(apconvenience);
        }
        List<PaymentMethod> payments = paymentMethodDao.findAll();
        for (PaymentMethod payment : payments) {
            ApartmentPayment apPayment = new ApartmentPayment();
            apPayment.setApartment(entity);
            apPayment.setPaymentMethod(payment);
            apPayment.setExists(false);
            apartmentPaymentDao.create(apPayment);
        }
        ActionApartment actionApartment = new ActionApartment();
        actionApartment.setApartment(entity);
        actionApartment.setStatus(ApartmentStatus.CREATED);
        actionApartmentDao.create(actionApartment);
        FileUtils.makeDirApartment(entity);
    }

    @Override
    public List<Apartment> findAllInDescOrder() {
        return apartmentDao.findAllInDescOrder();
    }

    @Override
    @PreAuthorize("principal.id == #apartment.renter.id or hasRole('ADMIN')")
    public void deleteById(Object id) {
        Apartment apartment = apartmentDao.findById(new Integer((int) id));
        for (LinkPhoto photo : apartment.getLinks()) {
            FileUtils.deleteFile(photo.getUrl());
        }
        FileUtils.removeDirApartment(apartment);
        super.deleteById(id);
    }

    @Override
    @PreAuthorize("principal.id == #apartment.renter.id  or hasRole('ADMIN')")
    public void delete(Apartment apartment) {
        for (LinkPhoto photo : apartment.getLinks()) {
            FileUtils.deleteFile(photo.getUrl());
        }
        FileUtils.removeDirApartment(apartment);
        super.delete(apartment);
    }

    @Override
    @PreAuthorize("principal.id == #apartment.renter.id  or hasRole('ADMIN')")
    public void updateApartmentInfo(Apartment apartment) {
        apartmentDao.updateApartmentInfo(apartment);
        Apartment newApartment = apartmentDao.findById(apartment.getId());
        if (newApartment.getModerator() != null) {
            newApartment.setAproved(false);
            ActionApartment actionApartment = new ActionApartment(newApartment, ApartmentStatus.CHANGED);
            actionApartmentDao.create(actionApartment);
            apartmentDao.update(newApartment);
        }     
    }

    @Override
    @PreAuthorize("principal.id == #apartment.renter.id  or hasRole('ADMIN')")
    public boolean updateApartmentCoordinates(Apartment apartment) {
        Apartment updateApartment = apartmentDao.findById(apartment.getId());
        Location location = new Location();
        location.latitude = apartment.getLatitude();
        location.longitude = apartment.getLongitude();
        if (GoogleMapsUtils.distance(location,
                GoogleMapsUtils.getLocationCity(updateApartment.getCity())) > MAX_DISTANCE) {
            return false;
        }
        updateApartment.setLatitude(apartment.getLatitude());
        updateApartment.setLongitude(apartment.getLongitude());
        if (updateApartment.getModerator() != null) {
            updateApartment.setAproved(false);
            ActionApartment actionApartment = new ActionApartment(apartment, ApartmentStatus.CHANGED);
            actionApartmentDao.create(actionApartment);
        }
        apartmentDao.update(updateApartment);
        return true;
    }

    @Override
    public void updateApartmentConveniences(ApartmentConveniencesDto acdto) {
        Apartment apartment = this.apartmentDao.findById(acdto.getId());
        apartment.setMaxCountGuests(acdto.getMaxCountGuests());
        for (ApartmentConveniences apConvenience : apartment.getApartmentConveniences()) {
            if (acdto.getConveniences() != null
                    && acdto.getConveniences().contains(apConvenience.getConvenience().getName())) {
                apConvenience.setExists(true);
            } else {
                apConvenience.setExists(false);
            }
        }
        if (apartment.getModerator() != null) {
            apartment.setAproved(false);
            ActionApartment actionApartment = new ActionApartment(apartment, ApartmentStatus.CHANGED);
            actionApartmentDao.create(actionApartment);
        }
        apartmentDao.update(apartment);
    }
    
    @Override
    public void updateApartmentPaymentSettings(ApartmentPaymentDto apartmentPaymentDto) {
        Apartment apartment = this.apartmentDao.findById(apartmentPaymentDto.getId());
        apartment.setPrice(apartmentPaymentDto.getPrice());
        for (ApartmentPayment apPayment : apartment.getApartmentPayments()) {
            if (apartmentPaymentDto.getPayments() != null
                    && apartmentPaymentDto.getPayments().contains(apPayment.getPaymentMethod().getName())) {
                apPayment.setExists(true);
            } else {
                apPayment.setExists(false);
            }
        }
        apartmentDao.update(apartment);
    }

    @Override
    public List<Apartment> findAllEnabledForRenter(User user) {
        return apartmentDao.findAllEnabledForRenter(user);
    }

    @Override
    public List<Apartment> findAllDisabledForRenter(User user) {
        return apartmentDao.findAllDisabledForRenter(user);
    }

    @Override
    public List<Apartment> findAllUpublishedForRenter(User user) {
        return apartmentDao.findAllUnpublishedForRenter(user);
    }

    @Override
    public List<Apartment> findAllEnabledForModerator(User user) {
        return apartmentDao.findAllEnabledForModerator(user);
    }

    @Override
    public List<Apartment> findAllDisabledForModerator(User user) {
        return apartmentDao.findAllDisabledForModerator(user);
    }

    @Override
    public List<Apartment> findAllFree() {
        return apartmentDao.findAllFree();
    }

    @Override
    public List<Apartment> findAllMy(User user) {
        return apartmentDao.findAllMy(user);
    }

    @Override
    public List<Apartment> findAllOther(User user) {
        return apartmentDao.findAllOther(user);
    }

    @Override
    public void unPublishApartment(Apartment apartment) {
        apartment.setPublished(false);
        apartmentDao.update(apartment);
        ActionApartment actionApartment = new ActionApartment(apartment, ApartmentStatus.UNPUBLISHED);
        actionApartmentDao.create(actionApartment);
    }

    @Override
    public void publishApartment(Apartment apartment) {
        apartment.setPublished(true);
        apartmentDao.update(apartment);
        ActionApartment actionApartment = new ActionApartment(apartment, ApartmentStatus.PUBLISHED);
        actionApartmentDao.create(actionApartment);
    }

    @Override
    public void assignApartment(Apartment apartment, User moderator) {
        apartment.setModerator(moderator);
        apartmentDao.update(apartment);
        ActionApartment actionApartment = new ActionApartment(apartment, ApartmentStatus.ASSIGNED);
        actionApartment.setModerator(moderator);
        actionApartmentDao.create(actionApartment);
    }

    @Override
    public void approveApartment(Apartment apartment) {
        apartment.setAproved(true);
        apartmentDao.update(apartment);
        ActionApartment actionApartment = new ActionApartment(apartment, ApartmentStatus.APPROVED);
        actionApartment.setModerator(apartment.getModerator());
        actionApartmentDao.create(actionApartment);
    }
    
    @Override
    public List<Apartment> filterApartment(ApartmentOrders apartmentOrders, int currentPage, int pageSize) {
        return apartmentDao.filterApartments(apartmentOrders, currentPage, pageSize);
    }

    @Override
    public List<Apartment> findAllEnabledForRenterPageable(User user, PaginationInfoDto apartmentPageInfo) {
        return apartmentDao.findAllEnabledForRenterPageable(user, apartmentPageInfo);
    }

}
