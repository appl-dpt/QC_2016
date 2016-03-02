package com.softserve.hotels.service;

import java.util.List;

import org.joda.time.LocalDate;

import com.softserve.hotels.dto.ActiveOrders;
import com.softserve.hotels.dto.PaginationInfoDto;
import com.softserve.hotels.model.Apartment;
import com.softserve.hotels.model.Reserved;
import com.softserve.hotels.model.User;

public interface ReservedService extends AbstractService<Reserved> {

    List<Reserved> findAprovedByApartment(Apartment apartment);

    List<Reserved> findActiveByApartment(Apartment apartment);

    List<Reserved> findActiveForPeriod(Apartment apartment, LocalDate startDate, LocalDate endDate);

    List<Reserved> findActiveAfterDate(Apartment apartment, LocalDate startDate);

    Reserved findNextActiveAfterDate(Apartment apartment, LocalDate startDate);

    boolean isReservedForTime(Reserved reserved);

    List<LocalDate> getReservedDatesforApartment(Apartment apartment);

    List<String> getReservedDatesforApartment(Apartment apartment, LocalDate startDate, LocalDate endDate);

    List<Reserved> getFutureReserved(Apartment apartment);

    List<Reserved> getNeedConfirm(Apartment apartment);

    List<Reserved> getPastReserved(Apartment apartment);

    List<Reserved> getCurrentReserved(Apartment apartment);

    void declineReservationByRenter(Reserved reserved, String comment);

    void declineReservationByRenterBadDeal(Reserved reserved, String comment);

    void declineReservationByRenterFake(Reserved reserved, String comment);

    void declineReservationByTenant(Reserved reserved, String comment);

    void confirmReservation(Reserved reserved);

    @Override
    void create(Reserved reserved);

    List<Reserved> findFutureReservedForUser(User user);

    List<Reserved> findFutureForUserByRange(ActiveOrders filterTenant, int currentPage, int pageSize);

    List<Reserved> findPastReservedForUser(User user);

    List<Reserved> findPastForUserByRange(ActiveOrders filterTenant, int currentPage, int pageSize);

    List<Reserved> filterReserved(ActiveOrders filterTenant, int startPage, int pageSize);

    List<Reserved> findCommentableForUserAndApartment(User user, Apartment apartment);

    List<Reserved> getFutureReserved(Apartment apartment, PaginationInfoDto apartmentPageInfo);

    List<Reserved> getCurrentReserved(Apartment apartment, PaginationInfoDto apartmentPageInfo);

    List<Reserved> getPastReserved(Apartment apartment, PaginationInfoDto apartmentPageInfo);

    List<Reserved> getNeedConfirm(Apartment apartment, PaginationInfoDto apartmentPageInfo);
    
    Reserved findLastUnpayedForUser(User user);
    
    List<Reserved> findAllUnpayedForUser(User user);
    
    void deleteUnpayedOvertime();
    
    void changePastReservationsStatus();
}
