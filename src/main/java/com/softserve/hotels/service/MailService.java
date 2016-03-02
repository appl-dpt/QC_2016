package com.softserve.hotels.service;

import com.softserve.hotels.model.Reserved;
import com.softserve.hotels.model.User;

public interface MailService {
    void sendMessage(User user, String subject, String text);

    String buildRegisterMessage(User user, String token);

    String buildUnbookMessage(User user);

    String buildReminderMessage(Reserved reserved);

    String buildUnbookMessageBecauseFake(User user);

    String buildConfirmReservMessage(User user);
}
