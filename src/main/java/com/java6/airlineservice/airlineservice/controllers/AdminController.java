package com.java6.airlineservice.airlineservice.controllers;

import com.java6.airlineservice.airlineservice.models.Airport;
import com.java6.airlineservice.airlineservice.models.Schedule;
import com.java6.airlineservice.airlineservice.models.Flight;
import com.java6.airlineservice.airlineservice.models.Location;
import com.java6.airlineservice.airlineservice.repository.AirportRepository;
import com.java6.airlineservice.airlineservice.repository.FlightRepository;
import com.java6.airlineservice.airlineservice.repository.ScheduleRepository;
import com.java6.airlineservice.airlineservice.repository.FlightRepository;
import com.java6.airlineservice.airlineservice.repository.LocationRepository;
import com.java6.airlineservice.airlineservice.services.AirportServices;
import com.java6.airlineservice.airlineservice.services.FlightService;
import com.java6.airlineservice.airlineservice.services.LocationServices;
import com.java6.airlineservice.airlineservice.services.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.jws.WebParam;

@Controller
public class AdminController {
    @Autowired
    AirportServices airportServices;
    @Autowired
    AirportRepository airportRepository;

    @Autowired
    LocationServices locationServices;

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    FlightService flightService;

    @Autowired
    FlightRepository flightRepository;
    @Autowired
    ScheduleService scheduleService;

    @Autowired
    ScheduleRepository scheduleRepository;





    @GetMapping("/admin")
    public String returnAdminPage() {
        return "admin";
    }

    @GetMapping("/airport-admin")
    public String returnAirportAdmin() {
        return "airport-admin";
    }

    @GetMapping("/schedule-admin")
    public String returnScheduleAdmin(Model model){
        model.addAttribute("flights", flightRepository.findAll());
        return "schedule-admin";
    }


    @GetMapping("/flight-admin")
    public String returnFlightAdmin() {
        return "flight-admin";
    }

    @GetMapping("/location-admin")
    public String returnLocationAdmin() {
        return "location-admin";
    }

    @PostMapping("/addairport")
    public ModelAndView addAirport(Airport airport) {
        ModelAndView model = new ModelAndView();
        model.addObject(airportRepository.save(airport));
        model.setViewName("airport-add-successful");
        return model;

    }
        @PostMapping("/addschedule")
        public ModelAndView addSchedule(Schedule schedule){
            ModelAndView model= new ModelAndView();
            model.addObject("schedule",scheduleRepository.save(schedule));
            model.setViewName("schedule-add-successful");
            return model;


    }

    @PostMapping("/addflight")
    public ModelAndView addFlight(Flight flight) {
        ModelAndView model = new ModelAndView();
        model.addObject(flightRepository.save(flight));
        model.setViewName("flight-add-successful");
        return model;
    }

    @PostMapping("/addlocation")
    public ModelAndView addLocation(Location location) {
        ModelAndView model = new ModelAndView();
        model.addObject(locationRepository.save(location));
        model.setViewName("location-add-successful");
        return model;

    }
}
