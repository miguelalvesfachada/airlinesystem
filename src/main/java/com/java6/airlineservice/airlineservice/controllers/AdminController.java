package com.java6.airlineservice.airlineservice.controllers;

import com.java6.airlineservice.airlineservice.models.*;
import com.java6.airlineservice.airlineservice.repository.AirportRepository;
import com.java6.airlineservice.airlineservice.repository.FlightRepository;
import com.java6.airlineservice.airlineservice.repository.ScheduleRepository;
import com.java6.airlineservice.airlineservice.repository.FlightRepository;
import com.java6.airlineservice.airlineservice.repository.LocationRepository;
import com.java6.airlineservice.airlineservice.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.jws.WebParam;

@Controller
public class AdminController {
    @Autowired
    AirportServices airportServices;
    @Autowired
    LocationServices locationServices;
    @Autowired
    FlightService flightService;
    @Autowired
    ScheduleService scheduleService;
    @Autowired
    BookingService bookingService;


    @GetMapping("/admin")
    public String returnAdminPage() {
        return "admin";
    }

    @GetMapping("/airport-admin")
    public String returnAirportAdmin() {
        return "airport-admin";
    }

    @GetMapping("/schedule-admin")
    public String returnScheduleAdmin(Model model) {
        model.addAttribute("flights", flightService.findAllFlights());
        return "schedule-admin";
    }



    @GetMapping("/location-admin")
    public String returnLocationAdmin() {
        return "location-admin";
    }

    @PostMapping("/addairport")
    public ModelAndView addAirport(Airport airport) {
        ModelAndView model = new ModelAndView();
        model.addObject("airport", airportServices.addAirport(airport));
        model.setViewName("airport-add-successful");
        return model;

    }

    @PostMapping("/addschedule")
    public ModelAndView addSchedule(Schedule schedule) {
        ModelAndView model = new ModelAndView();
        model.addObject("schedule", scheduleService.addSchedule(schedule));
        model.setViewName("schedule-add-successful");
        return model;


    }

    @PostMapping("/addflight")
    public ModelAndView addFlight(Flight flight) {
        ModelAndView model = new ModelAndView();
        flightService.addFlight(flight);
        model.addObject("flights",flightService.findAllFlights());
        model.setViewName("flight-admin");
        return model;
    }

    @PostMapping("/addlocation")
    public ModelAndView addLocation(Location location) {
        ModelAndView model = new ModelAndView();
        model.addObject(locationServices.addLocation(location));
        model.setViewName("location-add-successful");
        return model;

    }

    @GetMapping("/flight-admin")
    public ModelAndView viewAllFlights() {
        ModelAndView model = new ModelAndView();
        model.addObject("flights",flightService.findAllFlights());
        model.setViewName("flight-admin");
        return model;
    }

    @GetMapping("/viewReservations")
    public ModelAndView viewAllReservations(@RequestParam(value = "p", defaultValue = "0") int page) {
        ModelAndView model = new ModelAndView();
        Page<Reservation> reservationPage = bookingService.findAllReservations (page);
        model.addObject("page", reservationPage.getNumber());
        model.addObject("totalPages", reservationPage.getTotalPages());
        model.addObject("reservations",reservationPage.getContent());
        model.setViewName("list-of-all-reservations");
        return model;
    }

    @GetMapping("/viewLocations")
    public ModelAndView viewAllLocations() {
        ModelAndView model = new ModelAndView();
        model.addObject("locations",locationServices.findAllLocations ());
        model.setViewName("list-of-all-locations");
        return model;
    }

    //TODO display all reservations (max 100 per page?) --
    //TODO display all locations
    //TODO display all schedules (max 100 per page?)
    //TODO editing functionality for the repositories
    //TODO deleting functionality for the repositories
    //TODO SPRING SECURITY for admin login
}
