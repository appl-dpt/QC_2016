/**
 * @author Rogulya Volodymyr

 */
package com.softserve.hotels.controller;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.softserve.hotels.dto.ApartmentOrders;
import com.softserve.hotels.dto.ReservedState;
import com.softserve.hotels.model.Apartment;
import com.softserve.hotels.model.Feedback;
import com.softserve.hotels.model.Reserved;
import com.softserve.hotels.model.Role;
import com.softserve.hotels.model.User;
import com.softserve.hotels.model.VerificationToken;
import com.softserve.hotels.service.ApartmentService;
import com.softserve.hotels.service.MailService;
import com.softserve.hotels.service.ReservedService;
import com.softserve.hotels.service.UserService;
import com.softserve.hotels.service.VerificationTokenService;
import com.softserve.hotels.social.CustomUserDetails;

@Controller
public class UserController {

    public static final Logger LOG = LogManager.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    
    @Autowired
    private MailService mailService;

    @Autowired
    private ReservedService reservedService;

    @Autowired
    private VerificationTokenService tokenService;

    @Autowired
    private ApartmentService apartmentService;

    private static final int PAGINATION_SIZE = 2;
    
    @RequestMapping("/")
    public String main(Model model) throws Exception {
        return "redirect:/1";
    }
    
    @RequestMapping(value = "/{pageNumber}", method = RequestMethod.GET)
    public String apartmentsOrders(@PathVariable("pageNumber") int pageNumber, ModelMap model) {
        ApartmentOrders apartmentOrders = new ApartmentOrders();
        return showApartment(model, apartmentService.filterApartment(apartmentOrders, pageNumber, PAGINATION_SIZE),
                apartmentOrders, "index");
    }
    
    @RequestMapping(value = "/{pageNumber}", method = RequestMethod.POST)
    public String apartmentsOrdersFilter(@ModelAttribute("filterApartment") ApartmentOrders apartmentOrders,
            @PathVariable("pageNumber") Integer pageNumber, ModelMap model) {
        return showApartment(model, apartmentService.filterApartment(apartmentOrders, pageNumber, PAGINATION_SIZE),
                apartmentOrders, "index");
    }
    
    private String showApartment(ModelMap model, List<Apartment> apartmentList, ApartmentOrders filterApartment,
            String jsp) {
        model.addAttribute("apartmentList", apartmentList);
        model.addAttribute("filterApartment", filterApartment);
        return jsp;
    }
    
    @RequestMapping(value = { "/registration" }, method = RequestMethod.GET)
    public String newUser(ModelMap model) {
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("edit", false);
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String saveUser(@Valid User user, BindingResult result, ModelMap model,
            @RequestParam(value = "myCheckbox", required = false) Boolean myCheckbox,
            @RequestParam(value = "email") String email) {

        if (result.hasErrors()) {
            return "registration";
        }

        if (myCheckbox != null) {
            user.setRole(Role.RENTER);
        }
             
        if (email != null) {
            user.setEmail(email.toLowerCase());
        }

        String token = UUID.randomUUID().toString();
        String confirmationMessage = mailService.buildRegisterMessage(user, token);

        user.setEnabled(false);
        userService.create(user);
        tokenService.createToken(token, user);
        model.addAttribute("success", "registration");
        mailService.sendMessage(user, "Registration confirmation", confirmationMessage);

        return "success";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }
    
    @RequestMapping(value = "/invalidCredentials", method = RequestMethod.GET) 
    public String invalidCredentials(ModelMap model) {
        model.addAttribute("error", "invalidCredentials");
        return "login"; 
    } 
    
    @RequestMapping(value = "/loginBlocked", method = RequestMethod.GET) 
    public String loginBlocked(ModelMap model) {
        model.addAttribute("error", "blocked");
        return "login"; 
    } 
    
    @RequestMapping(value = "/loginNotConfirmed", method = RequestMethod.GET) 
    public String loginConfirm(ModelMap model) {
        model.addAttribute("error", "notConfirmed"); 
        return "login"; 
    } 
    
    @RequestMapping(value = "/invalidPassword", method = RequestMethod.GET) 
    public String invalidPassword(ModelMap model) {
        model.addAttribute("error", "invalidPassword"); 
        return "login"; 
    } 
    
    @RequestMapping(value = "/book/{id}", method = RequestMethod.GET)
    public String userPermissions(@PathVariable("id") int id, ModelMap model) {

        Apartment apartment = apartmentService.findById(id);
        List<LocalDate> reservedDates = reservedService.getReservedDatesforApartment(apartment);

        Reserved reserved = (Reserved) model.get("registeredReserved");
        ReservedState modalBookState = (ReservedState) model.get("reservedState");
        model.addAttribute("apartment", apartment);
        model.addAttribute("feedbacks", apartment.getFeedback());
        model.addAttribute("reservedDates", reservedDates);
        if (modalBookState != null) {
            model.addAttribute("reservedState", modalBookState);
            model.addAttribute("bookingPeriod",
            reserved.getDateStartReservation() + "-" + reserved.getDateEndReservation());
        }

        if (!isAuthenticatedAnonymous()) {
            boolean canLeaveFeedback = !reservedService
                    .findCommentableForUserAndApartment(getUserFromContext(), apartment)
                    .isEmpty();
            model.addAttribute("canLeaveFeedback", canLeaveFeedback);
            model.addAttribute("newReserved", new Reserved());
            model.addAttribute("feedback", new Feedback());
        }
        return "bookingApartment";
    }
    
    @RequestMapping(value = "/userSettings", method = RequestMethod.GET)
    public String userSettings(ModelMap model) {
        CustomUserDetails auth = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User user = userService.findById(auth.getId());
        model.addAttribute("user", user);
        return "userSettings";
    }

    @RequestMapping(value = "/userSettings/updateUser", method = RequestMethod.POST)
    public String updateUser(@ModelAttribute("user") User user) {
        userService.update(user);
        return "redirect:/userSettings";
    }

    @RequestMapping(value = "/userSettings/resetPhoto", method = RequestMethod.POST)
    public String resetPhoto() {
        return "redirect:/userSettings";
    }

    @RequestMapping(value = "/accessDenied", method = RequestMethod.GET)
    public String accessDenied() {
        return "accessDenied";
    }

    @RequestMapping(value = "/confirm", method = RequestMethod.GET)
    public String confirmRegistration(@RequestParam("token") String token) {
        VerificationToken verificationToken = tokenService.findByToken(token);
        User user = verificationToken.getUser();
        user.setEnabled(true);
        userService.update(user);
        tokenService.delete(verificationToken);
        return "redirect:/login";
    }

    private User getUserFromContext() {
        CustomUserDetails auth = (CustomUserDetails) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        return userService.findById(auth.getId());
    }

    @SuppressWarnings("unchecked")
    private static boolean isAuthenticatedAnonymous() {
        Collection<GrantedAuthority> authes = (Collection<GrantedAuthority>) SecurityContextHolder.getContext()
                .getAuthentication().getAuthorities();
        for (GrantedAuthority ath : authes) {
            if (!("ROLE_ANONYMOUS".equals(ath.getAuthority()))) {
                LOG.debug(ath.getAuthority());
                return false;
            }
        }
        return true;
    }
    
}
