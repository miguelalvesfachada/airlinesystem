package com.java6.airlineservice.airlineservice.controllers;

import com.java6.airlineservice.airlineservice.models.Airport;
import com.java6.airlineservice.airlineservice.models.Schedule;
import com.java6.airlineservice.airlineservice.models.SearchParameters;
import com.java6.airlineservice.airlineservice.services.AirportServices;
import com.java6.airlineservice.airlineservice.services.LocationServices;
import com.java6.airlineservice.airlineservice.services.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class SearchController {

    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private AirportServices airportServices;
    @Autowired
    private LocationServices locationServices;

    @GetMapping("")
    public ModelAndView returnSearchPage(){
        ModelAndView modelAndView = new ModelAndView();
        LocalDate date = LocalDate.now();
        modelAndView.addObject("airports", airportServices.findAllAirports());
        modelAndView.addObject("locations", locationServices.findAllLocations());
        modelAndView.addObject("currentTime", date);
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @GetMapping("/search")
    public ModelAndView searchForAvailableFlightSchedules(ModelAndView modelAndView, @Valid SearchParameters searchParameters){


        modelAndView.addObject("schedules", scheduleService.searchForAvailableFlightSchedules(searchParameters));
        modelAndView.addObject("bookingError", "");


        modelAndView.setViewName("flights");
        return modelAndView;
    }

    @GetMapping("/search/return")
    public ModelAndView searchForFlightSchedulesWithReturn(ModelAndView modelAndView, SearchParameters searchParameters){
        modelAndView.addObject("toschedules", scheduleService.searchForFlightSchedulesWithReturn(searchParameters).get("toFlights"));
        modelAndView.addObject("returnschedules", scheduleService.searchForFlightSchedulesWithReturn(searchParameters).get("returnFlights"));
        modelAndView.setViewName("flights-return");
        return modelAndView;
    }


}
