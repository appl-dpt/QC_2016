package com.softserve.hotels.controller;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.maxmind.geoip.Location;
import com.softserve.hotels.dto.ApartmentConveniencesDto;
import com.softserve.hotels.dto.ApartmentPaymentDto;
import com.softserve.hotels.dto.Info;
import com.softserve.hotels.dto.PaginationInfoDto;
import com.softserve.hotels.model.Apartment;
import com.softserve.hotels.model.Feedback;
import com.softserve.hotels.model.LinkPhoto;
import com.softserve.hotels.model.User;
import com.softserve.hotels.service.ApartmentService;
import com.softserve.hotels.service.FeedbackService;
import com.softserve.hotels.service.FeedbackTokenService;
import com.softserve.hotels.service.PgDumper;
import com.softserve.hotels.service.UserService;
import com.softserve.hotels.social.CustomUserDetails;
import com.softserve.hotels.utils.ConvenienceDTOBuilder;
import com.softserve.hotels.utils.GoogleMapsUtils;
import com.softserve.hotels.utils.PaymentsDTOBuilder;
import com.softserve.hotels.validators.ApartmentPaymentDtoValidator;
import com.softserve.hotels.validators.ApartmentValidator;

/**
 * @author Mykola Ilashchuk
 */
@Controller
@RequestMapping(value = "/renter")
public class ApartmentController {

    private static final Logger LOG = LogManager.getLogger(ApartmentController.class);

    @Autowired
    private ApartmentService apartmentService;

    @Autowired
    private UserService userService;

    @Autowired
    private ApartmentValidator apartmentValidator;

    @Autowired
    private ConvenienceDTOBuilder convenienceDTOBuilder;

    @Autowired
    private PaymentsDTOBuilder paymentsDTOBuilder;
    
    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private FeedbackTokenService feedbackTokenService;
    
    @Autowired
    private ApartmentPaymentDtoValidator apartmentPaymentDtoValidator;
    
    @Autowired
    @Qualifier("pgDumper")
    private PgDumper pgDumper;
    
    @InitBinder("apartment")
    public void binder(WebDataBinder binder) {
        binder.addValidators(apartmentValidator);
    }
    
