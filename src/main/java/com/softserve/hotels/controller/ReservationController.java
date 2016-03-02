package com.softserve.hotels.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.softserve.hotels.dto.CanceledReservation;
import com.softserve.hotels.dto.Info;
import com.softserve.hotels.dto.PaginationInfoDto;
import com.softserve.hotels.model.Apartment;
import com.softserve.hotels.model.Reserved;
import com.softserve.hotels.service.ApartmentService;
import com.softserve.hotels.service.ReservedService;

@Controller
@RequestMapping(value = "/renter")
public class ReservationController {

    @Autowired
    private ReservedService reservedService;

    @Autowired
    private ApartmentService apartmentService;

    @RequestMapping(value = "/removeReservationByRenter", method = RequestMethod.POST)
    public String removeReservationByRenter(
            @ModelAttribute("canceledReservation") CanceledReservation canceledReservation,
            final RedirectAttributes redirectAttributes) {
        Reserved reserved = this.reservedService.findById(canceledReservation.getIdReservation());
        switch (canceledReservation.getType()) {
        case 0:
            this.reservedService.declineReservationByRenter(reserved, canceledReservation.getComment());
            redirectAttributes.addFlashAttribute("info", new Info("FutureReservations", "deleteReservation", ""));
            return "redirect:/renter/futureReservations/" + reserved.getApartment().getId();
        case 1:
            this.reservedService.declineReservationByRenterFake(reserved, canceledReservation.getComment());
            redirectAttributes.addFlashAttribute("info", new Info("CurrentReservations", "deleteReservation", ""));
            return "redirect:/renter/currentReservations/" + reserved.getApartment().getId();
        case 2:
            this.reservedService.declineReservationByRenterBadDeal(reserved, canceledReservation.getComment());
            redirectAttributes.addFlashAttribute("info", new Info("NeedConfirmReservations", "deleteReservation", ""));
            return "redirect:/renter/needConfirmReservations/" + reserved.getApartment().getId();
        default:
            this.reservedService.declineReservationByRenter(reserved, canceledReservation.getComment());
            redirectAttributes.addFlashAttribute("info", new Info("FutureReservations", "deleteReservation", ""));
            return "redirect:/renter/futureReservations/" + reserved.getApartment().getId();
        }
    }

    @RequestMapping(value = "/confirmReservation/{id}", method = RequestMethod.GET)
    public String confirmReservation(@PathVariable("id") int id, final RedirectAttributes redirectAttributes) {
        Reserved reserved = reservedService.findById(id);
        reservedService.confirmReservation(reserved);
        redirectAttributes.addFlashAttribute("info", new Info("NeedConfirmReservations", "confirmReservation", ""));
        return "redirect:/renter/needConfirmReservations/" + reserved.getApartment().getId();
    }

    @RequestMapping(value = "/reservationDetail/{id}", method = RequestMethod.GET)
    public String reservationDetail(@PathVariable("id") int idReseration, Model model) {
        Reserved reservation = this.reservedService.findById(idReseration);
        model.addAttribute("reservation", reservation);
        model.addAttribute("apartment", reservation.getApartment());
        return "reservationDetail";
    }

    @RequestMapping(value = "/backToHistory/{id}", method = RequestMethod.GET)
    public String backToHistory(@PathVariable("id") int idReseration, Model model) {
        Reserved reservation = this.reservedService.findById(idReseration);
        model.addAttribute("apartment", reservation.getApartment());
        model.addAttribute("pastReserved", this.reservedService.getPastReserved(reservation.getApartment()));
        return "historyReservations";
    }

    /*
     * @RequestMapping(value = "/futureReservations/{id}", method =
     * RequestMethod.GET) public String futureReservations(@PathVariable("id")
     * int idApartment, Model model) { Apartment apartment =
     * this.apartmentService.findById(idApartment);
     * model.addAttribute("apartment", apartment);
     * model.addAttribute("futereReserved",
     * this.reservedService.getFutureReserved(apartment));
     * model.addAttribute("canceledReservation", new CanceledReservation());
     * return "futureReservations"; }
     */

/*    @RequestMapping(value = "/needConfirmReservations/{id}", method = RequestMethod.GET)
    public String needConfirmReservations(@PathVariable("id") int idApartment, Model model) {
        Apartment apartment = this.apartmentService.findById(idApartment);
        model.addAttribute("apartment", apartment);
        model.addAttribute("needConfirmReserved", this.reservedService.getNeedConfirm(apartment));
        model.addAttribute("canceledReservation", new CanceledReservation());
        return "needConfirmReservations";
    }*/

/*    @RequestMapping(value = "/currentReservations/{id}", method = RequestMethod.GET)
    public String currentReservations(@PathVariable("id") int idApartment, Model model) {
        Apartment apartment = this.apartmentService.findById(idApartment);
        model.addAttribute("apartment", apartment);
        model.addAttribute("currentReserved", this.reservedService.getCurrentReserved(apartment));
        model.addAttribute("canceledReservation", new CanceledReservation());
        return "currentReservations";
    }*/
    
