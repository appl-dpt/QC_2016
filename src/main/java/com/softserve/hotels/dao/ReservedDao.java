package com.softserve.hotels.dao;

import java.util.List;

import org.joda.time.LocalDate;

import com.softserve.hotels.dto.ActiveOrders;
import com.softserve.hotels.dto.PaginationInfoDto;
import com.softserve.hotels.model.ActionStatus;
import com.softserve.hotels.model.Apartment;
import com.softserve.hotels.model.Reserved;
import com.softserve.hotels.model.User;

public interface ReservedDao extends AbstractDao<Reserved> {

    List<Reserved> findFutureReservedForApartment(Apartment apartment);
    
    List<Reserved> findFutureReservedForApartment(Apartment apartment, PaginationInfoDto apartmentPageInfo);

    List<Reserved> findPastReservedForApartment(Apartment apartment);
    
    List<Reserved> findPastReservedForApartment(Apartment apartment, PaginationInfoDto apartmentPageInfo);

    List<Reserved> findCurrentReservedForApartment(Apartment apartment);
    
    List<Reserved> findCurrentReservedForApartment(Apartment apartment, PaginationInfoDto apartmentPageInfo);

    List<Reserved> findNeedConfirmForApartment(Apartment apartment);
    
    List<Reserved> findNeedConfirmForApartment(Apartment apartment, PaginationInfoDto apartmentPageInfo);

    List<Reserved> findFutureReservedForUser(User user);

    List<Reserved> findFutureForUserByRange(ActiveOrders orderFilter, int currentPage, int pageSize);

    List<Reserved> findPastReservedForUser(User user);

    List<Reserved> findPastForUserByRange(ActiveOrders orderFilter, int currentPage, int pageSize);

    List<Reserved> findReservedApartmentByStatus(Apartment apartment, ActionStatus status);

    List<Reserved> findReservedApartmentByStatus(Apartment apartment, List<ActionStatus> statusList);

    List<Reserved> findActiveForDate(LocalDate date);

    List<Reserved> findActiveForPeriod(Apartment apartment, LocalDate startDate, LocalDate endDate);

    List<Reserved> findActiveAfterDate(Apartment apartment, LocalDate startDate);

    List<Reserved> filterReservations(ActiveOrders orderFilter, int currentPage, int pageSize);

    List<Reserved> findCommentableForUserAndApartment(User user, Apartment apartment);
    
    Reserved findLastUnpayedForUser(User user);
    
    List<Reserved> findAllUnpayedForUser(User user);
    
    List<Reserved> findAllUnpayed();
    
    
    List<Reserved> findPastReservedByStatuses(ActionStatus ...actionStatuses);
}
