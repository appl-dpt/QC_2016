package com.softserve.hotels.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Table(name = "ActionReservation")
public class ActionReservation implements Serializable {

    private static final long serialVersionUID = 4029744886306706539L;
    private static final int LENGTH_COMMENT = 150;

    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "dateAction")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    private LocalDateTime dateTimeAction;

    @ManyToOne
    @JoinColumn(name = "reservationId", nullable = false)
    private Reserved reservation;

    @Column(name = "status")
    @Enumerated(EnumType.ORDINAL)
    private ActionStatus status;

    @Column(name = "comment", length = LENGTH_COMMENT)
    private String comment;

    public ActionReservation() {
        status = ActionStatus.APROVED;
        dateTimeAction = new LocalDateTime();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getDateTimeAction() {
        return dateTimeAction;
    }

    public void setDateTimeAction(LocalDateTime dateTimeAction) {
        this.dateTimeAction = dateTimeAction;
    }

    public Reserved getReservation() {
        return reservation;
    }

    public void setReservation(Reserved reservation) {
        this.reservation = reservation;
    }

    public ActionStatus getStatus() {
        return status;
    }

    public void setStatus(ActionStatus status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDateTimeActionS() {
        DateTimeFormatter fmt = org.joda.time.format.DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        return fmt.print(dateTimeAction);
    }

}