    @RequestMapping(value = "/currentReservations/{id}", method = RequestMethod.GET)
    public String currentReservations(@PathVariable("id") int idApartment, Model model,
            @RequestParam(value = "size", defaultValue = "5") Integer pageSize,
            @RequestParam(value = "page", defaultValue = "1") Integer currentPage) {
        PaginationInfoDto apartmentPageInfo = new PaginationInfoDto(pageSize, currentPage);
        Apartment apartment = this.apartmentService.findById(idApartment);
        model.addAttribute("apartment", apartment);
        model.addAttribute("currentReserved", this.reservedService.getCurrentReserved(apartment));
        model.addAttribute("canceledReservation", new CanceledReservation());
        model.addAttribute("lastPageIndex", apartmentPageInfo.getPageCount());
        model.addAttribute("currentPage", apartmentPageInfo.getCurrentPage());
        return "currentReservations";
    }

/*    @RequestMapping(value = "/historyReservations/{id}", method = RequestMethod.GET)
    public String history(@PathVariable("id") int idApartment, Model model) {
        Apartment apartment = this.apartmentService.findById(idApartment);
        model.addAttribute("apartment", apartment);
        model.addAttribute("pastReserved", this.reservedService.getPastReserved(apartment));
        return "historyReservations";
    }*/
    
    @RequestMapping(value = "/historyReservations/{id}", method = RequestMethod.GET)
    public String history(@PathVariable("id") int idApartment, Model model,
            @RequestParam(value = "size", defaultValue = "3") Integer pageSize,
            @RequestParam(value = "page", defaultValue = "1") Integer currentPage) {
        PaginationInfoDto apartmentPageInfo = new PaginationInfoDto(pageSize, currentPage);
        Apartment apartment = this.apartmentService.findById(idApartment);
        model.addAttribute("apartment", apartment);
        model.addAttribute("pastReserved", this.reservedService.getPastReserved(apartment, apartmentPageInfo));
        model.addAttribute("lastPageIndex", apartmentPageInfo.getPageCount());
        model.addAttribute("currentPage", apartmentPageInfo.getCurrentPage());
        return "historyReservations";
    }

    @RequestMapping(value = "/futureReservations/{id}", method = RequestMethod.GET)
    public String futureReservations(@PathVariable("id") int idApartment, Model model,
            @RequestParam(value = "size", defaultValue = "5") Integer pageSize,
            @RequestParam(value = "page", defaultValue = "1") Integer currentPage) {
        PaginationInfoDto apartmentPageInfo = new PaginationInfoDto(pageSize, currentPage);
        Apartment apartment = this.apartmentService.findById(idApartment);
        model.addAttribute("apartment", apartment);
        model.addAttribute("futereReserved", this.reservedService.getFutureReserved(apartment, apartmentPageInfo));
        model.addAttribute("canceledReservation", new CanceledReservation());
        model.addAttribute("lastPageIndex", apartmentPageInfo.getPageCount());
        model.addAttribute("currentPage", apartmentPageInfo.getCurrentPage());
        return "futureReservations";
    }
    
    @RequestMapping(value = "/needConfirmReservations/{id}", method = RequestMethod.GET)
    public String needConfirmReservations(@PathVariable("id") int idApartment, Model model,
            @RequestParam(value = "size", defaultValue = "5") Integer pageSize,
            @RequestParam(value = "page", defaultValue = "1") Integer currentPage) {
        PaginationInfoDto apartmentPageInfo = new PaginationInfoDto(pageSize, currentPage);
        Apartment apartment = this.apartmentService.findById(idApartment);
        model.addAttribute("apartment", apartment);
        model.addAttribute("needConfirmReserved", this.reservedService.getNeedConfirm(apartment, apartmentPageInfo));
        model.addAttribute("canceledReservation", new CanceledReservation());
        model.addAttribute("lastPageIndex", apartmentPageInfo.getPageCount());
        model.addAttribute("currentPage", apartmentPageInfo.getCurrentPage());
        return "needConfirmReservations";
    }

}
