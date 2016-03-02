package com.softserve.hotels.service;

import java.util.List;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.softserve.hotels.annotations.Loggable;
import com.softserve.hotels.dao.ReservedDao;
import com.softserve.hotels.model.Reserved;

@Loggable
@Service
public class TravelReminderServiceImpl implements TravelReminderService {

    private static final Integer DAYS_OFFSET = 5;
    private static final String MAIL_SUBJECT = "We wait you in " + DAYS_OFFSET + " days!";
    @Autowired
    private MailService mailService;

    @Autowired
    private ReservedDao reservedDao;

    @Scheduled(cron = "0 0 0 * * ?")
    @Override
    public void remind() {
        LocalDate dateForReminder = new LocalDate();
        dateForReminder = dateForReminder.plusDays(DAYS_OFFSET);
        List<Reserved> reservationsToRemind = reservedDao.findActiveForDate(dateForReminder);
        for (Reserved item : reservationsToRemind) {
            String message = mailService.buildReminderMessage(item);
            mailService.sendMessage(item.getTenant(), MAIL_SUBJECT, message);
        }
    }

}
