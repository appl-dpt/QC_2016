package com.softserve.hotels.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

@NamedQueries({
    @NamedQuery(
        name = Apartment.NQ_FIND_ALL_ORDER_BY_ID_DESC,
        query = "From Apartment order by id desc"
    ),
    @NamedQuery(
            name = Apartment.NQ_UPDATE_APARTMENT_INFO,
            query = "UPDATE Apartment c SET c.name = :name, "
                    + "c.description = :description, c.address = :address, " 
                    + "c.city = :city WHERE c.id = :id"
        ),
    @NamedQuery(
            name = Apartment.NQ_FIND_ALL_ENABLED_FOR_USER,
            query = "From Apartment WHERE renter = :renter "
                    + "AND published=true AND status = :status"
        ),
    @NamedQuery(
            name = Apartment.NQ_FIND_ALL_DISABLED_FOR_USER,
            query = "From Apartment WHERE renter = :renter AND status != :status"
        ),
    @NamedQuery(
            name = Apartment.NQ_FIND_ALL_UNPUBLISHED_FOR_USER,
            query = "From Apartment WHERE renter = :renter "
                    + "AND published=false AND status = :status"
        ),
    @NamedQuery(
            name = Apartment.NQ_FIND_ALL_STATUS_FOR_MODERATOR,
            query = "From Apartment WHERE moderator = :moderator "
                    + "AND status = :status"
        ),
    @NamedQuery(
            name = Apartment.NQ_FIND_ALL_FREE,
            query = "From Apartment WHERE moderator = NULL AND published = true"
        ),
    @NamedQuery(
            name = Apartment.NQ_FIND_ALL_MY,
            query = "From Apartment WHERE moderator.id = :id"
        ),
    @NamedQuery(
            name = Apartment.NQ_FIND_ALL_OTHER,
            query = "From Apartment WHERE moderator.id != :id"
        )
})
@Entity
@Table(name = "Apartments")
public class Apartment implements Serializable {

    private static final long serialVersionUID = -8531370975144419593L;
    private static final int LENGTH_NAME = 300;
    private static final int LENGTH_DESCRIPTION = 300;
    private static final int LENGTH_ADRESS = 100;
    private static final int LENGTH_CITY = 100;
    
