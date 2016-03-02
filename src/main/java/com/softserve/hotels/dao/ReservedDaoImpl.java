package com.softserve.hotels.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.springframework.stereotype.Repository;

import com.softserve.hotels.dto.ActiveOrders;
import com.softserve.hotels.dto.PaginationInfoDto;
import com.softserve.hotels.model.ActionStatus;
import com.softserve.hotels.model.Apartment;
import com.softserve.hotels.model.ApartmentStatus;
import com.softserve.hotels.model.Reserved;
import com.softserve.hotels.model.Role;
import com.softserve.hotels.model.User;

@Repository("reservedDao")
public class ReservedDaoImpl extends AbstractDaoImpl<Reserved> implements ReservedDao {
    public static final Logger LOG = LogManager.getLogger(ReservedDaoImpl.class);

    @Override
    public List<Reserved> findReservedApartmentByStatus(Apartment apartment, ActionStatus status) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Reserved> query = criteriaBuilder.createQuery(Reserved.class);
        Root<Reserved> entityRoot = query.from(Reserved.class);
        query.select(entityRoot);
        ArrayList<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.equal(entityRoot.<Apartment> get("apartment"), apartment));
        predicates.add(criteriaBuilder.equal(entityRoot.<ActionStatus> get("status"), status));
        query.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));
        return getEntityManager().createQuery(query).getResultList();
    }

    @Override
    public List<Reserved> findReservedApartmentByStatus(Apartment apartment, List<ActionStatus> statusList) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Reserved> query = criteriaBuilder.createQuery(Reserved.class);
        Root<Reserved> entityRoot = query.from(Reserved.class);
        query.select(entityRoot);
        ArrayList<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.equal(entityRoot.<Apartment> get("apartment"), apartment));
        predicates.add(criteriaBuilder.or(statusPredicate(statusList, criteriaBuilder, entityRoot)));
        query.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));
        return getEntityManager().createQuery(query).getResultList();
    }

    @Override
    public List<Reserved> findFutureReservedForApartment(Apartment apartment) {
        TypedQuery<Reserved> query = getEntityManager().createNamedQuery(Reserved.NQ_FIND_FUTURE_BY_APARTMENT,
                Reserved.class);
        query.setParameter("idApartment", apartment.getId());
        query.setParameter("now", new LocalDate());
        query.setParameter("status", ActionStatus.APROVED);
        return query.getResultList();
    }

    @Override
    public List<Reserved> findNeedConfirmForApartment(Apartment apartment) {
        TypedQuery<Reserved> query = getEntityManager().createNamedQuery(Reserved.NQ_FIND_FUTURE_BY_APARTMENT,
                Reserved.class);
        query.setParameter("idApartment", apartment.getId());
        query.setParameter("now", new LocalDate());
        query.setParameter("status", ActionStatus.WAITING_CONFIRMATION);
        return query.getResultList();
    }

    @Override
    public List<Reserved> findPastReservedForApartment(Apartment apartment) {
        TypedQuery<Reserved> query = getEntityManager().createNamedQuery(Reserved.NQ_FIND_PAST_BY_APARTMENT,
                Reserved.class);
        query.setParameter("idApartment", apartment.getId());
        query.setParameter("status", ActionStatus.APROVED);
        return query.getResultList();
    }

    @Override
    public List<Reserved> findCurrentReservedForApartment(Apartment apartment) {
        TypedQuery<Reserved> query = getEntityManager().createNamedQuery(Reserved.NQ_FIND_CURRENT_BY_APARTMENT,
                Reserved.class);
        query.setParameter("idApartment", apartment.getId());
        query.setParameter("now", new LocalDate());
        query.setParameter("status", ActionStatus.APROVED);
        return query.getResultList();
    }

    @Override
    public List<Reserved> findFutureReservedForUser(User user) {
        TypedQuery<Reserved> query = getEntityManager().createNamedQuery(Reserved.NQ_FIND_FUTURE_RESERVED_FOR_USER,
                Reserved.class);
        query.setParameter("idTenant", user.getId());
        query.setParameter("now", new LocalDate());
        return query.getResultList();
    }

    @Override
    public List<Reserved> findFutureForUserByRange(ActiveOrders filterTenant, int currentPage, int pageSize) {

        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Reserved> query = criteriaBuilder.createQuery(Reserved.class);
        Root<Reserved> entityRoot = query.from(Reserved.class);
        query.select(entityRoot);
        ArrayList<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.equal(entityRoot.<User> get("tenant"), filterTenant.getTenant()));
        predicates.add(
                criteriaBuilder.or(statusPredicate(ActionStatus.getAllActiveStatus(), criteriaBuilder, entityRoot)));
        query.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));
        query.orderBy(criteriaBuilder.desc(entityRoot.<LocalDateTime>get("orderingTime")));

        List<Reserved> resultList = findByRange(query, (currentPage - 1) * pageSize, pageSize);
        Long pageCount = (long) Math.ceil(((double) (getEntityCount(criteriaBuilder, query, entityRoot))) / pageSize);

        filterTenant.setPageCount(pageCount.intValue());
        filterTenant.setCurrentPage(currentPage);

        return resultList;
    }

    @Override
    public List<Reserved> findPastReservedForUser(User user) {
        TypedQuery<Reserved> query = getEntityManager().createNamedQuery(Reserved.NQ_FIND_PAST_RESERVED_FOR_USER,
                Reserved.class);
        query.setParameter("idTenant", user.getId());
        query.setParameter("now", new LocalDate());
        return query.getResultList();
    }

    @Override
    public List<Reserved> findPastForUserByRange(ActiveOrders filterTenant, int currentPage, int pageSize) {

        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Reserved> query = criteriaBuilder.createQuery(Reserved.class);
        Root<Reserved> entityRoot = query.from(Reserved.class);
        query.select(entityRoot);

        ArrayList<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.equal(entityRoot.<User> get("tenant"), filterTenant.getTenant()));

        predicates.add(
                criteriaBuilder.or(statusPredicate(ActionStatus.getAllInactiveStatus(), criteriaBuilder, entityRoot)));

        query.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));
        query.orderBy(criteriaBuilder.desc(entityRoot.<LocalDateTime> get("orderingTime")));

        List<Reserved> resultList = findByRange(query, (currentPage - 1) * pageSize, pageSize);
        Long pageCount = (long) Math.ceil(((double) (getEntityCount(criteriaBuilder, query, entityRoot))) / pageSize);

        filterTenant.setPageCount(pageCount.intValue());
        filterTenant.setCurrentPage(currentPage);

        return resultList;
    }

    @Override
    public List<Reserved> findActiveForDate(LocalDate date) {
        TypedQuery<Reserved> query = getEntityManager().createNamedQuery(Reserved.NQ_FIND_ACTIVE_FOR_DATE,
                Reserved.class);
        query.setParameter("date", date);
        query.setParameter("status", ActionStatus.APROVED);
        return query.getResultList();
    }
    
    @Override
    public Reserved findLastUnpayedForUser(User user) {
        TypedQuery<Reserved> query = getEntityManager().createNamedQuery(Reserved.NQ_FIND_LAST_UNPAYED_FOR_USER,
                Reserved.class);
        query.setParameter("tenantId", user.getId());
        query.setParameter("status", ActionStatus.WAITING_PAYMENT);
        List<Reserved> reserved = query.getResultList();
        return reserved.size() > 0? reserved.get(0):null;
    }
    
    @Override
    public List<Reserved> findAllUnpayedForUser(User user) {
        TypedQuery<Reserved> query = getEntityManager().createNamedQuery(Reserved.NQ_FIND_ALL_UNPAYED_FOR_USER,
                Reserved.class);
        query.setParameter("tenantId", user.getId());
        query.setParameter("status", ActionStatus.WAITING_PAYMENT);
        return query.getResultList();
    }
    
    
    @Override
    public List<Reserved> findAllUnpayed() {
        TypedQuery<Reserved> query = getEntityManager().createNamedQuery(Reserved.NQ_FIND_ALL_UNPAYED,
                Reserved.class);
        query.setParameter("status", ActionStatus.WAITING_PAYMENT);
        return query.getResultList();
    }


    @Override
    public List<Reserved> findActiveForPeriod(Apartment apartment, LocalDate startDate, LocalDate endDate) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Reserved> query = criteriaBuilder.createQuery(Reserved.class);
        Root<Reserved> entityRoot = query.from(Reserved.class);
        query.select(entityRoot);
        ArrayList<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.equal(entityRoot.<Apartment> get("apartment"), apartment));

        predicates.add(criteriaBuilder.or(
                criteriaBuilder.and(
                        criteriaBuilder.lessThanOrEqualTo(entityRoot.<LocalDate> get("dateStartReservation"),
                                startDate),
                criteriaBuilder.greaterThanOrEqualTo(entityRoot.<LocalDate> get("dateEndReservation"), startDate),
                criteriaBuilder.lessThanOrEqualTo(entityRoot.<LocalDate> get("dateEndReservation"), endDate)),
                criteriaBuilder.and(
                        criteriaBuilder.greaterThanOrEqualTo(entityRoot.<LocalDate> get("dateStartReservation"),
                                startDate),
                        criteriaBuilder.lessThanOrEqualTo(entityRoot.<LocalDate> get("dateEndReservation"), endDate)),
                criteriaBuilder.and(
                        criteriaBuilder.greaterThanOrEqualTo(entityRoot.<LocalDate> get("dateStartReservation"),
                                startDate),
                        criteriaBuilder.lessThanOrEqualTo(entityRoot.<LocalDate> get("dateStartReservation"), endDate),
                        criteriaBuilder.greaterThanOrEqualTo(entityRoot.<LocalDate> get("dateEndReservation"),
                                endDate)),
                criteriaBuilder.and(
                        criteriaBuilder.lessThanOrEqualTo(entityRoot.<LocalDate> get("dateStartReservation"),
                                startDate),
                        criteriaBuilder.greaterThanOrEqualTo(entityRoot.<LocalDate> get("dateEndReservation"),
                                endDate))));
        predicates.add(
                criteriaBuilder.or(statusPredicate(ActionStatus.getAllActiveStatus(), criteriaBuilder, entityRoot)));
        query.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));
        return getEntityManager().createQuery(query).getResultList();
    }

    @Override
    public List<Reserved> findActiveAfterDate(Apartment apartment, LocalDate startDate) {

        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Reserved> query = criteriaBuilder.createQuery(Reserved.class);
        Root<Reserved> entityRoot = query.from(Reserved.class);
        query.select(entityRoot);
        ArrayList<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.equal(entityRoot.<Apartment> get("apartment"), apartment));
        predicates.add(criteriaBuilder.greaterThan(entityRoot.<LocalDate> get("dateStartReservation"), startDate));
        predicates.add(
                criteriaBuilder.or(statusPredicate(ActionStatus.getAllActiveStatus(), criteriaBuilder, entityRoot)));
        query.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));
        query.orderBy(criteriaBuilder.asc(entityRoot.<LocalDate> get("dateStartReservation")));
        return getEntityManager().createQuery(query).getResultList();
    }

    @Override
    public List<Reserved> filterReservations(ActiveOrders orderFilter, int currentPage, int pageSize) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Reserved> query = criteriaBuilder.createQuery(Reserved.class);
        Root<Reserved> entityRoot = query.from(Reserved.class);
        query.select(entityRoot);
        ArrayList<Predicate> predicates = new ArrayList<>();

        predicates.add(criteriaBuilder.equal(entityRoot.<User> get("tenant"), orderFilter.getTenant()));

        if (orderFilter.getName() != null && orderFilter.getName() != "") {
            Join<Reserved, User> userJoin = entityRoot.join("apartment");
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(userJoin.<String> get("name")),
                    "%" + orderFilter.getName().toLowerCase() + "%"));
        }

        if (orderFilter.getStartDate() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(entityRoot.<LocalDate> get("dateStartReservation"),
                    orderFilter.getStartDate()));
        }

        if (orderFilter.getEndDate() != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(entityRoot.<LocalDate> get("dateEndReservation"),
                    orderFilter.getEndDate()));
        }

        if (orderFilter.getActionStatus() == ActionStatus.ALL_ACTIVE) {
            predicates.add(criteriaBuilder
                    .or(statusPredicate(ActionStatus.getAllActiveStatus(), criteriaBuilder, entityRoot)));
        } else if (orderFilter.getActionStatus() == ActionStatus.ALL_INACTIVE) {
            predicates.add(criteriaBuilder
                    .or(statusPredicate(ActionStatus.getAllInactiveStatus(), criteriaBuilder, entityRoot)));
        } else {
            predicates
                    .add(criteriaBuilder.equal(entityRoot.<ActionStatus> get("status"), orderFilter.getActionStatus()));
        }

        query.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));
        query.orderBy(criteriaBuilder.desc(entityRoot.<LocalDateTime> get("orderingTime")));
        List<Reserved> resultList = findByRange(query, (currentPage - 1) * pageSize, pageSize);
        Long pageCount = (long) Math.ceil(((double) (getEntityCount(criteriaBuilder, query, entityRoot))) / pageSize);
        orderFilter.setPageCount(pageCount.intValue());
        orderFilter.setCurrentPage(currentPage);
        
        return resultList;

    }

    /**
     * http://www.baeldung.com/jpa-pagination
     */

    @Override
    public List<Reserved> findCommentableForUserAndApartment(User user, Apartment apartment) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Reserved> query = criteriaBuilder.createQuery(Reserved.class);
        Root<Reserved> c = query.from(Reserved.class);
        query.select(c);
        Predicate predicates = criteriaBuilder.equal(c.<User> get("tenant"), user);

        query.where(criteriaBuilder.and(predicates, criteriaBuilder.equal(c.<Apartment> get("apartment"), apartment),
                criteriaBuilder.equal(c.<ActionStatus> get("status"), ActionStatus.AFTER_RESERVATION.ordinal())));
        return getEntityManager().createQuery(query).getResultList();
    }

    private Predicate[] statusPredicate(List<ActionStatus> statusList, CriteriaBuilder criteriaBuilder,
            Root<?> entityRoot) {
        ArrayList<Predicate> predicates = new ArrayList<>();
        for (ActionStatus currentStatus : statusList) {
            predicates.add(criteriaBuilder.equal(entityRoot.<ActionStatus> get("status"), currentStatus));
        }
        return predicates.toArray(new Predicate[predicates.size()]);
    }

    @Override
    public List<Reserved> findFutureReservedForApartment(Apartment apartment, PaginationInfoDto apartmentPageInfo) {
        int currentPage = apartmentPageInfo.getCurrentPage();
        int pageSize = apartmentPageInfo.getPageSize();
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Reserved> query = criteriaBuilder.createQuery(Reserved.class);
        Root<Reserved> root = query.from(Reserved.class);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.equal(root.<Apartment> get("apartment"), apartment));
        predicates.add(criteriaBuilder.greaterThan(root.<LocalDate> get("dateStartReservation"), new LocalDate()));
        predicates.add(criteriaBuilder.equal(root.<ActionStatus> get("status"), ActionStatus.APROVED));
        query.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));
        List<Reserved> resultList = findByRange(query, (currentPage - 1) * pageSize, pageSize);
        Long pageCount = getEntityPagesCount(getEntityCount(criteriaBuilder, query, root), pageSize);
        apartmentPageInfo.setPageCount(pageCount);
        return resultList;
    }

    @Override
    public List<Reserved> findCurrentReservedForApartment(Apartment apartment, PaginationInfoDto apartmentPageInfo) {
        int currentPage = apartmentPageInfo.getCurrentPage();
        int pageSize = apartmentPageInfo.getPageSize();
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Reserved> query = criteriaBuilder.createQuery(Reserved.class);
        Root<Reserved> root = query.from(Reserved.class);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.equal(root.<Apartment> get("apartment"), apartment));
        predicates
                .add(criteriaBuilder.lessThanOrEqualTo(root.<LocalDate> get("dateStartReservation"), new LocalDate()));
        predicates
                .add(criteriaBuilder.greaterThanOrEqualTo(root.<LocalDate> get("dateEndReservation"), new LocalDate()));
        predicates.add(criteriaBuilder.equal(root.<ActionStatus> get("status"), ActionStatus.APROVED));
        query.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));
        List<Reserved> resultList = findByRange(query, (currentPage - 1) * pageSize, pageSize);
        Long pageCount = getEntityPagesCount(getEntityCount(criteriaBuilder, query, root), pageSize);
        apartmentPageInfo.setPageCount(pageCount);
        return resultList;
    }

    @Override
    public List<Reserved> findPastReservedForApartment(Apartment apartment, PaginationInfoDto apartmentPageInfo) {
        int currentPage = apartmentPageInfo.getCurrentPage();
        int pageSize = apartmentPageInfo.getPageSize();
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Reserved> query = criteriaBuilder.createQuery(Reserved.class);
        Root<Reserved> root = query.from(Reserved.class);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.equal(root.<Apartment> get("apartment"), apartment));
        predicates.add(criteriaBuilder.notEqual(root.<ActionStatus> get("status"), ActionStatus.APROVED));
        query.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));
        List<Reserved> resultList = findByRange(query, (currentPage - 1) * pageSize, pageSize);
        Long pageCount = getEntityPagesCount(getEntityCount(criteriaBuilder, query, root), pageSize);
        apartmentPageInfo.setPageCount(pageCount);
        return resultList;
    }

    @Override
    public List<Reserved> findNeedConfirmForApartment(Apartment apartment, PaginationInfoDto apartmentPageInfo) {
        int currentPage = apartmentPageInfo.getCurrentPage();
        int pageSize = apartmentPageInfo.getPageSize();
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Reserved> query = criteriaBuilder.createQuery(Reserved.class);
        Root<Reserved> root = query.from(Reserved.class);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.equal(root.<Apartment> get("apartment"), apartment));
        predicates
        .add(criteriaBuilder.greaterThan(root.<LocalDate> get("dateStartReservation"), new LocalDate()));
        predicates.add(criteriaBuilder.equal(root.<ActionStatus> get("status"), ActionStatus.WAITING_CONFIRMATION));
        query.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));
        List<Reserved> resultList = findByRange(query, (currentPage - 1) * pageSize, pageSize);
        Long pageCount = getEntityPagesCount(getEntityCount(criteriaBuilder, query, root), pageSize);
        apartmentPageInfo.setPageCount(pageCount);
        return resultList;
    }
    

    @Override
    public List<Reserved> findPastReservedByStatuses(ActionStatus... actionStatuses) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Reserved> query = criteriaBuilder.createQuery(Reserved.class);
        Root<Reserved> c = query.from(Reserved.class);
        query.select(c);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder
                .and(criteriaBuilder.lessThan(c.<LocalDate> get("dateEndReservation"), new LocalDate())));
        for (ActionStatus status : actionStatuses) {
            predicates.add(criteriaBuilder.or(criteriaBuilder.equal(c.<ActionStatus> get("status"), status.ordinal())));
        }
        query.where(predicates.toArray(new Predicate[predicates.size()]));
        return getEntityManager().createQuery(query).getResultList();
    }

}
