package com.softserve.hotels.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

import com.softserve.hotels.annotations.UniqueEmail;

/**
 * @author Rogulya Volodymyr
 */
@NamedQueries({ @NamedQuery(name = User.NQ_FIND_ALL_BY_ROLE, query = "FROM User WHERE role = :role"),
        @NamedQuery(name = User.NQ_FIND_USER_LIKE_EMAIL_AND_BY_ROLE,
                query = "from User WHERE email LIKE :email AND role = :role"), 
        @NamedQuery(name = User.NQ_FIND_USER_LIKE_EMAIL, 
                query = "from User WHERE email LIKE :email")})

@Entity
@Table(name = "USERS")
public class User implements Serializable {

    private static final long serialVersionUID = 6753788052681283622L;
    public static final int NAME_MIN_SIZE = 3;
    public static final int NAME_MAX_SIZE = 30;
    public static final int PASSWORD_MIN_SIZE = 5;
    public static final int PASSWORD_MAX_SIZE = 61;
    public static final int IMAGE_SIZE = 1000000;
    public static final String NQ_FIND_ALL_BY_ROLE = "findAllByRole";
    public static final String NQ_FIND_USER_LIKE_EMAIL_AND_BY_ROLE = "findUserLikeEmailAndByRole";
    public static final String NQ_FIND_USER_LIKE_EMAIL = "findUserLikeEmail";

    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @NotEmpty
    @Email
    @Column(name = "email", unique = true)
    @UniqueEmail(message = "")
    private String email;

    @Size(min = PASSWORD_MIN_SIZE, max = PASSWORD_MAX_SIZE)
    @Column(name = "password")
    private String password;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "role")
    @Enumerated(EnumType.ORDINAL)
    private Role role;

    @Size(min = NAME_MIN_SIZE, max = NAME_MAX_SIZE)
    @Column(name = "nickname")
    private String nickname;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "phonenumber")
    private String phonenumber;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "date_registration")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    private LocalDate dateRegistration;

    @Column(name = "image")
    @Size(max = IMAGE_SIZE)
    private String imageLink;

    @Column(name = "blocked")
    private boolean blocked;

    @Column(name = "visibility")
    private boolean visibility;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "renter")
    private Set<Apartment> rentAppartments;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "moderator")
    private Set<Apartment> moderAppartments;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "tenant")
    private Set<Reserved> tenants;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "feedbacker")
    private Set<Feedback> feedbackers;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enable) {
        this.enabled = enable;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public LocalDate getDateRegistration() {
        return dateRegistration;
    }

    public void setDateRegistration(LocalDate dateRegistration) {
        this.dateRegistration = dateRegistration;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public boolean getBlocked() {
        return blocked;
    }

    public void setBlocked(Boolean blocked) {
        this.blocked = blocked;
    }

    public boolean getVisibility() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    public Set<Apartment> getRentAppartments() {
        return rentAppartments;
    }

    public void setRentAppartments(Set<Apartment> rentAppartments) {
        this.rentAppartments = rentAppartments;
    }

    public Set<Apartment> getModerAppartments() {
        return moderAppartments;
    }

    public void setModerAppartments(Set<Apartment> moderAppartments) {
        this.moderAppartments = moderAppartments;
    }

    public Set<Reserved> getTenants() {
        return tenants;
    }

    public void setTenants(Set<Reserved> tenants) {
        this.tenants = tenants;
    }

    public Set<Feedback> getFeedbackers() {
        return feedbackers;
    }

    public void setFeedbackers(Set<Feedback> feedbackers) {
        this.feedbackers = feedbackers;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", role=" + role + "]";
    }

}
