package com.softserve.hotels.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class IpAdressUtils {

    private static final String IP_SERVICE = "http://myip.by/";
    private static final int LENGTH_OF_BUFFER = 1024;
    private static final int IP_COUNT_NUMBER = 4;
    private static final int IP_OFFSET = 8;

    private static final Logger LOG = LogManager.getLogger(IpAdressUtils.class);

    private IpAdressUtils() {
    }

    public static String getCurrentIP() {
        String result = null;
        try {
            URL url = new URL(IP_SERVICE);
            InputStream inputStream = url.openStream();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                StringBuilder allText = new StringBuilder();
                char[] buff = new char[LENGTH_OF_BUFFER];

                int count;
                while ((count = reader.read(buff)) != -1) {
                    allText.append(buff, 0, count);
                }
                Integer indStart = allText.indexOf("\">whois ");
                Integer indEnd = allText.indexOf("</a>", indStart);

                String ipAddress = new String(allText.substring(indStart + IP_OFFSET, indEnd));
                if (ipAddress.split("\\.").length == IP_COUNT_NUMBER) {
                    result = ipAddress;
                }
            }
        } catch (MalformedURLException ex) {
            LOG.error("Bad ULR request to IP service");
            LOG.error(ex);
        } catch (IOException ex) {
            LOG.error("Can't open response from IP service");
            LOG.error(ex);
        }
        return result;
    }

}
