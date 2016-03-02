package com.softserve.hotels.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.softserve.hotels.model.LinkPhoto;

public final class ImageConverter {
    private static final Logger LOGGER = LoggerFactory.getLogger(ImageConverter.class);

    private ImageConverter() {
        /* util class */ }

    public static byte[] toByteArray(LinkPhoto photo) {

        try {
            String url = FileUtils.ROOT_PATH + photo.getUrl();
            BufferedImage image = ImageIO.read(new File(url));
            String extension = FileUtils.getFileExtention(photo.getUrl());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, extension, baos);
            baos.flush();
            return baos.toByteArray();
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        return null;
    }

}
