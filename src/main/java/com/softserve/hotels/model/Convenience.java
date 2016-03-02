package com.softserve.hotels.model;

import java.io.Serializable;
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

@NamedQueries({ @NamedQuery(name = Convenience.NQ_ALL_CONVENIENCE_NAMES, query = "SELECT c.name FROM Convenience c") })
@Entity
@Table(name = "Conveniences")
public class Convenience implements Serializable {

    private static final long serialVersionUID = 8000509581629489104L;
    private static final int LENGTH_NAME = 50;

    public static final String NQ_ALL_CONVENIENCE_NAMES = "getAllConvenienceNames";

    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "name", length = LENGTH_NAME)
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "convenience", fetch = FetchType.EAGER, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<ApartmentConveniences> apartmentConveniences;

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

    public Set<ApartmentConveniences> getApartmentConveniences() {
        return apartmentConveniences;
    }

    public void setApartmentConveniences(Set<ApartmentConveniences> apartmentConveniences) {
        this.apartmentConveniences = apartmentConveniences;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Convenience))
            return false;
        Convenience convenience = (Convenience) obj;
        return this.name.equals(convenience.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

}
