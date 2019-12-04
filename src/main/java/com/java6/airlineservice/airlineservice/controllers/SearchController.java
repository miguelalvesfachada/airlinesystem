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

        Optional<Long> fromLocationId = Optional.ofNullable(searchParameters.getFromLocationId());
        Optional<Long> toLocationId = Optional.ofNullable(searchParameters.getToLocationId());

        if (fromLocationId.isPresent() && toLocationId.isPresent()) {
            List<Airport> fromAirports = airportServices.findAllByLocationId(fromLocationId.get());
            List<Airport> toAirports = airportServices.findAllByLocationId(toLocationId.get());
            List<Schedule> schedules = new ArrayList<>();

            for (Airport fromAirport: fromAirports) {
                for (Airport toAirport: toAirports) {
                    schedules.addAll(scheduleService.searchForAvailableFlightSchedules(fromAirport.getCode(),
                            toAirport.getCode(), searchParameters.getFlightTime(),searchParameters.getNumberOfPeople()));
                }
            }
            modelAndView.addObject("schedules", schedules);
        } else if (fromLocationId.isPresent()) {
            List<Airport> fromAirports = airportServices.findAllByLocationId(fromLocationId.get());
            List<Schedule> schedules = new ArrayList<>();
            for (Airport fromAirport: fromAirports) {
                schedules.addAll(scheduleService.searchForAvailableFlightSchedules(fromAirport.getCode(),
                        searchParameters.getToAirport(), searchParameters.getFlightTime(),searchParameters.getNumberOfPeople()));
            }
            modelAndView.addObject("schedules", schedules);
        } else if (toLocationId.isPresent()) {
            List<Airport> toAirports = airportServices.findAllByLocationId(toLocationId.get());
            List<Schedule> schedules = new ArrayList<>();
            for (Airport toAirport: toAirports) {
                schedules.addAll(scheduleService.searchForAvailableFlightSchedules(searchParameters.getFromAirport(),
                        toAirport.getCode(), searchParameters.getFlightTime(),searchParameters.getNumberOfPeople()));
            }
            modelAndView.addObject("schedules", schedules);
        } else {
            modelAndView.addObject("schedules", scheduleService.searchForAvailableFlightSchedules(searchParameters.getFromAirport(),
                        searchParameters.getToAirport(),searchParameters.getFlightTime(),searchParameters.getNumberOfPeople()));
        }

        modelAndView.addObject("bookingError", "");


        modelAndView.setViewName("flights");
        return modelAndView;
    }

    @GetMapping("/search/return")
    public ModelAndView searchForFlightSchedulesWithReturn(String fromLocation, String toLocation,
                                                           @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate flightTime,
                                                           @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate returnFlightTime){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("toschedules", scheduleService.searchForFlightSchedulesWithReturn(fromLocation,toLocation,flightTime,returnFlightTime).get("toflights"));
        modelAndView.addObject("returnschedules", scheduleService.searchForFlightSchedulesWithReturn(fromLocation,toLocation,flightTime,returnFlightTime).get("returnflights"));
        modelAndView.setViewName("flights-return");
        return modelAndView;
    }

    //TODO schedules by location
    //TODO some smart autocomplete functionality for the user interface

}
