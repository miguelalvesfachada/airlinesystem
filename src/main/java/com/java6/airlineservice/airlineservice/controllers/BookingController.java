package com.java6.airlineservice.airlineservice.controllers;


import com.java6.airlineservice.airlineservice.models.Reservation;
import com.java6.airlineservice.airlineservice.models.Schedule;
import com.java6.airlineservice.airlineservice.repository.ScheduleRepository;
import com.java6.airlineservice.airlineservice.services.BookingService;
import com.java6.airlineservice.airlineservice.services.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;
    @Autowired
    private ScheduleService scheduleService;

    @GetMapping("/search-reservation")
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

    @GetMapping("/manage")
    public String manageBooking(@RequestParam("bookingCode") String bookingCode, Model model) {
        Reservation reservation =  bookingService.getBookingByCode(bookingCode);
        model.addAttribute("booking", reservation);
        model.addAttribute("schedule", scheduleService.getScheduleById(reservation.getScheduleId()));
        return "show-booking";
    }


    @PostMapping("/cancel")
    public String setBookingStatusToCancel(Model model, Reservation reservation){
        model.addAttribute(bookingService.cancelReservation(reservation));
        return "cancel-booking-success";
    }

}
