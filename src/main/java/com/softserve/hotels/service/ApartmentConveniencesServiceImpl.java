package com.softserve.hotels.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.softserve.hotels.dao.ApartmentConveniencesDao;
import com.softserve.hotels.dao.ConvenienceDao;
import com.softserve.hotels.model.Apartment;
import com.softserve.hotels.model.ApartmentConveniences;

@Service("apartmentConveniencesService")
@Transactional
public class ApartmentConveniencesServiceImpl extends AbstractServiceImpl<ApartmentConveniences>
        implements ApartmentConveniencesService {

    @Autowired
    private ApartmentConveniencesDao apartmentConveniencesDao;

    @Autowired
    private ConvenienceDao convenienceDao;

    @Override
    public ApartmentConveniences findConvenianceForApartment(Apartment apartment, String convenience) {
        return apartmentConveniencesDao.findConvenianceForApartment(apartment, convenience);
    }

    @Override
    public void resetAllConveniencesForApartment(Apartment apartment) {
        List<String> conveniences = convenienceDao.getAllConvenienceNames();
        for (String convenience : conveniences) {
            ApartmentConveniences apConvenience = apartmentConveniencesDao.findConvenianceForApartment(apartment,
                    convenience);
            apConvenience.setExists(false);
            apartmentConveniencesDao.update(apConvenience);
        }
    }

}
