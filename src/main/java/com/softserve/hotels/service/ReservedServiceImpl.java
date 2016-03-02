package com.softserve.hotels.service;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.Instant;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.Minutes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.softserve.hotels.annotations.Loggable;
import com.softserve.hotels.dao.ActionReservationDao;
import com.softserve.hotels.dao.ReservedDao;
import com.softserve.hotels.dto.ActiveOrders;
import com.softserve.hotels.dto.PaginationInfoDto;
import com.softserve.hotels.model.ActionReservation;
import com.softserve.hotels.model.ActionStatus;
import com.softserve.hotels.model.Apartment;
import com.softserve.hotels.model.Reserved;
import com.softserve.hotels.model.User;

@Service("reservedService")
@Transactional
public class ReservedServiceImpl extends AbstractServiceImpl<Reserved> implements ReservedService {

    @Autowired
    private ReservedDao reservedDao;

    @Autowired
    private ActionReservationDao actionReservationDao;

    @Autowired
    private MailService mailService;

    @Override
    public List<Reserved> findAprovedByApartment(Apartment apartment) {
        return reservedDao.findReservedApartmentByStatus(apartment, ActionStatus.APROVED);
    }

    @Override
    public List<Reserved> findActiveByApartment(Apartment apartment) {
        return reservedDao.findReservedApartmentByStatus(apartment, ActionStatus.getAllActiveStatus());
    }

    @Override
    public List<Reserved> findActiveForPeriod(Apartment apartment, LocalDate startDate, LocalDate endDate) {
        return reservedDao.findActiveForPeriod(apartment, startDate, endDate);
    }

    @Override
    public List<Reserved> findActiveAfterDate(Apartment apartment, LocalDate startDate) {

        return reservedDao.findActiveAfterDate(apartment, startDate);
    }

