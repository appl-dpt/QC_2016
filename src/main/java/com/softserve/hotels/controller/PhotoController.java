package com.softserve.hotels.controller;

import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.maxmind.geoip.Location;
import com.softserve.hotels.dto.Info;
import com.softserve.hotels.model.Apartment;
import com.softserve.hotels.model.LinkPhoto;
import com.softserve.hotels.model.PhotoExtention;
import com.softserve.hotels.service.ApartmentService;
import com.softserve.hotels.service.ConfigsService;
import com.softserve.hotels.service.LinkPhotoService;
import com.softserve.hotels.service.PhotoExtentionService;
import com.softserve.hotels.utils.ConvenienceDTOBuilder;
import com.softserve.hotels.utils.FileUtils;
import com.softserve.hotels.utils.GoogleMapsUtils;
import com.softserve.hotels.utils.PaymentsDTOBuilder;
import com.softserve.hotels.validators.PhotoValidator;

@Controller
public class PhotoController {

    public static final Logger LOG = LogManager.getLogger(PhotoController.class);

    @Autowired
    private LinkPhotoService photoService;

    @Autowired
    private ApartmentService apartmentService;

    @Autowired
    private PhotoValidator photoValidator;
    
    @Autowired
    private PhotoExtentionService photoExtentionService;
    
    @Autowired
    private ConvenienceDTOBuilder convenienceDTOBuilder;
    
    @Autowired
    private PaymentsDTOBuilder paymentsDTOBuilder;
    
    @Autowired
    private ConfigsService configsService;

    private void setApartmentAttriburtes(Apartment apartment, Model model) {
        if (Double.doubleToRawLongBits(apartment.getLatitude()) == 0 
                && Double.doubleToRawLongBits(apartment.getLongitude()) == 0) {
            Location hostLocation = GoogleMapsUtils.getLocationCity(apartment.getCity());
            apartment.setLatitude(hostLocation.latitude);
            apartment.setLongitude(hostLocation.longitude);
            model.addAttribute("position", 0);
        } else {
            model.addAttribute("position", 1);
        }
        model.addAttribute("apartment", apartment);
        model.addAttribute("apconveniences", convenienceDTOBuilder.build(apartment));
        model.addAttribute("apartmentPayments", paymentsDTOBuilder.build(apartment));
    }
    
    @ResponseBody
    @RequestMapping(value = "/Apartments/photo/{id}", produces = MediaType.ALL_VALUE, method = RequestMethod.GET)
    public byte[] getPhoto(@PathVariable("id") Integer id) {
        return photoService.getPhotoDataById(id);
    }

    @RequestMapping(value = "/renter/uploadPhoto", method = RequestMethod.POST)
    public String uploadPhoto(@RequestParam("file") MultipartFile file, @ModelAttribute("photo") LinkPhoto photo,
            @RequestParam("idApartment") Integer idApartment, BindingResult result, Model model) {
        System.out.println(file.getSize());
        System.out.println(configsService.getConfigsByFeature("MaxUploadSizeOfPhoto").getParameter());
        if (file.getSize() > Integer.parseInt(
                configsService.getConfigsByFeature("MaxUploadSizeOfPhoto").getParameter())) {
            result.rejectValue("url", "file.too.large");
            LOG.debug("image too large");
        }
        Apartment apartment = this.apartmentService.findById(idApartment);
        photo.setApartment(apartment);
        photo.setUrl(FileUtils.makeUrl(photo, file));
        photoValidator.validate(photo, result);
        if (result.hasErrors()) {
            setApartmentAttriburtes(apartment, model);
            model.addAttribute("info", new Info("Photos", "notUpload", ""));
            return "apartmentDetail";
        }
        try {
            FileUtils.saveFile(photo, file);
        } catch (IOException e) {
            LOG.debug("You failed to upload " + photo.getName() + " => " + e.getMessage());
        }
        this.photoService.create(photo);
        apartment = this.apartmentService.findById(idApartment);
        setApartmentAttriburtes(apartment, model);
        model.addAttribute("photo", new LinkPhoto());
        model.addAttribute("info", new Info("Photos", "upload", ""));
        return "apartmentDetail";
    }

    @RequestMapping(value = "/renter/removePhoto", method = RequestMethod.GET)
    public String removePhoto(@RequestParam("idPhoto") int id, final RedirectAttributes redirectAttributes) {
        LinkPhoto photo = this.photoService.findById(id);
        if (photo.getApartment().getLinks().size() > 1) {
            this.photoService.deleteById(id);
            redirectAttributes.addFlashAttribute("info", new Info("Photos", "deletePhoto", ""));
        } else {
            redirectAttributes.addFlashAttribute("info", new Info("Photos", "notDeletePhoto", ""));
        }
        return "redirect:/renter/apartmentDetail/" + photo.getApartment().getId();
    }

    @RequestMapping(value = "/renter/upPhoto/{id}")
    public String upPhoto(@PathVariable("id") int id, final RedirectAttributes redirectAttributes) {
        LinkPhoto photo = this.photoService.findById(id);
        this.photoService.priorityUp(photo);
        redirectAttributes.addFlashAttribute("info", new Info("Photos", "", ""));
        return "redirect:/renter/apartmentDetail/" + photo.getApartmentID();
    }

    @RequestMapping(value = "/renter/downPhoto/{id}")
    public String downPhoto(@PathVariable("id") int id, final RedirectAttributes redirectAttributes) {
        LinkPhoto photo = this.photoService.findById(id);
        this.photoService.priorityDown(photo);
        redirectAttributes.addFlashAttribute("info", new Info("Photos", "", ""));
        return "redirect:/renter/apartmentDetail/" + photo.getApartmentID();
    }

    @RequestMapping(value = "/UploadPhotoError/{idApartment}")
    public String uploadPhotoErrorBySize(@PathVariable("idApartment") int idApartment, Model model) {
        Apartment apartment = this.apartmentService.findById(idApartment);
        setApartmentAttriburtes(apartment, model);
        model.addAttribute("photo", new LinkPhoto());
        model.addAttribute("info", new Info("Photos", "notUpload", ""));
        return "apartmentDetail";
    }

    @RequestMapping(value = "/renter/extentions", method = RequestMethod.GET)
    @ResponseBody
    public Properties getExtentions() {
        Properties result = new Properties();
        for (PhotoExtention phExtention : this.photoExtentionService.findAll()) {
            result.setProperty(phExtention.getId().toString(), phExtention.getExtention());
        }
        return result;
    }

}
