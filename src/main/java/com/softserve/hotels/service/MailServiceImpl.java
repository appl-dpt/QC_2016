package com.softserve.hotels.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.softserve.hotels.model.Reserved;
import com.softserve.hotels.model.User;

@Service
public class MailServiceImpl implements MailService {
    private static final Logger LOGGER = LogManager.getLogger(MailServiceImpl.class);

    @Autowired
    private ServletContext context;

    @Autowired
    private JavaMailSender mailSender;

    private Queue<MimeMessage> messageQueue = new ConcurrentLinkedQueue<>();

    private Thread senderThread;

    @Override
    public void sendMessage(User user, String subject, String text) {
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {

            helper.setTo(user.getEmail());
            helper.setText(text, true);
            helper.setSubject(subject);
        } catch (MessagingException e) {
            LOGGER.error(e);
        }
        messageQueue.add(message);
        if (senderThread == null || !senderThread.isAlive()) {
            senderThread = new Thread(new MessageSender(messageQueue, mailSender));
            senderThread.setDaemon(true);
            senderThread.start();
        }
    }

    private static class MessageSender implements Runnable {
        private Queue<MimeMessage> messageQueue;
        private JavaMailSender mailSender;
        private List<Class<? extends MailException>> loggedExceptions = new ArrayList<>();

        MessageSender(Queue<MimeMessage> messageQueue, JavaMailSender mailSender) {
            this.messageQueue = messageQueue;
            this.mailSender = mailSender;
        }

        @Override
        public void run() {

            while (!messageQueue.isEmpty()) {
                MimeMessage message = messageQueue.peek();
                try {
                    mailSender.send(message);
                    messageQueue.poll();
                } catch (MailException e) {
                    if (!loggedExceptions.contains(e.getClass())) {
                        LOGGER.error(e);
                        loggedExceptions.add(e.getClass());
                    }
                }
            }
        }
    }

    @Override
    public String buildRegisterMessage(User user, String token) {
        String confirmationUrl = "http://" + context.getVirtualServerName() + ":8080" + context.getContextPath()
                + "/confirm?token=" + token;
        StringBuilder messageBuilder = new StringBuilder(
                "Hello, " + user.getNickname() + ". Welcome to our little project.");
        messageBuilder.append("To active your account please<a href=\'" + confirmationUrl + "\'> click here</a>. ");
        return messageBuilder.toString();
    }

    @Override
    public String buildUnbookMessage(User user) {
        StringBuilder messageBuilder = new StringBuilder(
                "Hello, " + user.getNickname() + ". Your order was rejected by renter.");
        messageBuilder.append("Sorry for that.");
        return messageBuilder.toString();
    }

    @Override
    public String buildUnbookMessageBecauseFake(User user) {
        StringBuilder messageBuilder = new StringBuilder(
                "Hello, " + user.getNickname() + ". Your order was rejected by renter, because you didn't come. ");
        messageBuilder.append("You are very irresponsible person.");
        return messageBuilder.toString();
    }

    @Override
    public String buildConfirmReservMessage(User user) {
        StringBuilder messageBuilder = new StringBuilder(
                "Hello, " + user.getNickname() + ". Your order was confirmed by renter. ");
        messageBuilder.append("Waiting for you.");
        return messageBuilder.toString();
    }

    @Override
    public String buildReminderMessage(Reserved reserved) {
        StringBuilder messageBuilder = new StringBuilder("Hello, " + reserved.getTenant().getNickname()
                + ". We remind you, that in a 5 days you have to arrive in " + reserved.getApartment().getName());
        return messageBuilder.toString();
    }

}
