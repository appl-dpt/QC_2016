package com.softserve.hotels.dto;

import com.fasterxml.jackson.annotation.JsonView;

public class ApartmentLocation {

    @JsonView
    private Integer id;

    @JsonView
    private String name;

    @JsonView
    private Float latitude;

    @JsonView
    private Float longitude;

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

}
