package com.softserve.hotels.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.softserve.hotels.dao.ApartmentConveniencesDao;
import com.softserve.hotels.dao.ApartmentDAO;
import com.softserve.hotels.dao.ConvenienceDao;
import com.softserve.hotels.model.Apartment;
import com.softserve.hotels.model.ApartmentConveniences;
import com.softserve.hotels.model.Convenience;

@Service("convenienceMethodService")
@Transactional
public class ConvenienceServiceImpl extends AbstractServiceImpl<Convenience> implements ConvenienceService {

    @Autowired
    private ConvenienceDao convenienceDao;

    @Autowired
    private ApartmentDAO apartmentDao;

    @Autowired
    private ApartmentConveniencesDao apartmentConveniencesDao;

    @Override
    public void create(Convenience entity) {
        super.create(entity);
        for (Apartment apartment : apartmentDao.findAll()) {
            ApartmentConveniences apconvenience = new ApartmentConveniences();
            apconvenience.setApartment(apartment);
            apconvenience.setConvenience(entity);
            apconvenience.setExists(false);
            apartmentConveniencesDao.create(apconvenience);
        }
    }

    @Override
    public List<String> getAllConvenienceNames() {
        List<Convenience> conveniences = convenienceDao.findAll();
        List<String> names = new ArrayList<>();
        for (Convenience convenience : conveniences) {
            names.add(convenience.getName());
        }
        return names;
    }

}
