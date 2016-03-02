package com.softserve.hotels.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.softserve.hotels.model.ActionApartment;

@Service("actionApartmentService")
@Transactional
public class ActionApartmentServiceImpl extends AbstractServiceImpl<ActionApartment> implements ActionApartmentService {

}
