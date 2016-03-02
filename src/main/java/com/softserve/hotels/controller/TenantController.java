package com.softserve.hotels.controller;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.softserve.hotels.dto.ActiveOrders;
import com.softserve.hotels.dto.CanceledReservation;
import com.softserve.hotels.dto.ReservedState;
import com.softserve.hotels.model.ActionStatus;
import com.softserve.hotels.model.Apartment;
import com.softserve.hotels.model.ApartmentPayment;
import com.softserve.hotels.model.Feedback;
import com.softserve.hotels.model.PaymentToken;
import com.softserve.hotels.model.Reserved;
import com.softserve.hotels.model.User;
import com.softserve.hotels.model.paymentResult;
import com.softserve.hotels.service.ApartmentPaymentService;
import com.softserve.hotels.service.ApartmentService;
import com.softserve.hotels.service.FeedbackService;
import com.softserve.hotels.service.PaymentService;
import com.softserve.hotels.service.ReservedService;
import com.softserve.hotels.service.UserService;
import com.softserve.hotels.social.CustomUserDetails;
import com.softserve.hotels.utils.ZipUtils;

@Controller
@RequestMapping(value = "/tenant")
public class TenantController {

    @Autowired
    private ReservedService reservedService;

    @Autowired
    private UserService userService;

    @Autowired
    private ApartmentService apartmentService;

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private ApartmentPaymentService apartmentPaymentService;

    private static final int PAGINATION_SIZE = 3;

    @RequestMapping(value = "/bookApartment", method = RequestMethod.POST)
    public String apartmentBook(@ModelAttribute("newReserved") Reserved reserved, RedirectAttributes redirectAttrs) {
        Apartment currentApartment = apartmentService.findById(reserved.getApartment().getId());

        User user = getUserFromContext();
        if (!reservedService.isReservedForTime(reserved)) {
            reserved.setTenant(user);
            reserved.setApartment(currentApartment);
            reserved.setStatus(ActionStatus.WAITING_PAYMENT);
            reservedService.create(reserved);
            return "redirect:/tenant/paymentDetails";
        } else {
            redirectAttrs.addFlashAttribute("reservedState", ReservedState.ERROR);
        }

        redirectAttrs.addFlashAttribute("registeredReserved", reserved);
        return "redirect:/book/" + currentApartment.getId();

    }

    @RequestMapping(value = "/unbookApartment", method = RequestMethod.POST)
    public String apartmentUnBook(@ModelAttribute("canceledReservation") CanceledReservation canceledReservation) {
        Reserved reserved = this.reservedService.findById(canceledReservation.getIdReservation());
        reservedService.declineReservationByTenant(reserved, canceledReservation.getComment());
        return "redirect:/tenant/activeOrders/1";

    }

    @RequestMapping(value = "/activeOrders", method = RequestMethod.GET)
    public String activeRedirect() {
        return "redirect:/tenant/activeOrders/1";
    }

    @RequestMapping(value = "/historyOrders", method = RequestMethod.GET)
    public String historyRedirect() {
        return "redirect:/tenant/historyOrders/1";
    }
    
    @RequestMapping(value = "/ordersToPay", method = RequestMethod.GET)
    public String ordersToPayRedirect() {
        return "redirect:/tenant/ordersToPay/1";
    }

    @RequestMapping(value = "/activeOrders/{pageNumber}", method = RequestMethod.GET)
    public String userOrdersActive(@PathVariable("pageNumber") int pageNumber, ModelMap model) {
        ActiveOrders filterTenant = new ActiveOrders();
        filterTenant.setTenant(getUserFromContext());
        filterTenant.setActionStatus(ActionStatus.ALL_ACTIVE);
        model.addAttribute("canceledReservation", new CanceledReservation());
        model.addAttribute("oredrStatusList", ActionStatus.getAllActiveStatus());
        return showReserved(model, reservedService.findFutureForUserByRange(filterTenant, pageNumber, PAGINATION_SIZE),
                filterTenant, "userOrders");
    }

    @RequestMapping(value = "/activeOrders/{pageNumber}", method = RequestMethod.POST)
    public String userOrdersFilter(@ModelAttribute("filterTenant") ActiveOrders filterTenant,
            @PathVariable("pageNumber") Integer pageNumber, ModelMap model) {
        filterTenant.setTenant(getUserFromContext());
        model.addAttribute("canceledReservation", new CanceledReservation());
        model.addAttribute("oredrStatusList", ActionStatus.getAllActiveStatus());
        return showReserved(model, reservedService.filterReserved(filterTenant, pageNumber, PAGINATION_SIZE),
                filterTenant, "userOrders");
    }