    public static final String NQ_FIND_ALL_ORDER_BY_ID_DESC = "findAllInDescOrder";
    public static final String NQ_UPDATE_APARTMENT_INFO = "updateApartmentInfo";
    public static final String NQ_FIND_ALL_ENABLED_FOR_USER = "findAllEnabledForUser";
    public static final String NQ_FIND_ALL_DISABLED_FOR_USER = "findAllDisabledForUser";
    public static final String NQ_FIND_ALL_UNPUBLISHED_FOR_USER = "findAllUnpublishedForUser";
    public static final String NQ_FIND_ALL_STATUS_FOR_MODERATOR = "findAllEnabledForModerator";
    public static final String NQ_FIND_ALL_FREE = "findAllFree";
    public static final String NQ_FIND_ALL_MY = "findAllMy";
    public static final String NQ_FIND_ALL_OTHER = "findAllOther";

    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "name", length = LENGTH_NAME)
    private String name;

    @NotNull
    @Column(name = "city", length = LENGTH_CITY)
    private String city;

    @Column(name = "description", length = LENGTH_DESCRIPTION)
    private String description;

    @NotNull
    @Column(name = "address", length = LENGTH_ADRESS)
    private String address;

    @Column(name = "aproved")
    private Boolean aproved;

    @Column(name = "raiting")
    private Float raiting;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "apartment", fetch = FetchType.EAGER)
    @OrderBy("priority ASC")
    private Set<LinkPhoto> links;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "apartment", fetch = FetchType.EAGER)
    @OrderBy("id DESC")
    private Set<Feedback> feedbacks;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "apartment")
    private Set<Reserved> reserved;

    @ManyToOne
    @JoinColumn(name = "renter_id", nullable = false)
    private User renter;

    @ManyToOne
    @JoinColumn(name = "moderator_id")
    private User moderator;

    @Column(name = "latitude")
    private Float latitude;

    @Column(name = "longitude")
    private Float longitude;
    
    @Column(name = "status")
    private ApartmentStatus status;
    
    @Column(name = "maxCountGuests")
    private Integer maxCountGuests;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "apartment", fetch = FetchType.EAGER)
    @OrderBy("id ASC")
    private Set<ApartmentConveniences> apartmentConveniences;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "apartment", fetch = FetchType.EAGER)
    @OrderBy("id ASC")
    private Set<ApartmentPayment> apartmentPayments;

    @Column(name = "published")
    private Boolean published;
    
    @Column(name = "price")
    private Float price;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "apartment", fetch = FetchType.EAGER)
    @OrderBy("id ASC")
    private Set<ActionApartment> apartmentActions;

    public Apartment() {
        id = 0;
        aproved = false;
        raiting = 0.0F;
        latitude = 0.0F;
        longitude = 0.0F;
        status = ApartmentStatus.ENABLED;
        maxCountGuests = 2;
        published = false;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean getAproved() {
        return aproved;
    }

    public void setAproved(boolean aproved) {
        this.aproved = aproved;
    }

    public Float getRaiting() {
        return raiting;
    }

    public void setRaiting(Float raiting) {
        this.raiting = raiting;
    }

    public Set<LinkPhoto> getLinks() {
        return links;
    }

    public void setLinks(Set<LinkPhoto> links) {
        this.links = links;
    }

    public void setFeedbacks(Set<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }

    public Set<Feedback> getFeedback() {
        return feedbacks;
    }

    public void setRenter(User renter) {
        this.renter = renter;
    }

    public User getRenter() {
        return renter;
    }

    public void setModerator(User moderator) {
        this.moderator = moderator;
    }

    public User getModerator() {
        return moderator;
    }

    public Set<Reserved> getReserved() {
        return reserved;
    }

    public void setReserved(Set<Reserved> reserved) {
        this.reserved = reserved;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }
    
    public ApartmentStatus getStatus() {
		return status;
	}

	public void setStatus(ApartmentStatus status) {
		this.status = status;
	}
	
	public Integer getMaxCountGuests() {
		return maxCountGuests;
	}

	public void setMaxCountGuests(Integer maxCountGuests) {
		this.maxCountGuests = maxCountGuests;
	}
	
	public Set<ApartmentConveniences> getApartmentConveniences() {
        return apartmentConveniences;
    }

    public void setApartmentConveniences(Set<ApartmentConveniences> apartmentConveniences) {
        this.apartmentConveniences = apartmentConveniences;
    }
    
    public Set<ApartmentPayment> getApartmentPayments() {
        return apartmentPayments;
    }

    public void setApartmentPayments(Set<ApartmentPayment> apartmentPayments) {
        this.apartmentPayments = apartmentPayments;
    }
    
    public Boolean getPublished() {
		return published;
	}

	public void setPublished(Boolean published) {
		this.published = published;
	}
    
    public Set<ActionApartment> getApartmentActions() {
        return apartmentActions;
    }

    public void setApartmentActions(Set<ActionApartment> apartmentActions) {
        this.apartmentActions = apartmentActions;
    }
    
    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
    
    public ActionApartment getFirstAction() {
        return apartmentActions.iterator().next();
    }
    
    public ActionApartment getLastAction() {
        ActionApartment last = null;
        for (ActionApartment actionApartment : apartmentActions) {
            last = actionApartment;
        }
        return last;
    }

    @Override
    public String toString() {
        return name + " " + city;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Apartment))
            return false;
        Apartment apartment = (Apartment) obj;
        return this.name.equals(apartment.name) && this.city.equals(apartment.city);
    }

    @Override
    public int hashCode() {
    	return super.hashCode();
    }

}
