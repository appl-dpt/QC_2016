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
@NamedQueries({
    @NamedQuery(name = LinkPhoto.NQ_GET_URL, 
            query = "SELECT p.url FROM LinkPhoto p")
})
@Entity
@Table(name = "Photos")
public class LinkPhoto implements Serializable {

    private static final long serialVersionUID = 3711923352577628250L;
    private static final int LENGTH_URL = 200;
    private static final int LENGTH_NAME = 50;

    public static final String NQ_GET_URL = "getUrl";
    
    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "name", length = LENGTH_NAME)
    private String name;

    @Column(name = "url", length = LENGTH_URL)
    private String url;

    @Column(name = "priority")
    private Integer priority;

    @ManyToOne
    @JoinColumn(name = "apartment_id")
    private Apartment apartment;

    public LinkPhoto() {
        url = "";
        id = 0;
        apartment = null;
    }

    public int getId() {
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

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }

    public Apartment getApartment() {
        return apartment;
    }

    public int getApartmentID() {
        return apartment.getId();
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return url;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof LinkPhoto))
            return false;
        LinkPhoto photo = (LinkPhoto) obj;
        return this.id == photo.id;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

}
