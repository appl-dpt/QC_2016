package com.softserve.hotels.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.softserve.hotels.model.ActionReservation;

@Service("actionReservationService")
@Transactional
public class ActionReservationServiceImpl extends AbstractServiceImpl<ActionReservation>
        implements ActionReservationService {

}
