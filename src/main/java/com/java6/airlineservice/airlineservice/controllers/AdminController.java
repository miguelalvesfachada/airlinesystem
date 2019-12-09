package com.java6.airlineservice.airlineservice.controllers;

import com.java6.airlineservice.airlineservice.models.*;
import com.java6.airlineservice.airlineservice.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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
    @Secured("ROLE_ADMIN_VIEW")
    public String returnAdminPage() {
        return "admin";
    }

    @GetMapping("/airport-admin")
    @Secured("ROLE_AIRPORT_WRITE")
    public String returnAirportAdmin(Model model) {
        model.addAttribute("airports", airportServices.findAllAirports());
        model.addAttribute("locations", locationServices.findAllLocations());
        return "airport-admin";
    }

    @GetMapping("/schedule-admin")
    @Secured("ROLE_SCHEDULE_WRITE")
    public String returnScheduleAdmin(Model model){
        model.addAttribute("flights", flightService.findAllFlights());
        model.addAttribute("airports", airportServices.findAllAirports());
        model.addAttribute("schedules", scheduleService.findAllSchedules());
        return "schedule-admin";
    }

    @GetMapping("/location-admin")
    @Secured("ROLE_LOCATION_WRITE")
    public String returnLocationAdmin(Model model) {
        model.addAttribute("locations", locationServices.findAllLocations());
        return "location-admin";
    }

    @PostMapping("/addairport")
    @Secured("ROLE_AIRPORT_WRITE")
    public ModelAndView addAirport(Airport airport) {
        ModelAndView model = new ModelAndView();
        airportServices.addAirport(airport);
        model.addObject("airports", airportServices.findAllAirports());
        model.addObject("locations", locationServices.findAllLocations());
        model.setViewName("airport-admin");
        return model;


    }

    @PostMapping("/delete-airport")
    @Secured("ROLE_AIRPORT_WRITE")
    public ModelAndView deleteAirport(Airport airport) {
        ModelAndView model = new ModelAndView();
        airportServices.deleteAirport(airport);
        model.addObject("airports", airportServices.findAllAirports());
        model.addObject("locations", locationServices.findAllLocations());
        model.setViewName("airport-admin");
        return model;


    }

    @PostMapping("/admin/manage/edit/airport")
    @Secured("ROLE_AIRPORT_WRITE")
    public ModelAndView editAirport(Airport airport) {
        ModelAndView model = new ModelAndView();
        airportServices.editAirport(airport);
        model.addObject("locations", locationServices.findAllLocations());
        model.addObject("airports", airportServices.findAllAirports());
        model.setViewName("airport-admin");
        return model;
    }

    @PostMapping("/addschedule")
    @Secured("ROLE_SCHEDULE_WRITE")
    public ModelAndView addSchedule(Schedule schedule){
        ModelAndView model= new ModelAndView();
        scheduleService.addSchedule(schedule);
        model.addObject("flights", flightService.findAllFlights());
        model.addObject("airports", airportServices.findAllAirports());
        model.addObject("schedules", scheduleService.findAllSchedules());
        model.setViewName("schedule-admin");
        return model;


    }

    @PostMapping("/delete-schedule")
    @Secured("ROLE_SCHEDULE_WRITE")
    public ModelAndView deleteSchedule(Schedule schedule){
        ModelAndView model= new ModelAndView();
        scheduleService.deleteSchedule(schedule);
        model.addObject("flights", flightService.findAllFlights());
        model.addObject("airports", airportServices.findAllAirports());
        model.addObject("schedules", scheduleService.findAllSchedules());
        model.setViewName("schedule-admin");
        return model;


    }

    @PostMapping("/admin/manage/edit/schedule")
    @Secured("ROLE_SCHEDULE_WRITE")
    public ModelAndView editSchedule(Schedule schedule) {
        ModelAndView model = new ModelAndView();
        scheduleService.editSchedule(schedule);
        model.addObject("flights", flightService.findAllFlights());
        model.addObject("airports", airportServices.findAllAirports());
        model.addObject("schedules", scheduleService.findAllSchedules());
        model.setViewName("schedule-admin");
        return model;
    }

    @PostMapping("/addflight")
    @Secured("ROLE_FLIGHT_WRITE")
    public ModelAndView addFlight(Flight flight) {
        ModelAndView model = new ModelAndView();
        flightService.addFlight(flight);
        model.addObject("flights",flightService.findAllFlights());
        model.setViewName("flight-admin");
        return model;
    }

    @PostMapping("/delete-flight")
    @Secured("ROLE_FLIGHT_WRITE")
    public ModelAndView deleteFlight(Flight flight){
        ModelAndView model = new ModelAndView();
        flightService.deleteFlight(flight);
        model.addObject("flights", flightService.findAllFlights());
        model.setViewName("flight-admin");
        return model;
    }

    @PostMapping("/admin/manage/edit/flight")
    @Secured("ROLE_FLIGHT_WRITE")
    public ModelAndView editFlight(Flight flight) {
        ModelAndView model = new ModelAndView();
        flightService.editFlight(flight);
        model.addObject("flights", flightService.findAllFlights());
        model.setViewName("flight-admin");
        return model;
    }


    @PostMapping("/addlocation")
    @Secured("ROLE_LOCATION_WRITE")
    public ModelAndView addLocation(Location location) {
        ModelAndView model = new ModelAndView();
        locationServices.addLocation(location);
        model.addObject("locations", locationServices.findAllLocations());
        model.setViewName("location-admin");
        return model;

    }

    @PostMapping("/delete-location")
    @Secured("ROLE_LOCATION_WRITE")
    public ModelAndView deleteLocation(Location location) {
        ModelAndView model = new ModelAndView();
        locationServices.deleteLocation(location);
        model.addObject("locations", locationServices.findAllLocations());
        model.setViewName("location-admin");
        return model;

    }

    @PostMapping("/admin/manage/edit/location")
    @Secured("ROLE_LOCATION_WRITE")
    public ModelAndView editLocation(Location location) {
        ModelAndView model = new ModelAndView();
        locationServices.editLocation(location);
        model.addObject("locations", locationServices.findAllLocations());
        model.setViewName("location-admin");
        return model;
    }

    @GetMapping("/flight-admin")
    @Secured("ROLE_FLIGHT_WRITE")
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
