package com.softserve.hotels.model;

import java.util.ArrayList;
import java.util.List;

public enum ActionStatus {

    APROVED, WAITING_PAYMENT, WAITING_CONFIRMATION, BAD_DEAL, DECLINED_RENTER, 
    DECLINED_TENANT, FAKE_RESERVATION, AFTER_RESERVATION, AFTER_FEEDBACK, ALL_INACTIVE, ALL_ACTIVE;

    public static List<ActionStatus> getAllActiveStatus() {

        List<ActionStatus> statusList = new ArrayList<>();
        statusList.add(ALL_ACTIVE);
        statusList.add(APROVED);
        statusList.add(WAITING_CONFIRMATION);
        statusList.add(WAITING_PAYMENT);
        return statusList;
    }

    public static List<ActionStatus> getAllInactiveStatus() {

        List<ActionStatus> statusList = new ArrayList<>();
        statusList.add(ALL_INACTIVE);
        statusList.add(BAD_DEAL);
        statusList.add(DECLINED_RENTER);
        statusList.add(DECLINED_TENANT);
        statusList.add(FAKE_RESERVATION);
        statusList.add(AFTER_RESERVATION);
        statusList.add(AFTER_FEEDBACK);
        return statusList;
    }

}