    @InitBinder("apartmentPaymentDto")
    public void binderApartmentPaymentDto(WebDataBinder binder) {
        binder.addValidators(apartmentPaymentDtoValidator);
    }
    
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
        model.addAttribute("apconveniences", convenienceDTOBuilder.build(apartment));
        model.addAttribute("apartmentPayments", paymentsDTOBuilder.build(apartment));
    }
    
    @RequestMapping(value = "/allApartments", method = RequestMethod.GET)
    public String listAllApartments(Model model) {
        CustomUserDetails cud = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User user = userService.findUserByEmail(cud.getEmail());
        model.addAttribute("listApartments", user.getRentAppartments());
        return "allApartments";
    }

    @RequestMapping(value = "/enabledApartments", method = RequestMethod.GET)
    public String enabledApartments(Model model, @RequestParam(value = "size", defaultValue = "3") Integer pageSize,
            @RequestParam(value = "page", defaultValue = "1") Integer currentPage) {
        PaginationInfoDto apartmentPageInfo = new PaginationInfoDto(pageSize, currentPage);
        CustomUserDetails cud = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User user = userService.findUserByEmail(cud.getEmail());
        model.addAttribute("listApartments",
                this.apartmentService.findAllEnabledForRenterPageable(user, apartmentPageInfo));
        model.addAttribute("lastPageIndex", apartmentPageInfo.getPageCount());
        model.addAttribute("currentPage", apartmentPageInfo.getCurrentPage());
        return "enabledApartments";
    }

    @RequestMapping(value = "/disabledApartments", method = RequestMethod.GET)
    public String disableApartments(Model model) {
        CustomUserDetails cud = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User user = userService.findUserByEmail(cud.getEmail());
        model.addAttribute("listApartments", this.apartmentService.findAllDisabledForRenter(user));
        return "disabledApartments";
    }

    @RequestMapping(value = "/unpublishedApartments", method = RequestMethod.GET)
    public String unpublishedApartments(Model model) {
        CustomUserDetails cud = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User user = userService.findUserByEmail(cud.getEmail());
        model.addAttribute("listApartments", this.apartmentService.findAllUpublishedForRenter(user));
        return "unpublishedApartments";
    }

    @RequestMapping(value = "/apartmentDetail/{id}", method = RequestMethod.GET)
    public String apartmentDetail(@PathVariable("id") Integer id, Model model) throws NoHandlerFoundException {
        CustomUserDetails principal = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Apartment apartment = this.apartmentService.findById(id);
        if (principal.getId() == apartment.getRenter().getId()) {
            setApartmentAttriburtes(apartment, model);
            model.addAttribute("apartment", apartment);
            model.addAttribute("photo", new LinkPhoto());
            return "apartmentDetail";
        }
        LOG.warn(principal.getId() + "tried to access another renter's  apartmentsDetails");
        throw new NoHandlerFoundException("GET", "/renter/apartmentDetail/" + id, null);
    }

    @RequestMapping(value = "/updateApartment", method = RequestMethod.POST)
    public String editApartment(@ModelAttribute("apartment") @Valid Apartment apartment, BindingResult result,
            final RedirectAttributes redirectAttributes, Model model) {
        if (result.hasErrors()) {
            Apartment newApartment = this.apartmentService.findById(apartment.getId());
            setApartmentAttriburtes(newApartment, model);
            model.addAttribute("apartment", newApartment);
            model.addAttribute("photo", new LinkPhoto());
            return "apartmentDetail";
        }
        this.apartmentService.updateApartmentInfo(apartment);
        redirectAttributes.addFlashAttribute("info", new Info("Info", "updateApartment", ""));
        return "redirect:/renter/apartmentDetail/" + apartment.getId();
    }

    @RequestMapping("/removeApartment/{id}")
    public String updateApartment(@PathVariable("id") int id) {
        this.apartmentService.deleteById(id);
        return "redirect:/renter/allApartments";
    }

    @RequestMapping(value = "/formApartment", method = RequestMethod.GET)
    public String formApartment(Model model) {
        Apartment apartment = new Apartment();
        model.addAttribute("apartment", apartment);
        CustomUserDetails cud = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User user = userService.findUserByEmail(cud.getEmail());
        model.addAttribute("userID", user.getId());
        return "addApartment";
    }

    @RequestMapping(value = "/addApartment", method = RequestMethod.POST)
    public String addApartment(@Valid @ModelAttribute("apartment") Apartment apartment, 
    		BindingResult result) {
    	if (result.hasErrors()) {
            return "addApartment";
        }
        apartmentService.create(apartment);
        return "redirect:/renter/apartmentDetail/" + apartment.getId();
    }

    @RequestMapping(value = "/setCoordinates", method = RequestMethod.POST)
    public String setCoordinates(@ModelAttribute("apartment") Apartment apartment,
            final RedirectAttributes redirectAttributes, Model model) {
        CustomUserDetails cud = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User user = userService.findUserByEmail(cud.getEmail());
        apartment.setRenter(user);
        if (this.apartmentService.updateApartmentCoordinates(apartment)) {
            redirectAttributes.addFlashAttribute("info", new Info("Map", "editCoord", ""));
            return "redirect:/renter/apartmentDetail/" + apartment.getId();
        } else {
            Apartment newApartment = this.apartmentService.findById(apartment.getId());
            setApartmentAttriburtes(newApartment, model);
            model.addAttribute("apartment", newApartment);
            model.addAttribute("photo", new LinkPhoto());
            model.addAttribute("info", new Info("Map", "badCoord", ""));
            return "apartmentDetail";
        }
    }

    @RequestMapping(value = "/preview/{id}", method = RequestMethod.GET)
    public String apartmentPreview(@PathVariable("id") int id, Model model) {
        Apartment apartment = apartmentService.findById(id);
        model.addAttribute("apartment", apartment);
        model.addAttribute("feedbacks", apartment.getFeedback());
        model.addAttribute("complainedFeedbacks", feedbackTokenService.findAllForApartment(apartment));
        return "previewApartment";
    }

    @RequestMapping(value = "/disableApartment/{id}", method = RequestMethod.GET)
    public String disableApartment(@PathVariable("id") int id) {
        this.apartmentService.unPublishApartment(apartmentService.findById(id));
        return "redirect:/renter/enabledApartments";
    }

    // type: type of return: 1-list apartments, 2 - apartmentDetail
    @RequestMapping(value = "/publishApartment/{id}/{type}", method = RequestMethod.GET)
    public String publishApartment(@PathVariable("id") int id, @PathVariable("type") int type,
            final RedirectAttributes redirectAttributes) {
        Apartment apartment = this.apartmentService.findById(id);
        if (!apartment.getLinks().isEmpty() && apartment.getLatitude() != 0 && apartment.getLongitude() != 0) {
            apartmentService.publishApartment(apartment);
            redirectAttributes.addFlashAttribute("info", new Info("", "publish", ""));
            if (type == 1)
                return "redirect:/renter/enabledApartments";
            if (type == 2)
                return "redirect:/renter/apartmentDetail/" + apartment.getId();
        }
        if (apartment.getLinks().isEmpty() && apartment.getLatitude() == 0 && apartment.getLongitude() == 0)
            redirectAttributes.addFlashAttribute("info", new Info("", "noPhotoLocation", "unpublish"));
        else {
            if (apartment.getLinks().isEmpty())
                redirectAttributes.addFlashAttribute("info", new Info("", "noPhoto", "unpublish"));
            if (apartment.getLatitude() == 0 && apartment.getLongitude() == 0)
                redirectAttributes.addFlashAttribute("info", new Info("", "noLocation", "unpublish"));
        }
        if (type == 1)
            return "redirect:/renter/unpublishedApartments";
        else
            return "redirect:/renter/apartmentDetail/" + apartment.getId();
    }

    @RequestMapping(value = "/updateConveniences", method = RequestMethod.POST)
    public String updateConveniences(@ModelAttribute("apconveniences") ApartmentConveniencesDto acdto,
            final RedirectAttributes redirectAttributes) {
        this.apartmentService.updateApartmentConveniences(acdto);
        redirectAttributes.addFlashAttribute("info", new Info("Conveniences", "editConveniences", ""));
        return "redirect:/renter/apartmentDetail/" + acdto.getId();
    }

    @RequestMapping(value = "/updatePaymentSettings", method = RequestMethod.POST)
    public String updatePaymentSettings(
            @ModelAttribute("apartmentPaymentDto") @Valid ApartmentPaymentDto apartmentPaymentDto,
            BindingResult result,
            final RedirectAttributes redirectAttributes,
            Model model) {
        if (result.hasErrors()) {
        	Apartment apartment = apartmentService.findById(apartmentPaymentDto.getId());
        	setApartmentAttriburtes(apartment, model);
            model.addAttribute("apartment", apartment);
            model.addAttribute("photo", new LinkPhoto());
            model.addAttribute("info", 
            		new Info("PaymentSettings", "notEditPaymentSettings", ""));
            return "apartmentDetail";
        }
    	this.apartmentService.updateApartmentPaymentSettings(apartmentPaymentDto);
        redirectAttributes.addFlashAttribute("info", 
        		new Info("PaymentSettings", "editPaymentSettings", ""));
        return "redirect:/renter/apartmentDetail/" + apartmentPaymentDto.getId(); 
    }

    @RequestMapping(value = "/userDetails/{id}/{idA}", method = RequestMethod.GET)
    public String userDetail(@PathVariable("id") int id, @PathVariable("idA") int idA, Model model) {
        model.addAttribute("user", userService.findById(id));
        model.addAttribute("apartment", apartmentService.findById(idA));
        return "userDetailRenter";
    }

    @RequestMapping(value = "/complainFeedback/{id}", method = RequestMethod.GET)
    public String complainFeedback(@PathVariable("id") int id) {
        Feedback feedback = feedbackService.findById(id);
        feedbackService.complain(feedback);
        return "redirect:/renter/preview/" + feedback.getApartment().getId();
    }
    
    @RequestMapping(value = "/apartmentHistory/{id}", method = RequestMethod.GET)
    public String apartmentHistory(@PathVariable("id") int id, Model model) {
    	model.addAttribute("apartment", apartmentService.findById(id));
        return "renterApartmentHistory"; 
    }

}