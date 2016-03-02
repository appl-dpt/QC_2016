package com.softserve.hotels.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import com.softserve.hotels.model.Apartment;
import com.softserve.hotels.model.LinkPhoto;

public final class FileUtils {

    public static final String ROOT_DIR = "ApartmentsPhotos";
    public static final String ROOT_PATH = System.getProperty("catalina.home");
    public static final String PREFIX_FOLDER_APARTMENT = "Apartment";
    public static final String PREFIX_FOLDER_RENTER = "Renter";

    public static final Logger LOG = LogManager.getLogger(FileUtils.class);

    private FileUtils() {
        /* util class */ }

    public static String getFileExtention(String fileName) {
        if (fileName.lastIndexOf(".") > 0)
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        else
            return "";
    }

    public static void saveFile(LinkPhoto photo, MultipartFile file) throws IOException {
        byte[] bytes = file.getBytes();
        File serverFile = new File(ROOT_PATH + photo.getUrl());
        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
        stream.write(bytes);
        stream.close();
        LOG.debug("uploaded: " + serverFile.getAbsolutePath());
    }

    public static void deleteFile(String url) {
        File file = new File(ROOT_PATH + url);
        if (file.exists())
            file.delete();
    }

    public static void makeDirApartment(Apartment apartment) {
        File dir = new File(ROOT_PATH + File.separator + ROOT_DIR + File.separator + PREFIX_FOLDER_RENTER
                + apartment.getRenter().getId() + File.separator + PREFIX_FOLDER_APARTMENT + apartment.getId());
        if (!dir.exists())
            dir.mkdirs();
    }

    public static void makeDirRenter(int idRenter) {
        File dir = new File(ROOT_PATH + File.separator + ROOT_DIR + File.separator + PREFIX_FOLDER_RENTER + idRenter);
        if (!dir.exists())
            dir.mkdirs();
    }

    public static String makeUrl(LinkPhoto photo, MultipartFile file) {
        return File.separator + ROOT_DIR + File.separator + PREFIX_FOLDER_RENTER
                + photo.getApartment().getRenter().getId() + File.separator + PREFIX_FOLDER_APARTMENT
                + photo.getApartmentID() + File.separator + photo.getName() + "."
                + getFileExtention(file.getOriginalFilename());
    }

    public static void removeDirApartment(Apartment apartment) {
        File dir = new File(ROOT_PATH + File.separator + ROOT_DIR + File.separator + PREFIX_FOLDER_RENTER
                + apartment.getRenter().getId() + File.separator + PREFIX_FOLDER_APARTMENT + apartment.getId());
        if (dir.exists()) {
            dir.delete();
        }
    }

}