    @RequestMapping(value = "/historyOrders/{pageNumber}", method = RequestMethod.GET)
    public String userOrdersHistory(@PathVariable("pageNumber") int pageNumber, ModelMap model) {
        ActiveOrders filterTenant = new ActiveOrders();
        filterTenant.setTenant(getUserFromContext());
        filterTenant.setActionStatus(ActionStatus.ALL_INACTIVE);
        model.addAttribute("oredrStatusList", ActionStatus.getAllInactiveStatus());
        return showReserved(model, reservedService.findPastForUserByRange(filterTenant, pageNumber, PAGINATION_SIZE),
                filterTenant, "historyOrders");
    }

    @RequestMapping(value = "/historyOrders/{pageNumber}", method = RequestMethod.POST)
    public String userHistoryFilter(@ModelAttribute("filterTenant") ActiveOrders filterTenant,
            @PathVariable("pageNumber") Integer pageNumber, ModelMap model) {
        filterTenant.setTenant(getUserFromContext());
        model.addAttribute("oredrStatusList", ActionStatus.getAllInactiveStatus());
        return showReserved(model, reservedService.filterReserved(filterTenant, pageNumber, PAGINATION_SIZE),
                filterTenant, "historyOrders");
    }

    private String showReserved(ModelMap model, List<Reserved> filterList, ActiveOrders filterTenant, String jsp) {
        model.addAttribute("reservedList", filterList);
        model.addAttribute("filterTenant", filterTenant);
        model.addAttribute("deleteReserved", new Reserved());

        return jsp;
    }

