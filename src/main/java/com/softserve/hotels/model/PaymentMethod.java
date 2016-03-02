package com.softserve.hotels.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@NamedQueries ({
@NamedQuery(
        query = "SELECT c.name FROM PaymentMethod c",
        name = PaymentMethod.NQ_ALL_PAYMENTS_NAMES
        ),
@NamedQuery(
        query = "FROM PaymentMethod c WHERE c.enabled = true",
        name = PaymentMethod.NQ_ALL_ENABLED
        )
})
@Entity
@Table(name = "PaymentMethods")
public class PaymentMethod {
    
    public static final String NQ_ALL_PAYMENTS_NAMES = "getAllPaymentsNames";
    public static final String NQ_ALL_ENABLED = "getAllEnabled";
    private static final int LENGTH_NAME = 100;
    private static final int LENGTH_ICON = 1000000;
    
    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "name", length = LENGTH_NAME)
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "paymentMethod", fetch = FetchType.EAGER, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<ApartmentPayment> apartmentPayments;
    
    @Column(name = "enabled")
    private Boolean enabled;
    
    public PaymentMethod() {
        enabled = true;
    }
    
    @Column(name = "icon", length = LENGTH_ICON)
    private String icon;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<ApartmentPayment> getApartmentPayments() {
        return apartmentPayments;
    }

    public void setApartmentPayments(Set<ApartmentPayment> apartmentPayments) {
        this.apartmentPayments = apartmentPayments;
    }
    
    public void setIcon(String icon) {
        this.icon = icon;
    }
    
    public String getIcon() {
        return icon;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
    
    

}
