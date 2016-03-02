package com.softserve.hotels.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "Feedback")
public class Feedback implements Serializable {

    private static final long serialVersionUID = -2456314561385485758L;
    public static final Integer MAX_RATING = 5;

    @Id
    @SequenceGenerator(name = "feedback_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "feedback_seq")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @ManyToOne()
    @JoinColumn(name = "apartment_id")
    private Apartment apartment;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User feedbacker;

    @Column(name = "description")
    private String description;

    @Column(name = "rating")
    private Float rating;

    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

    public Feedback() {
        enabled = true;
    }

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

    public void setFeedbacker(User feedbacker) {
        this.feedbacker = feedbacker;
    }

    public User getFeedbacker() {
        return feedbacker;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public Float getRating() {
        return rating;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

}
