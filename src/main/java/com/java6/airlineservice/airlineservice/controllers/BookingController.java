package com.java6.airlineservice.airlineservice.controllers;


import com.java6.airlineservice.airlineservice.models.Reservation;
import com.java6.airlineservice.airlineservice.models.Schedule;
import com.java6.airlineservice.airlineservice.repository.ScheduleRepository;
import com.java6.airlineservice.airlineservice.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BookingController {

    @Autowired
    private BookingService bookingService;
    @Autowired
    private ScheduleRepository scheduleRepository;


    @GetMapping ("/book/{id}")
    public String book (@PathVariable long id, Model model) {
        Schedule schedule = scheduleRepository.getOne(id);
        model.addAttribute("schedule", schedule);
        model.addAttribute("reservation", new Reservation());
        return "booking";
    }

   /* @PostMapping("/booking")
    public String bookingAction (Model model, Schedule schedule){
        model.addAttribute("schedule",schedule);
        return "confirmBooking";
    }*/


    @PostMapping("/booking/confirm")
    public String confirmBooking (Model model, Reservation reservation) {
        Reservation savedReservation = bookingService.addBooking(reservation);
        model.addAttribute("bookingCode", savedReservation.getBookingCode());
        return "booking-successful";
    }



}
