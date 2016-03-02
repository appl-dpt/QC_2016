package com.softserve.hotels.dto;

import java.util.List;

public class ApartmentConveniencesDto {

    private Integer id;
    private Integer maxCountGuests;
    private List<String> conveniences;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMaxCountGuests() {
        return maxCountGuests;
    }

    public void setMaxCountGuests(Integer maxCountGuests) {
        this.maxCountGuests = maxCountGuests;
    }

    public List<String> getConveniences() {
        return conveniences;
    }

    public void setConveniences(List<String> convenience) {
        this.conveniences = convenience;
    }

}
