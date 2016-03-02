package com.softserve.hotels.validators;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.softserve.hotels.controller.PhotoController;
import com.softserve.hotels.dto.ApartmentPaymentDto;

@Component
public class ApartmentPaymentDtoValidator implements Validator {
    
    public static final Logger LOG = LogManager.getLogger(PhotoController.class);

	@Override
	public boolean supports(Class<?> arg0) {
		return ApartmentPaymentDto.class.equals(arg0);
	}

	@Override
	public void validate(Object object, Errors errors) {
		ApartmentPaymentDto payment = (ApartmentPaymentDto) object;
		float price = 0;
		try {
			price = payment.getPrice();
		} catch (Exception e) {
		    LOG.error(e);
			errors.rejectValue("price", "apartmentDetail.priceWrongFormat");
		}
		if (price < 0) 
			errors.rejectValue("price", "apartmentDetail.negativePrice");
		if (payment.getPayments() == null) {
		    errors.rejectValue("payments", "apartmentDetail.notChosenPayments");
		}
	}

}
