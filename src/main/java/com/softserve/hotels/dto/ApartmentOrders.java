package com.softserve.hotels.dto;

import org.joda.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

import com.softserve.hotels.model.Apartment;

public class ApartmentOrders {

    private static final int PAGES_RANGE = 5;

    private Apartment apartment;
    private String name;
    private String city;
    private Integer maxCountGuests;
    private String sortBy;
    
    private Float startRaiting;
    private Float endRaiting;
    private Float startPrice;
    private Float endPrice;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    
    private int startPage = 1;
    private int endPage = PAGES_RANGE;
    private int pageCount;
    private int currentPage = 1;

    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getMaxCountGuests() {
        return maxCountGuests;
    }

    public void setMaxCountGuests(Integer maxCountGuests) {
        this.maxCountGuests = maxCountGuests;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public Float getStartRaiting() {
        return startRaiting;
    }

    public void setStartRaiting(Float startRaiting) {
        this.startRaiting = startRaiting;
    }

    public Float getEndRaiting() {
        return endRaiting;
    }

    public void setEndRaiting(Float endRaiting) {
        this.endRaiting = endRaiting;
    }

    public Float getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(Float startPrice) {
        this.startPrice = startPrice;
    }

    public Float getEndPrice() {
        return endPrice;
    }

    public void setEndPrice(Float endPrice) {
        this.endPrice = endPrice;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getStartPage() {
        return startPage;
    }

    public void setStartPage(int startPage) {
        this.startPage = startPage;
    }

    public int getEndPage() {
        return endPage;
    }

    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
        startPage = (currentPage % PAGES_RANGE == 0) ? currentPage - (PAGES_RANGE - 1)
                : ((currentPage / PAGES_RANGE) * PAGES_RANGE + 1);
        endPage = (startPage + PAGES_RANGE) - 1;
        if (endPage >= pageCount) {
            endPage = pageCount;
        }
    }

    public int getPagesRange() {
        return PAGES_RANGE;
    }
}
