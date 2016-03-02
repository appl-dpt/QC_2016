package com.softserve.hotels.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;

public class AjaxModeratorMap {

    @JsonView
    private Float latitude;

    @JsonView
    private Float longitude;

    @JsonView
    private List<ApartmentLocation> myApartments;

    @JsonView
    private List<ApartmentLocation> freeApartments;

    @JsonView
    private List<ApartmentLocation> otherApartments;

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

    public List<ApartmentLocation> getMyApartments() {
        return myApartments;
    }

    public void setMyApartments(List<ApartmentLocation> apartments) {
        this.myApartments = apartments;
    }

    public List<ApartmentLocation> getFreeApartments() {
        return freeApartments;
    }

    public void setFreeApartments(List<ApartmentLocation> apartments) {
        this.freeApartments = apartments;
    }

    public List<ApartmentLocation> getOtherApartments() {
        return otherApartments;
    }

    public void setOtherApartments(List<ApartmentLocation> apartments) {
        this.otherApartments = apartments;
    }

}
