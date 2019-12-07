package com.java6.airlineservice.airlineservice.controllers;


import com.java6.airlineservice.airlineservice.models.Reservation;
import com.java6.airlineservice.airlineservice.models.Schedule;
import com.java6.airlineservice.airlineservice.services.BookingService;
import com.java6.airlineservice.airlineservice.services.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;
    @Autowired
    private ScheduleService scheduleService;


    @GetMapping("/search")
    public String returnSearchReservation(){
        return "search-reservation";
    }


    @GetMapping ("/{id}")
    public String book (@PathVariable long id, Model model) {
        Schedule schedule = scheduleService.getScheduleById(id);
        model.addAttribute("schedule", schedule);
        model.addAttribute("reservation", new Reservation());
        return "booking";
    }

    @GetMapping("/{toid}/{returnid}")
    public String bookWithReturn(@PathVariable long toid, @PathVariable long returnid, Model model){
        Schedule toSchedule = scheduleService.getScheduleById(toid);
        Schedule returnSchedule = scheduleService.getScheduleById(returnid);
        model.addAttribute("toSchedule", toSchedule);
        model.addAttribute("returnSchedule", returnSchedule);
        return "booking-with-return";
    }



   /* @PostMapping("/booking")
    public String bookingAction (Model model, Schedule schedule){
        model.addAttribute("schedule",schedule);
        return "confirmBooking";
}*/


    @PostMapping("/confirm")
    public String confirmBooking (Model model, Reservation reservation) {
        Reservation savedReservation = bookingService.addBooking(reservation);
        model.addAttribute("bookingCode", savedReservation.getBookingCode());
        return "booking-successful";
    }

    @GetMapping("/findbooking")
    public String returnFindBookingPage(){
        return "search-reservation";
    }

    //TODO add some reservetion editing functionality: change reservation name,?,?...
    @GetMapping("/manage")
    public String manageBooking(@RequestParam("bookingCode") String bookingCode, Model model) {
        Reservation reservation =  bookingService.getBookingByCode(bookingCode);
        model.addAttribute("booking", reservation);
        model.addAttribute("schedule", scheduleService.getScheduleById(reservation.getScheduleId()));
        return "show-booking";
    }
    @PostMapping("/booking-return/confirm")
    public String confirmBooking (Model model, Long toScheduleId, Long returnScheduleId, String name) {
        Reservation toReservation = bookingService.addBooking(Reservation.builder().name(name).scheduleId(toScheduleId).build());
        Reservation returnReservation = bookingService.addBooking(Reservation.builder().name(name).scheduleId(returnScheduleId).build());
        model.addAttribute("toBookingCode", toReservation.getBookingCode());
        model.addAttribute("returnBookingCode", returnReservation.getBookingCode());
        return "booking-return-successful";
    }





    @PostMapping("/cancel")
    public String setBookingStatusToCancel(Model model, Reservation reservation){
        model.addAttribute(bookingService.cancelReservation(reservation));
        return "cancel-booking-success";
    }

    //TODO agree on the terminology - reservation vs booking, and keep using only one. Start changing the names carefully.

}
