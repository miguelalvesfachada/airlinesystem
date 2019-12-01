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
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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

    @GetMapping("/book/{toid}/{returnid}")
    public String bookWithReturn(@PathVariable long toid, @PathVariable long returnid, Model model){
        Schedule toSchedule = scheduleRepository.getOne(toid);
        Schedule returnSchedule = scheduleRepository.getOne(returnid);
        model.addAttribute("toSchedule", toSchedule);
        model.addAttribute("returnSchedule", returnSchedule);
        return "booking-with-return";
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

    @PostMapping("/booking-return/confirm")
    public String confirmBooking (Model model, Long toScheduleId, Long returnScheduleId, String name) {
        Reservation toReservation = bookingService.addBooking(Reservation.builder().name(name).scheduleId(toScheduleId).build());
        Reservation returnReservation = bookingService.addBooking(Reservation.builder().name(name).scheduleId(returnScheduleId).build());
        model.addAttribute("toBookingCode", toReservation.getBookingCode());
        model.addAttribute("returnBookingCode", returnReservation.getBookingCode());
        return "booking-return-successful";
    }





}
