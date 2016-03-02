package com.softserve.hotels.service;

import java.util.List;

import com.softserve.hotels.dto.ApartmentConveniencesDto;
import com.softserve.hotels.dto.ApartmentPaymentDto;
import com.softserve.hotels.dto.PaginationInfoDto;
import com.softserve.hotels.dto.ApartmentOrders;
import com.softserve.hotels.model.Apartment;
import com.softserve.hotels.model.User;

public interface ApartmentService extends AbstractService<Apartment> {
    @Override
    void create(Apartment entity);

    List<Apartment> findAllInDescOrder();

    @Override
    void deleteById(Object id);

    @Override
    void delete(Apartment apartment);

    void updateApartmentInfo(Apartment apartment);

    boolean updateApartmentCoordinates(Apartment apartment);

    void updateApartmentConveniences(ApartmentConveniencesDto acdto);
    
    void updateApartmentPaymentSettings(ApartmentPaymentDto apartmentPaymentDto);

    List<Apartment> findAllEnabledForRenter(User user);

    List<Apartment> findAllDisabledForRenter(User user);

    List<Apartment> findAllUpublishedForRenter(User user);

    List<Apartment> findAllEnabledForModerator(User user);

    List<Apartment> findAllDisabledForModerator(User user);

    List<Apartment> findAllFree();

    List<Apartment> findAllMy(User user);

    List<Apartment> findAllOther(User user);

    void unPublishApartment(Apartment apartment);

    void publishApartment(Apartment apartment);

    void assignApartment(Apartment apartment, User moderator);

    void approveApartment(Apartment apartment);
    
    List<Apartment> findAllEnabledForRenterPageable(User user, PaginationInfoDto apartmentPageInfo);

    List<Apartment> filterApartment(ApartmentOrders apartmentOrders, int startPage, int pageSize);
    
}
