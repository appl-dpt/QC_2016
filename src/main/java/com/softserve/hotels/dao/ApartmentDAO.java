package com.softserve.hotels.dao;

import java.util.List;

import com.softserve.hotels.dto.PaginationInfoDto;
import com.softserve.hotels.dto.SortingInfoDto;
import com.softserve.hotels.dto.ApartmentOrders;
import com.softserve.hotels.model.Apartment;
import com.softserve.hotels.model.User;

public interface ApartmentDAO extends AbstractDao<Apartment> {

    List<Apartment> findAllInDescOrder();

    void updateApartmentInfo(Apartment apartment);
    
    List<Apartment> findAllEnabledForRenter(User user);
    
    List<Apartment> findAllDisabledForRenter(User user);
    
    List<Apartment> findAllUnpublishedForRenter(User user);
    
    List<Apartment> findAllEnabledForModerator(User user);
    
    List<Apartment> findAllDisabledForModerator(User user);
    
    List<Apartment> findAllFree();
    
    List<Apartment> findAllMy(User user);
    
    List<Apartment> findAllOther(User user);
    
    List<Apartment> findAllEnabledForRenterPageable(User user, PaginationInfoDto apartmentPageInfo);
 
    List<Apartment> filterApartments(ApartmentOrders apartmentOrders, int currentPage, int pageSize);
    
}