    @Override
    public Reserved findNextActiveAfterDate(Apartment apartment, LocalDate startDate) {
        ArrayList<Reserved> resultList = (ArrayList<Reserved>) findActiveAfterDate(apartment, startDate);
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    @Override
    public boolean isReservedForTime(Reserved reserved) {
        for (Reserved currentReserved : (ArrayList<Reserved>) findActiveByApartment(reserved.getApartment())) {
            if (getIntervalFromReserved(reserved).overlap(getIntervalFromReserved(currentReserved)) != null) {
                return true;
            }
        }
        return false;
    }

    private static Interval getIntervalFromReserved(Reserved reserved) {
        return new Interval(reserved.getDateStartReservation().toDateTime(new Instant(0)),
                reserved.getDateEndReservation().toDateTime(new Instant(0)));
    }

    @Override
    public List<LocalDate> getReservedDatesforApartment(Apartment apartment) {

        ArrayList<LocalDate> reservedDates = new ArrayList<>();
        ArrayList<Reserved> reservedList = (ArrayList<Reserved>) findActiveByApartment(apartment);
        for (Reserved reserved : reservedList) {
            LocalDate dateStart = reserved.getDateStartReservation().plusDays(1);
            LocalDate dateEnd = reserved.getDateEndReservation().minusDays(1);
            while (dateStart.isBefore(dateEnd) || dateStart.isEqual(dateEnd)) {
                reservedDates.add(dateStart);
                dateStart = dateStart.plusDays(1);
            }
        }
        return reservedDates;
    }

    @Override
    public List<String> getReservedDatesforApartment(Apartment apartment, LocalDate startDate, LocalDate endDate) {

        List<String> reservedDates = new ArrayList<>();
        List<Reserved> reservedList = findActiveForPeriod(apartment, startDate, endDate);
        for (Reserved reserved : reservedList) {
            LocalDate dateStart = reserved.getDateStartReservation();
            LocalDate dateEnd = reserved.getDateEndReservation().minusDays(1);
            while (dateStart.isBefore(dateEnd) || dateStart.isEqual(dateEnd)) {
                reservedDates.add(dateStart.toString());
                dateStart = dateStart.plusDays(1);
            }
        }
        return reservedDates;
    }

    @Override
    public List<Reserved> getFutureReserved(Apartment apartment) {
        return reservedDao.findFutureReservedForApartment(apartment);
    }

    @Override
    public List<Reserved> getNeedConfirm(Apartment apartment) {
        return reservedDao.findNeedConfirmForApartment(apartment);
    }

    @Override
    public List<Reserved> getPastReserved(Apartment apartment) {
        return reservedDao.findPastReservedForApartment(apartment);
    }

    @Override
    public List<Reserved> getCurrentReserved(Apartment apartment) {
        return reservedDao.findCurrentReservedForApartment(apartment);
    }

    @Override
    public void declineReservationByRenter(Reserved reserved, String comment) {
        reserved.setStatus(ActionStatus.DECLINED_RENTER);
        ActionReservation action = new ActionReservation();
        action.setStatus(ActionStatus.DECLINED_RENTER);
        action.setReservation(reserved);
        action.setComment(comment);
        this.actionReservationDao.create(action);
        this.reservedDao.update(reserved);
        mailService.sendMessage(reserved.getTenant(), "Canceling of reservation",
                mailService.buildUnbookMessage(reserved.getTenant()));
    }

    @Override
    public void declineReservationByRenterBadDeal(Reserved reserved, String comment) {
        reserved.setStatus(ActionStatus.BAD_DEAL);
        ActionReservation action = new ActionReservation();
        action.setStatus(ActionStatus.BAD_DEAL);
        action.setReservation(reserved);
        action.setComment(comment);
        this.actionReservationDao.create(action);
        this.reservedDao.update(reserved);
        mailService.sendMessage(reserved.getTenant(), "Canceling of reservation",
                mailService.buildUnbookMessage(reserved.getTenant()));
    }

    @Override
    public void declineReservationByRenterFake(Reserved reserved, String comment) {
        ActionReservation action = new ActionReservation();
        action.setStatus(ActionStatus.FAKE_RESERVATION);
        action.setReservation(reserved);
        action.setComment(comment);
        this.actionReservationDao.create(action);
        reserved.setStatus(ActionStatus.FAKE_RESERVATION);
        reservedDao.update(reserved);
        mailService.sendMessage(reserved.getTenant(), "Canceling of reservation",
                mailService.buildUnbookMessageBecauseFake(reserved.getTenant()));
    }

    @Override
    public void declineReservationByTenant(Reserved reserved, String comment) {
        ActionReservation action = new ActionReservation();
        action.setStatus(ActionStatus.DECLINED_TENANT);
        action.setReservation(reserved);
        action.setComment(comment);
        this.actionReservationDao.create(action);
        reserved.setStatus(ActionStatus.DECLINED_TENANT);
        reservedDao.update(reserved);
    }

    @Override
    public void confirmReservation(Reserved reserved) {
        ActionReservation action = new ActionReservation();
        action.setReservation(reserved);
        this.actionReservationDao.create(action);
        reserved.setStatus(ActionStatus.APROVED);
        reservedDao.update(reserved);
        mailService.sendMessage(reserved.getTenant(), "Confirmation of reservation",
                mailService.buildConfirmReservMessage(reserved.getTenant()));
    }

    @Override
    public void create(Reserved reserved) {
        reserved.setStatus(ActionStatus.WAITING_PAYMENT);
        reserved.setOrderingTime(new LocalDateTime());
        ActionReservation action = new ActionReservation();
        action.setReservation(reserved);
        action.setStatus(ActionStatus.WAITING_PAYMENT);
        super.create(reserved);
        this.actionReservationDao.create(action);
    }

    @Override
    public List<Reserved> findFutureReservedForUser(User user) {
        return reservedDao.findFutureReservedForUser(user);
    }

    @Override
    public List<Reserved> findFutureForUserByRange(ActiveOrders filterTenant, int currentPage, int pageSize) {
        return reservedDao.findFutureForUserByRange(filterTenant, currentPage, pageSize);
    }

    @Override
    public List<Reserved> findPastReservedForUser(User user) {
        return reservedDao.findPastReservedForUser(user);
    }

    @Override
    public List<Reserved> findPastForUserByRange(ActiveOrders filterTenant, int currentPage, int pageSize) {
        return reservedDao.findPastForUserByRange(filterTenant, currentPage, pageSize);
    }

    @Override
    public List<Reserved> filterReserved(ActiveOrders filterTenant, int currentPage, int pageSize) {
        return reservedDao.filterReservations(filterTenant, currentPage, pageSize);
    }

    @Override
    public List<Reserved> findCommentableForUserAndApartment(User user, Apartment apartment) {
        return reservedDao.findCommentableForUserAndApartment(user, apartment);
    }

    @Override
    public List<Reserved> getFutureReserved(Apartment apartment, PaginationInfoDto apartmentPageInfo) {
        return reservedDao.findFutureReservedForApartment(apartment, apartmentPageInfo);
    }

    @Override
    public List<Reserved> getCurrentReserved(Apartment apartment, PaginationInfoDto apartmentPageInfo) {
        return reservedDao.findCurrentReservedForApartment(apartment, apartmentPageInfo);
    }

    @Override
    public List<Reserved> getPastReserved(Apartment apartment, PaginationInfoDto apartmentPageInfo) {
        return reservedDao.findPastReservedForApartment(apartment, apartmentPageInfo);
    }

    @Override
    public List<Reserved> getNeedConfirm(Apartment apartment, PaginationInfoDto apartmentPageInfo) {
        return reservedDao.findNeedConfirmForApartment(apartment, apartmentPageInfo);
    }
    
    @Override
    public Reserved findLastUnpayedForUser(User user) {
        return reservedDao.findLastUnpayedForUser(user);
    }
    
    @Override
    public List<Reserved> findAllUnpayedForUser(User user) {
        return reservedDao.findAllUnpayedForUser(user);
    }

    @Scheduled(fixedRate=55000)
    @Override
    public void deleteUnpayedOvertime() {
        System.out.println("Checking all orders");
        ArrayList<Reserved> unpayedList = (ArrayList<Reserved>)reservedDao.findAllUnpayed();
        System.out.println("Unpays: " + unpayedList.size());
        for(Reserved reserved : unpayedList) {
            Minutes minutes = Minutes.minutesBetween(reserved.getOrderingTime(), new LocalDateTime());
            System.out.println("Minutes: " + minutes.getMinutes());
            if(minutes.getMinutes() >=5) {
                reservedDao.delete(reserved);
                System.out.println("deleted reserved");
            }
        }
        
    }
    
    @Loggable
    @Override
    @Scheduled(cron = "0 10 0 * * ?")
    public void changePastReservationsStatus() {
        List<Reserved> reservationsToChange = reservedDao.findPastReservedByStatuses(ActionStatus.APROVED);
        for (Reserved reserved : reservationsToChange) {
            reserved.setStatus(ActionStatus.AFTER_RESERVATION);
            reservedDao.update(reserved);
        }
    }

}
