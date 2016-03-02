package com.softserve.hotels.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

@NamedQueries({
        @NamedQuery(name = Reserved.NQ_FIND_ACTIVE_BY_APARTMENT,
                query = "from Reserved where apartment.id = :reserved AND status = :active"),
        @NamedQuery(name = Reserved.NQ_FIND_FUTURE_BY_APARTMENT,
                query = "SELECT r FROM Reserved r WHERE r.apartment.id = :idApartment "
                        + "AND r.dateStartReservation > :now  AND r.status = :status "
                        + "ORDER BY r.dateStartReservation"),
        @NamedQuery(name = Reserved.NQ_FIND_PAST_BY_APARTMENT,
                query = "SELECT r FROM Reserved r WHERE r.apartment.id = :idApartment "
                        + "AND r.status != :status ORDER BY r.dateStartReservation DESC"),
        @NamedQuery(name = Reserved.NQ_FIND_CURRENT_BY_APARTMENT,
                query = "SELECT r FROM Reserved r WHERE r.apartment.id = :idApartment "
                        + "AND r.dateStartReservation <= :now AND r.dateEndReservation >= :now "
                        + "AND r.status = :status ORDER BY r.dateStartReservation"),
        @NamedQuery(name = Reserved.NQ_FIND_FUTURE_RESERVED_FOR_USER,
                query = "SELECT reserved FROM Reserved reserved " + "WHERE reserved.tenant.id = :idTenant "
                        + "AND reserved.dateStartReservation > :now " + "ORDER BY reserved.dateStartReservation DESC"),
        @NamedQuery(name = Reserved.NQ_FIND_PAST_RESERVED_FOR_USER,
                query = "SELECT reserved FROM Reserved reserved WHERE reserved.tenant.id = :idTenant "
                        + "AND reserved.dateEndReservation < :now ORDER BY reserved.dateStartReservation DESC"),
        @NamedQuery(name = Reserved.NQ_FIND_ACTIVE_FOR_DATE,
                query = "from Reserved where dateStartReservation = :date AND status = :status"),
        @NamedQuery(name = Reserved.NQ_FIND_LAST_UNPAYED_FOR_USER,
        query = "SELECT reserved FROM Reserved reserved where reserved.orderingTime = "
                + "(SELECT max(reserved.orderingTime) FROM reserved) AND reserved.tenant.id = :tenantId "
                + "AND reserved.status = :status"),
        @NamedQuery(name = Reserved.NQ_FIND_ALL_UNPAYED_FOR_USER,
        query = "SELECT reserved FROM Reserved reserved where reserved.status = :status AND reserved.tenant.id = :tenantId"),
        @NamedQuery(name = Reserved.NQ_FIND_ALL_UNPAYED,
        query = "SELECT reserved FROM Reserved reserved where reserved.status = :status")        
})

@Entity
@Table(name = "Reserved")
public class Reserved implements Serializable {

    private static final long serialVersionUID = -8261632602825309075L;

    public static final String NQ_FIND_ACTIVE_BY_APARTMENT = "findActiveReservedByApartment";
    public static final String NQ_FIND_FUTURE_BY_APARTMENT = "findFutureReservedForApartment";
    public static final String NQ_FIND_PAST_BY_APARTMENT = "findPastReservedForApartment";
    public static final String NQ_FIND_CURRENT_BY_APARTMENT = "findCurrentReservedForApartment";
    public static final String NQ_FIND_FUTURE_RESERVED_FOR_USER = "findFutureReservedForUser";
    public static final String NQ_FIND_PAST_RESERVED_FOR_USER = "findPastReservedForUser";
    public static final String NQ_FIND_ACTIVE_FOR_DATE = "findActiveForDate";
    public static final String NQ_FIND_LAST_UNPAYED_FOR_USER = "findLastUnpayedForUser";
    public static final String NQ_FIND_ALL_UNPAYED_FOR_USER = "findAllUnpayedForUser";
    public static final String NQ_FIND_ALL_UNPAYED = "findAllUnpayed";
    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "apartment_id", nullable = false)
    private Apartment apartment;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User tenant;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "date_start_reservation")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    private LocalDate dateStartReservation;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "date_end_reservation")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    private LocalDate dateEndReservation;

    @Column(name = "status")
    @Enumerated(EnumType.ORDINAL)
    private ActionStatus status;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "reservation", fetch = FetchType.EAGER)
    @OrderBy("id ASC")
    private Set<ActionReservation> actions;
    
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    @Column (name = "ordering_time")
    private LocalDateTime orderingTime;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }

    public Apartment getApartment() {
        return apartment;
    }

    public void setTenant(User tenant) {
        this.tenant = tenant;
    }

    public User getTenant() {
        return tenant;
    }

    public void setDateStartReservation(LocalDate dateStartReservation) {
        this.dateStartReservation = dateStartReservation;
    }

    public LocalDate getDateStartReservation() {
        return dateStartReservation;
    }

    public void setDateEndReservation(LocalDate dateEndReservation) {
        this.dateEndReservation = dateEndReservation;
    }

    public LocalDate getDateEndReservation() {
        return dateEndReservation;
    }

    public ActionStatus getStatus() {
        return status;
    }

    public void setStatus(ActionStatus status) {
        this.status = status;
    }

    public Set<ActionReservation> getActions() {
        return actions;
    }

    public void setActions(Set<ActionReservation> actions) {
        this.actions = actions;
    }
    
    public void setOrderingTime(LocalDateTime orderingTime) {
        this.orderingTime = orderingTime;
    }
    
    public LocalDateTime getOrderingTime() {
        return orderingTime;
    }
}
