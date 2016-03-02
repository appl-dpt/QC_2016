package com.softserve.hotels.model;

import java.io.Serializable;

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

@NamedQueries({ @NamedQuery(name = ApartmentConveniences.NQ_FIND_CONVENIENCE_FOR_APARTMENT,
        query = "from ApartmentConveniences "
                + "WHERE apartment.id = :apartmentId AND convenience.name LIKE :convenience") })
@Entity
@Table(name = "apartmentconveniences")
public class ApartmentConveniences implements Serializable {

    private static final long serialVersionUID = -3165947934368296284L;

    public static final String NQ_FIND_CONVENIENCE_FOR_APARTMENT = "findConvenianceForApartment";

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
    @JoinColumn(name = "convenienceId")
    private Convenience convenience;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }

    public Convenience getConvenience() {
        return convenience;
    }

    public void setConvenience(Convenience convenience) {
        this.convenience = convenience;
    }

    public Boolean getExists() {
        return exists;
    }

    public void setExists(Boolean exists) {
        this.exists = exists;
    }

    @Override
    public String toString() {
        String result = apartment.getName() + " " + convenience.getName();
        if (exists)
            result += " exists";
        else
            result += " not exists";
        return result;
    }

}
