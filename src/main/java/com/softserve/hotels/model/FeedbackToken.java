package com.softserve.hotels.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@NamedQueries({
        @NamedQuery(name = FeedbackToken.NQ_FIND_ALL_FOR_APARTMENT,
                query = "SELECT fb.feedback.id FROM FeedbackToken fb WHERE fb.feedback.apartment.id = :id"),
        @NamedQuery(name = FeedbackToken.NQ_FIND_BY_FEEDBACK, query = "FROM FeedbackToken WHERE feedback.id = :id") })
@Entity
@Table(name = "feedbacktoken")
public class FeedbackToken implements Serializable {

    private static final long serialVersionUID = -2672311939208676305L;

    public static final String NQ_FIND_ALL_FOR_APARTMENT = "findAllForApartment";
    public static final String NQ_FIND_BY_FEEDBACK = "findByFeedback";

    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @OneToOne(targetEntity = Feedback.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "feedbackid")
    private Feedback feedback;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Feedback getFeedback() {
        return feedback;
    }

    public void setFeedback(Feedback feedback) {
        this.feedback = feedback;
    }

}
