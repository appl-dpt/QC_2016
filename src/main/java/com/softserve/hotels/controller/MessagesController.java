package com.softserve.hotels.controller;

import java.util.Locale;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessagesController {
    @Autowired
    private MessageSource messageSource;

    @RequestMapping(value = "/locale", method = RequestMethod.GET)
    public Properties getMessage(@RequestParam(value = "codes[]") String[] codes, Locale locale) {
        Properties result = new Properties();
        for (String code : codes) {
            String message = messageSource.getMessage(code, null, locale);
            result.setProperty(code, message);
        }
        return result;
    }
}
