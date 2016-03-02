package com.softserve.hotels.dto;

import java.util.List;

public class ApartmentPaymentDto {
    
    private Integer id;
    private Float price;
    private List<String> payments;
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public Float getPrice() {
        return price;
    }
    
    public void setPrice(Float price) {
        this.price = price;
    }
    
    public List<String> getPayments() {
        return payments;
    }
    
    public void setPayments(List<String> payments) {
        this.payments = payments;
    }

}
