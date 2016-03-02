package com.softserve.hotels.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Repository;

import com.softserve.hotels.dto.ApartmentOrders;
import com.softserve.hotels.dto.PaginationInfoDto;
import com.softserve.hotels.dto.SortingInfoDto;
import com.softserve.hotels.model.Apartment;
import com.softserve.hotels.model.ApartmentStatus;
import com.softserve.hotels.model.Reserved;
import com.softserve.hotels.model.Role;
import com.softserve.hotels.model.User;

@Repository("apartmentDao")
public class ApartmentDAOImpl extends AbstractDaoImpl<Apartment> implements ApartmentDAO {

    public static final Logger LOG = LogManager.getLogger(UserDaoImpl.class);

    @Override
    public List<Apartment> findAllInDescOrder() {
        try {
            return getEntityManager().createNamedQuery(Apartment.NQ_FIND_ALL_ORDER_BY_ID_DESC, Apartment.class)
                    .getResultList();
        } catch (NoResultException e) {
            LOG.info(e);
            return new ArrayList<>();
        }
    }

    @Override
    public void updateApartmentInfo(Apartment apartment) {
        Query query = getEntityManager().createNamedQuery(Apartment.NQ_UPDATE_APARTMENT_INFO);
        query.setParameter("name", apartment.getName());
        query.setParameter("description", apartment.getDescription());
        query.setParameter("address", apartment.getAddress());
        query.setParameter("city", apartment.getCity());
        query.setParameter("id", apartment.getId());
        query.executeUpdate();
    }

    @Override
    public List<Apartment> findAllEnabledForRenter(User user) {
        TypedQuery<Apartment> query = getEntityManager().createNamedQuery(Apartment.NQ_FIND_ALL_ENABLED_FOR_USER,
                Apartment.class);
        query.setParameter("renter", user);
        query.setParameter("status", ApartmentStatus.ENABLED);
        return query.getResultList();
    }

    @Override
    public List<Apartment> findAllDisabledForRenter(User user) {
        TypedQuery<Apartment> query = getEntityManager().createNamedQuery(Apartment.NQ_FIND_ALL_DISABLED_FOR_USER,
                Apartment.class);
        query.setParameter("renter", user);
        query.setParameter("status", ApartmentStatus.ENABLED);
        return query.getResultList();
    }

    @Override
    public List<Apartment> findAllUnpublishedForRenter(User user) {
        TypedQuery<Apartment> query = getEntityManager().createNamedQuery(Apartment.NQ_FIND_ALL_UNPUBLISHED_FOR_USER,
                Apartment.class);
        query.setParameter("renter", user);
        query.setParameter("status", ApartmentStatus.ENABLED);
        return query.getResultList();
    }

    @Override
    public List<Apartment> findAllEnabledForModerator(User user) {
        TypedQuery<Apartment> query = getEntityManager().createNamedQuery(Apartment.NQ_FIND_ALL_STATUS_FOR_MODERATOR,
                Apartment.class);
        query.setParameter("moderator", user);
        query.setParameter("status", ApartmentStatus.ENABLED);
        return query.getResultList();
    }

    @Override
    public List<Apartment> findAllDisabledForModerator(User user) {
        TypedQuery<Apartment> query = getEntityManager().createNamedQuery(Apartment.NQ_FIND_ALL_STATUS_FOR_MODERATOR,
                Apartment.class);
        query.setParameter("moderator", user);
        query.setParameter("status", ApartmentStatus.DISABLED_BY_MODERATOR);
        return query.getResultList();
    }

    @Override
    public List<Apartment> findAllFree() {
        TypedQuery<Apartment> query = getEntityManager().createNamedQuery(Apartment.NQ_FIND_ALL_FREE, Apartment.class);
        return query.getResultList();
    }

    @Override
    public List<Apartment> findAllMy(User user) {
        TypedQuery<Apartment> query = getEntityManager().createNamedQuery(Apartment.NQ_FIND_ALL_MY, Apartment.class);
        query.setParameter("id", user.getId());
        return query.getResultList();
    }

    @Override
    public List<Apartment> findAllOther(User user) {
        TypedQuery<Apartment> query = getEntityManager().createNamedQuery(Apartment.NQ_FIND_ALL_OTHER, Apartment.class);
        query.setParameter("id", user.getId());
        return query.getResultList();
    }