    @ResponseBody
    @RequestMapping(value = "/monthDates/{apartment_id}", method = RequestMethod.POST)
    public List<String> loadDatesForMonth(
            @RequestParam(value = "startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(value = "endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
            @PathVariable("apartment_id") Integer apartmentId) {
        List<String> dateList = reservedService.getReservedDatesforApartment(apartmentService.findById(apartmentId),
                startDate, endDate);
        return dateList;
    }

    @ResponseBody
    @RequestMapping(value = "/searchNext/{apartment_id}", method = RequestMethod.POST)
    public String loadNextDate(
            @RequestParam(value = "startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @PathVariable("apartment_id") Integer apartmentId) {
        Reserved reserved = reservedService.findNextActiveAfterDate(apartmentService.findById(apartmentId), startDate);
        return reserved == null ? null : reserved.getDateStartReservation().toString();
    }

    @RequestMapping(value = "/{id}/feedback", method = RequestMethod.POST)
    public String send(@ModelAttribute("feedback") Feedback feedback, @PathVariable("id") Integer apartmentId) {
        User feedbacker = getUserFromContext();
        Apartment apartment = apartmentService.findById(apartmentId);
        feedback.setFeedbacker(feedbacker);
        feedback.setApartment(apartment);
        feedbackService.create(feedback);
        List<Reserved> reservedToChangeStatus = reservedService.findCommentableForUserAndApartment(feedbacker,
                apartment);
        for (Reserved reserved : reservedToChangeStatus) {
            reserved.setStatus(ActionStatus.AFTER_FEEDBACK);
            reservedService.update(reserved);
        }
        return "redirect:/book/" + feedback.getApartment().getId();
    }

    @RequestMapping(value = "/ordersToPay/{pageNumber}", method = RequestMethod.GET)
    public String ordersToPay(@PathVariable("pageNumber") int pageNumber, ModelMap model) {
        ActiveOrders filterTenant = new ActiveOrders();
        model.addAttribute("ordersToPayList", reservedService.findAllUnpayedForUser(getUserFromContext()));
        model.addAttribute("paymentReservation", new Reserved());
        return showPayment(model, reservedService.filterReserved(filterTenant, pageNumber, PAGINATION_SIZE),
                filterTenant, "ordersToPay");
    }
    
    @RequestMapping(value = "/ordersToPay/{pageNumber}", method = RequestMethod.POST)
    public String ordersToPayFilter(@ModelAttribute("filterTenant") ActiveOrders filterTenant,
            @PathVariable("pageNumber") Integer pageNumber, ModelMap model) {
        filterTenant.setTenant(getUserFromContext());
        model.addAttribute("ordersToPayList", reservedService.findAllUnpayedForUser(getUserFromContext()));
        model.addAttribute("paymentReservation", new Reserved());
        return showPayment(model, reservedService.filterReserved(filterTenant, pageNumber, PAGINATION_SIZE),
                filterTenant, "historyOrders");
    }
    
    private String showPayment(ModelMap model, List<Reserved> filterList, ActiveOrders filterTenant, String jsp) {
        model.addAttribute("reservedList", filterList);
        model.addAttribute("filterTenant", filterTenant);
        model.addAttribute("deleteReserved", new Reserved());
        return jsp;
    }

    @RequestMapping(value = "/paymentDetails", method = RequestMethod.GET)
    public String paymentDetails(ModelMap model) {
        Reserved reserved = reservedService.findLastUnpayedForUser(getUserFromContext());
         return paymentSets(model, reserved);
    }
    
    @RequestMapping(value = "/paymentDetails", method = RequestMethod.POST)
    public String paymentDetailsRetriew(@ModelAttribute("paymentReserved") Reserved reserved, ModelMap model) {
       reserved = reservedService.findById(reserved.getId());
        return paymentSets(model, reserved);
    }
    
    private String paymentSets(ModelMap model, Reserved reserved) {
        
        if (reserved == null) {
            model.addAttribute("resultPayment", paymentResult.PAYMENT_OLD);
            return "paymentResult";
        }        
        Apartment apartment = reserved.getApartment();
        ArrayList<ApartmentPayment> pymentList = (ArrayList<ApartmentPayment>) apartmentPaymentService
                .getAvailableForApartment(apartment);
        model.addAttribute("availablePayments", pymentList);
        model.addAttribute("paymentReserved", reserved);
        return "paymentDetails"; 
    }

    @RequestMapping(value = "/paymentMaking/{id}", method = RequestMethod.POST)
    public String paymentMaking(@ModelAttribute("paymentReserved") Reserved reserved,
            @PathVariable("id") Integer paymentId, ModelMap model) {
        String redirectLink = "";
        reserved = reservedService.findById(reserved.getId());
        
        if (reserved == null) {
            model.addAttribute("resultPayment", paymentResult.PAYMENT_OLD);
            return "paymentResult";
        }
        
        switch (paymentId) {
        case 1:
            redirectLink = handpayPayment(model, reserved);
            break;
        case 2:
            redirectLink = paypalPayment(reserved);
            break;
        case 3:
            redirectLink = payOnArrival(model, reserved);
            break;
        }
        return redirectLink;
    }

    private String paypalPayment(Reserved reserved) {

        PaymentToken paymentToken = new PaymentToken();
        paymentToken.setReserved(reserved);
        paymentService.create(paymentToken);
        return "redirect:" + paymentToken.getApprovementUrl();
    }

    private String handpayPayment(ModelMap model, Reserved reserved) {
        reserved.setStatus(ActionStatus.WAITING_CONFIRMATION);
        reservedService.update(reserved);
        model.addAttribute("resultPayment", paymentResult.PAYMENT_SUCCESS);
        return "paymentResult";
    }

    private String payOnArrival(ModelMap model, Reserved reserved) {
        reserved.setStatus(ActionStatus.WAITING_CONFIRMATION);
        reservedService.update(reserved);
        model.addAttribute("resultPayment", paymentResult.PAYMENT_SUCCESS);
        return "paymentResult";
    }
    
    @RequestMapping(value = "/paymentApproved", method = RequestMethod.GET)
    public String paymentApproved(@ModelAttribute("token") String token, @ModelAttribute("PayerID") String payerId,
            ModelMap model) {
        PaymentToken paymentToken = paymentService.findByToken(token);
        paymentToken.setPayerId(payerId);
        model.addAttribute("payment", paymentToken);
        paymentService.update(paymentToken);
        return "paymentApproved";
    }

    @RequestMapping(value = "/paymentExecuted", method = RequestMethod.GET)
    public String paymentExecuted(@ModelAttribute("token") String token, ModelMap model) {
        PaymentToken paymentToken = paymentService.findByToken(token);
        paymentToken.getReserved().setStatus(ActionStatus.APROVED);
        paymentService.deleteById(paymentToken.getId());
        model.addAttribute("resultPayment", paymentResult.PAYMENT_SUCCESS);
        return "paymentResult";
    }

    @RequestMapping(value = "/reservationDetail/{id}", method = RequestMethod.GET)
    public String reservationDetail(@PathVariable("id") int id, ModelMap model) {
        Reserved reservation = reservedService.findById(id);
        model.addAttribute("reservation", reservation);
        return "tenantReservationDetail";
    }

    @RequestMapping(value = "/userDetails/{id}", method = RequestMethod.GET)
    public String userDetails(@PathVariable("id") int id, ModelMap model) {
        model.addAttribute("user", userService.findById(id));
        return "userDetailTenant";
    }

    @RequestMapping(value = "/zipTestPage", method = RequestMethod.GET)
    public String zipTestPage() {
        return "compressTest";
    }

    @RequestMapping(value = "/compressTest", method = RequestMethod.GET)
    public String compress() {
        ZipUtils.compress("D:\\scr", "D:\\scr.zip");
        return "compressTest";
    }

    @RequestMapping(value = "/uncompressTest", method = RequestMethod.GET)
    public String unCompress() {
        ZipUtils.decompress("D:\\scr.zip", "D:\\scrUnzip");
        return "compressTest";
    }

    private User getUserFromContext() {
        CustomUserDetails auth = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        return userService.findById(auth.getId());
    }

}
