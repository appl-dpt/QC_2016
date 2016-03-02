package com.softserve.hotels.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@NamedQueries({
    @NamedQuery(
        name = ApartmentPayment.NQ_GET_AVAILABLE,
        query = "From ApartmentPayment WHERE apartmentId = :apartmentId AND isexists = TRUE order by id desc"
    )
})

@Entity
@Table(name = "apartmentpayments")
public class ApartmentPayment {
    
    public static final String NQ_GET_AVAILABLE = "getAvailableForApartment";
    
    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "isexists")
    private Boolean exists;

    @ManyToOne(optional = false)
    @JoinColumn(name = "apartmentId")
    private Apartment apartment;

    @ManyToOne(optional = false)
    @JoinColumn(name = "paymentId")
    private PaymentMethod paymentMethod;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getExists() {
        return exists;
    }

    public void setExists(Boolean exists) {
        this.exists = exists;
    }

    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

}
