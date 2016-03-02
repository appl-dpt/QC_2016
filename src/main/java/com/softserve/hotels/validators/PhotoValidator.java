package com.softserve.hotels.validators;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.softserve.hotels.model.Configs;
import com.softserve.hotels.model.LinkPhoto;
import com.softserve.hotels.model.PhotoExtention;
import com.softserve.hotels.service.ConfigsService;
import com.softserve.hotels.service.PhotoExtentionService;
import com.softserve.hotels.utils.FileUtils;

@Component
public class PhotoValidator implements Validator {

    @Autowired
    private PhotoExtentionService photoExtentionService;
    
    @Autowired
    private ConfigsService configsService;

    private boolean includeExtention(String extention) {
        List<PhotoExtention> extentions = this.photoExtentionService.findAll();
        for (PhotoExtention photoExtention : extentions) {
            if (photoExtention.getExtention().equals(extention)) {
                return true;
            }
        }
        return false;
    }

    private static String getExtention(String url) {
        if (url.lastIndexOf(".") > 0)
            return url.substring(url.lastIndexOf(".") + 1);
        else
            return "";
    }

    @Override
    public boolean supports(Class<?> arg0) {
        return LinkPhoto.class.equals(arg0);
    }

    @Override
    public void validate(Object object, Errors errors) {
        LinkPhoto photo = (LinkPhoto) object;
        Configs configs = configsService.getConfigsByFeature("MaxNumberOfPhotos");
        if (photo.getApartment().getLinks().size() >= Integer.parseInt(configs.getParameter())) {
            errors.rejectValue("name", "file.count.toolarge");
        }
        if ("".equals(photo.getName()))
            errors.rejectValue("name", "file.name.cantempty");
        else if (new File(FileUtils.ROOT_PATH + photo.getUrl()).exists()) {
            errors.rejectValue("name", "file.already.exists");
        }
        String extention = getExtention(photo.getUrl());
        if (!includeExtention(extention.toLowerCase())) {
            errors.rejectValue("url", "file.illegal.extention");
        }
    }

}
