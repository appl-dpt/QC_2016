package com.softserve.hotels.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.maxmind.geoip.Location;
import com.maxmind.geoip.LookupService;

public final class GoogleMapsUtils {

    public static final Logger LOG = LogManager.getLogger(GoogleMapsUtils.class);

    private static final String LOCATION_DATABASE = "location/GeoLiteCity.dat";
    private static final String GET_LOCATION_REQUEST = "https://maps.googleapis.com/maps/api/geocode/json?address=";

    private GoogleMapsUtils() {
    }
    
    public static double distance(Location point1, Location point2) {
        return Math.sqrt(
                Math.pow(point1.latitude - point2.latitude, 2.0) + Math.pow(point1.longitude - point2.longitude, 2.0));
    }

    public static Location getLocationCity(String city) {
        Location resLocation = new Location();
        String findCity = city.split(",")[0];
        try {
            URL url = new URL(GET_LOCATION_REQUEST + findCity);
            InputStream inputStream = url.openStream();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                String input = "";
                String line;
                while ((line = reader.readLine()) != null) {
                    input += line.trim();
                }
                JSONObject mainObject = new JSONObject(input);
                JSONArray jsonResults = mainObject.getJSONArray("results");
                if (jsonResults.length() == 0) {
                    return null;
                }
                JSONObject jsonLocation = jsonResults.getJSONObject(0).getJSONObject("geometry")
                        .getJSONObject("location");
                resLocation.latitude = ((Double) jsonLocation.get("lat")).floatValue();
                resLocation.longitude = ((Double) jsonLocation.get("lng")).floatValue();
            }
        } catch (MalformedURLException ex) {
            LOG.error("Bad ULR request to googleMaps");
            LOG.error(ex);
        } catch (IOException ex) {
            LOG.error("Can't open response from googleMaps");
            LOG.error(ex);
        }
        return resLocation;
    }

    public static Location getLocationIP() {
        Location locationServices = null;
        URL url = GoogleMapsUtils.class.getClassLoader().getResource(LOCATION_DATABASE);
        if (url == null) {
            LOG.error("location databaseIP is not found - " + LOCATION_DATABASE);
        } else {
            try {
                LookupService lookup = new LookupService(url.getPath(), LookupService.GEOIP_MEMORY_CACHE);
                locationServices = lookup.getLocation(IpAdressUtils.getCurrentIP());
            } catch (IOException e) {
                LOG.error("Cah't identify location by IP");
                LOG.error(e);
            }
        }
        return locationServices;
    }

}
