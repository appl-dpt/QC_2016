package com.softserve.hotels.utils;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.maxmind.geoip.Location;
import com.softserve.hotels.dto.AjaxModeratorMap;
import com.softserve.hotels.dto.ApartmentLocation;
import com.softserve.hotels.model.Apartment;
import com.softserve.hotels.model.User;
import com.softserve.hotels.service.ApartmentService;

@Component
public class AjaxModeratorMapDTOBuilder {

    @Autowired
    private ApartmentService apartmentService;

    public AjaxModeratorMap build(User moderator) {
        AjaxModeratorMap result = new AjaxModeratorMap();
        Location location = GoogleMapsUtils.getLocationIP();
        result.setLatitude(location.latitude);
        result.setLongitude(location.longitude);
        ArrayList<ApartmentLocation> myApartments = new ArrayList<>();
        for (Apartment apartment : apartmentService.findAllMy(moderator)) {
            ApartmentLocation apartmentLocation = new ApartmentLocation();
            apartmentLocation.setLatitude(apartment.getLatitude());
            apartmentLocation.setLongitude(apartment.getLongitude());
            apartmentLocation.setName(apartment.getName());
            apartmentLocation.setId(apartment.getId());
            myApartments.add(apartmentLocation);
        }
        result.setMyApartments(myApartments);
        ArrayList<ApartmentLocation> freeApartments = new ArrayList<>();
        for (Apartment apartment : apartmentService.findAllFree()) {
            ApartmentLocation apartmentLocation = new ApartmentLocation();
            apartmentLocation.setLatitude(apartment.getLatitude());
            apartmentLocation.setLongitude(apartment.getLongitude());
            apartmentLocation.setName(apartment.getName());
            apartmentLocation.setId(apartment.getId());
            freeApartments.add(apartmentLocation);
        }
        result.setFreeApartments(freeApartments);
        ArrayList<ApartmentLocation> otherApartments = new ArrayList<>();
        for (Apartment apartment : apartmentService.findAllOther(moderator)) {
            ApartmentLocation apartmentLocation = new ApartmentLocation();
            apartmentLocation.setLatitude(apartment.getLatitude());
            apartmentLocation.setLongitude(apartment.getLongitude());
            apartmentLocation.setName(apartment.getName());
            apartmentLocation.setId(apartment.getId());
            otherApartments.add(apartmentLocation);
        }
        result.setOtherApartments(otherApartments);
        return result;
    }

}
