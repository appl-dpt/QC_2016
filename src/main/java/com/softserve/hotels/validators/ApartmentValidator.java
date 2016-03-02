package com.softserve.hotels.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.softserve.hotels.model.Apartment;
import com.softserve.hotels.service.ApartmentService;
import com.softserve.hotels.utils.GoogleMapsUtils;

@Component
public class ApartmentValidator implements Validator {

    @Autowired
    private ApartmentService apartmentService;

    @Override
    public boolean supports(Class<?> arg0) {
        return Apartment.class.equals(arg0);
    }

    @Override
    public void validate(Object arg0, Errors errors) {
        Apartment apartment = (Apartment) arg0;
        if (this.apartmentService.findAll().contains(apartment) && apartment.getId() == 0) {
            errors.rejectValue("name", "apartmentDetail.apartmentExists");
        }
        if (apartment.getId() != 0) {
            Apartment oldApartment = this.apartmentService.findById(apartment.getId());
            if ((!apartment.getName().equals(oldApartment.getName())
                    || !apartment.getCity().equals(oldApartment.getCity()))
                    && this.apartmentService.findAll().contains(apartment)) {
                errors.rejectValue("name", "apartmentDetail.apartmentExists");
            }
        }
        if (apartment.getName() == null || "".equals(apartment.getName())) {
            errors.rejectValue("name", "apartmentDetail.emptyName");
        }
        if (apartment.getCity() == null || "".equals(apartment.getCity())) {
            errors.rejectValue("city", "apartmentDetail.emptyCity");
        } else if (GoogleMapsUtils.getLocationCity(apartment.getCity()) == null) {
            errors.rejectValue("city", "apartmentDetail.cityFromList");
        }
        if (apartment.getAddress() == null || "".equals(apartment.getAddress())) {
            errors.rejectValue("address", "apartmentDetail.emptyAddress");
        }
    }

}
