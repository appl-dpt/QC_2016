package com.softserve.hotels.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "actionapartment")
public class ActionApartment implements Serializable {

    private static final long serialVersionUID = 2048756111177892452L;
    private static final int COMMENT_LENGTH = 300;
    
    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    @Column(name = "id", unique = true, nullable = false)
    private Long id;
    
    @Column(name = "status")
    private ApartmentStatus status;
    
    @Column(name = "comment", length = COMMENT_LENGTH)
    private String comment;
    
    @ManyToOne
    @JoinColumn(name = "apartmentId")
    private Apartment apartment;

    @ManyToOne
    @JoinColumn(name = "moderatorId")
    private User moderator;
    
    @Column(name = "dateAction")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    private LocalDateTime dateTimeAction;
    
    public ActionApartment() {
        dateTimeAction = new LocalDateTime();
    }
    
    public ActionApartment(Apartment apartment, ApartmentStatus status) {
        this.apartment = apartment;
        this.status = status;
        dateTimeAction = new LocalDateTime();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ApartmentStatus getStatus() {
        return status;
    }

    public void setStatus(ApartmentStatus status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
    
    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }

    public User getModerator() {
        return moderator;
    }

    public void setModerator(User moderator) {
        this.moderator = moderator;
    }
    
    public LocalDateTime getDateTimeAction() {
        return dateTimeAction;
    }

    public void setDateTimeAction(LocalDateTime dateTimeAction) {
        this.dateTimeAction = dateTimeAction;
    }
    
    public String getDateTimeActionS() {
        DateTimeFormatter fmt = org.joda.time.format.DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        return fmt.print(dateTimeAction);
    }

}
