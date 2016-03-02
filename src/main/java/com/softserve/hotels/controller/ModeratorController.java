package com.softserve.hotels.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.softserve.hotels.dto.AjaxModeratorMap;
import com.softserve.hotels.dto.Info;
import com.softserve.hotels.model.ActionApartment;
import com.softserve.hotels.model.Apartment;
import com.softserve.hotels.model.ApartmentStatus;
import com.softserve.hotels.model.Feedback;
import com.softserve.hotels.model.User;
import com.softserve.hotels.service.ActionApartmentService;
import com.softserve.hotels.service.ApartmentService;
import com.softserve.hotels.service.FeedbackService;
import com.softserve.hotels.service.FeedbackTokenService;
import com.softserve.hotels.service.UserService;
import com.softserve.hotels.social.CustomUserDetails;
import com.softserve.hotels.utils.AjaxModeratorMapDTOBuilder;

@Controller
@RequestMapping(value = "/moderator")
public class ModeratorController {

    public static final Logger LOG = LogManager.getLogger(ApartmentController.class);

    @Autowired
    private ApartmentService apartmentService;

    @Autowired
    private UserService userService;

    @Autowired
    private AjaxModeratorMapDTOBuilder ajaxBuilder;

    @Autowired
    private ActionApartmentService actionApartmentService;

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private FeedbackTokenService feedbackTokenService;

    @RequestMapping(value = "/enabledApartments", method = RequestMethod.GET)
    public String enabledModeratorApartments(Model model) {
        CustomUserDetails cud = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User user = userService.findUserByEmail(cud.getEmail());
        model.addAttribute("listApartments", this.apartmentService.findAllEnabledForModerator(user));
        model.addAttribute("actionApartment", new ActionApartment());
        return "enabledModeratorApartments";
    }

    @RequestMapping(value = "/disabledApartments", method = RequestMethod.GET)
    public String disabledModeratorApartments(Model model) {
        CustomUserDetails cud = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User user = userService.findUserByEmail(cud.getEmail());
        model.addAttribute("listApartments", this.apartmentService.findAllDisabledForModerator(user));
        model.addAttribute("actionApartment", new ActionApartment());
        return "disabledModeratorApartments";
    }

    @RequestMapping(value = "/approveApartment/{id}", method = RequestMethod.GET)
    public String approveModeratorApartments(@PathVariable("id") int id, final RedirectAttributes redirectAttributes) {
        Apartment apartment = apartmentService.findById(id);
        apartmentService.approveApartment(apartment);
        redirectAttributes.addFlashAttribute("info", new Info("", "approveApartment", ""));
        return "redirect:/moderator/enabledApartments";
    }

    @RequestMapping(value = "/assignApartment/{id}", method = RequestMethod.GET)
    public String assignModeratorApartments(@PathVariable("id") int id, final RedirectAttributes redirectAttributes) {
        Apartment apartment = apartmentService.findById(id);
        CustomUserDetails cud = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User moderator = userService.findUserByEmail(cud.getEmail());
        redirectAttributes.addFlashAttribute("info", new Info("", "assignApartment", ""));
        this.apartmentService.assignApartment(apartment, moderator);

        return "redirect:/moderator/freeApartments";
    }

    @RequestMapping(value = "/disableApartment", method = RequestMethod.POST)
    public String disableApartment(@ModelAttribute("actionApartment") ActionApartment actionApartment,
            final RedirectAttributes redirectAttributes) {
        CustomUserDetails cud = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User moderator = userService.findUserByEmail(cud.getEmail());
        actionApartment.setModerator(moderator);
        actionApartment.setStatus(ApartmentStatus.DISABLED_BY_MODERATOR);
        actionApartmentService.create(actionApartment);
        Apartment apartment = apartmentService.findById(actionApartment.getApartment().getId());
        apartment.setStatus(ApartmentStatus.DISABLED_BY_MODERATOR);
        apartmentService.update(apartment);
        redirectAttributes.addFlashAttribute("info", new Info("", "disableApartment", ""));
        return "redirect:/moderator/enabledApartments";
    }

    @RequestMapping(value = "/enableApartment", method = RequestMethod.POST)
    public String enableApartment(@ModelAttribute("actionApartment") ActionApartment actionApartment,
            final RedirectAttributes redirectAttributes) {
        CustomUserDetails cud = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User moderator = userService.findUserByEmail(cud.getEmail());
        actionApartment.setModerator(moderator);
        actionApartment.setStatus(ApartmentStatus.ENABLED);
        actionApartmentService.create(actionApartment);
        Apartment apartment = apartmentService.findById(actionApartment.getApartment().getId());
        apartment.setStatus(ApartmentStatus.ENABLED);
        apartmentService.update(apartment);
        redirectAttributes.addFlashAttribute("info", new Info("", "enableApartment", ""));
        return "redirect:/moderator/disabledApartments";
    }

    @RequestMapping(value = "/freeApartments", method = RequestMethod.GET)
    public String freeModeratorApartments(Model model) {
        model.addAttribute("listApartments", this.apartmentService.findAllFree());
        return "freeApartments";
    }

    @RequestMapping(value = "/userDetails/{id}", method = RequestMethod.GET)
    public String userDetail(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userService.findById(id));
        return "userDetailModerator";
    }

    @RequestMapping(value = "/moderatorMap", method = RequestMethod.GET)
    public String moderatorMap() {
        return "moderatorMap";
    }

    @RequestMapping(value = "/allApartments", method = RequestMethod.GET)
    @ResponseBody
    public AjaxModeratorMap getSearchResultViaAjax() {
        CustomUserDetails cud = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User moderator = userService.findUserByEmail(cud.getEmail());
        return ajaxBuilder.build(moderator);
    }

    @RequestMapping(value = "/preview/{id}", method = RequestMethod.GET)
    public String previewApartment(@PathVariable("id") int id, Model model) {
        Apartment apartment = apartmentService.findById(id);
        model.addAttribute("apartment", apartment);
        model.addAttribute("feedbacks", apartment.getFeedback());
        CustomUserDetails cud = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User moderator = userService.findUserByEmail(cud.getEmail());
        model.addAttribute("moderator", moderator);
        model.addAttribute("complainedFeedbacks", feedbackTokenService.findAllForApartment(apartment));
        return "moderatorPreviewApartment";
    }

    @RequestMapping(value = "/deleteFeedback/{id}", method = RequestMethod.GET)
    public String deleteFeedback(@PathVariable("id") int id) {
        Feedback feedback = feedbackService.findById(id);
        feedbackService.deleteFeedback(feedback);
        return "redirect:/moderator/preview/" + feedback.getApartment().getId();
    }

    @RequestMapping(value = "/apartmentHistory/{id}", method = RequestMethod.GET)
    public String apartmentHistory(@PathVariable("id") int id, Model model) {
        model.addAttribute("apartment", apartmentService.findById(id));
        return "moderatorApartmentHistory";
    }

}