    @Override
    public List<Apartment> findAllEnabledForRenterPageable(User user, PaginationInfoDto apartmentPageInfo) {
        int currentPage = apartmentPageInfo.getCurrentPage();
        int pageSize = apartmentPageInfo.getPageSize();
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Apartment> query = criteriaBuilder.createQuery(Apartment.class);
        Root<Apartment> root = query.from(Apartment.class);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.equal(root.<Role> get("renter"), user));
        predicates.add(criteriaBuilder.equal(root.<ApartmentStatus> get("status"), ApartmentStatus.ENABLED));
        predicates.add(criteriaBuilder.equal(root.<Boolean> get("published"), true));
        query.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));
        List<Apartment> resultList = findByRange(query, (currentPage - 1) * pageSize, pageSize);
        Long pageCount = getEntityPagesCount(getEntityCount(criteriaBuilder, query, root), pageSize);
        apartmentPageInfo.setPageCount(pageCount);
        return resultList;
    }

    @Override
    public List<Apartment> filterApartments(ApartmentOrders orderFilter, int currentPage, int pageSize) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Apartment> query = criteriaBuilder.createQuery(Apartment.class);
        Root<Apartment> entityRoot = query.from(Apartment.class);
        query.select(entityRoot);
        ArrayList<Predicate> predicates = new ArrayList<>();
        
        if (orderFilter.getCity() != null && orderFilter.getCity() != "") {
            predicates.add(criteriaBuilder.like(entityRoot.<String> get("city"), "%" + orderFilter.getCity() + "%"));
        }
        
        if (orderFilter.getStartDate() != null && orderFilter.getEndDate() != null) {
            Join<Apartment, Reserved> reservedJoin = entityRoot.join("reserved");
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(reservedJoin.<LocalDate> get("dateStartReservation"),
                    orderFilter.getStartDate()));
            predicates.add(criteriaBuilder.lessThanOrEqualTo(reservedJoin.<LocalDate> get("dateEndReservation"),
                    orderFilter.getEndDate()));
        }
        
        if (orderFilter.getStartRaiting() != null && orderFilter.getEndRaiting() != null) {
            predicates.add(criteriaBuilder.between(entityRoot.<Float> get("raiting"), orderFilter.getStartRaiting(),
                    orderFilter.getEndRaiting()));
        }
        
        if (orderFilter.getStartPrice() != null && orderFilter.getEndPrice() != null) {
            predicates.add(criteriaBuilder.between(entityRoot.<Float> get("price"), orderFilter.getStartPrice(),
                    orderFilter.getEndPrice()));
        }
        
        if (orderFilter.getMaxCountGuests() != null && orderFilter.getMaxCountGuests() != 0) {
            predicates.add(criteriaBuilder.equal(entityRoot.<Integer> get ("maxCountGuests"), orderFilter.getMaxCountGuests()));
        }
        
        if (orderFilter.getName() != null && orderFilter.getName() != "") {
            predicates.add(criteriaBuilder.like(entityRoot.<String> get("name"), "%" + orderFilter.getName() + "%"));
        }
        
        predicates.add(criteriaBuilder.equal(entityRoot.<Boolean> get("published"), true));
        predicates.add(criteriaBuilder.equal(entityRoot.<ApartmentStatus> get("status"), ApartmentStatus.ENABLED));

        if (orderFilter.getName() != null && orderFilter.getSortBy().equals("sortByPrice")) {
            query.orderBy(criteriaBuilder.asc(entityRoot.<Apartment> get("price")));
        } else if (orderFilter.getName() != null && orderFilter.getSortBy().equals("sortByRating")) {
            query.orderBy(criteriaBuilder.asc(entityRoot.<Apartment> get("raiting")));
        } else {
            query.orderBy(criteriaBuilder.asc(entityRoot.<Apartment> get("city")));    
        }
 
        query.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));
        
        List<Apartment> resultList = findByRange(query, (currentPage - 1) * pageSize, pageSize);
        Long pageCount = (long) Math.ceil(((double) (getEntityCount(criteriaBuilder, query, entityRoot))) / pageSize);
        orderFilter.setPageCount(pageCount.intValue());
        orderFilter.setCurrentPage(currentPage);
        return resultList;
    }
    
}
