package com.java6.airlineservice.airlineservice.controllers;

import com.java6.airlineservice.airlineservice.services.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.Instant;

@Controller
public class SearchController {

    @Autowired
    private ScheduleService scheduleService;

    @GetMapping("/search")
    public ModelAndView searchForFlightSchedules(String fromLocation, String toLocation, Instant flightTime) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("flights");
        modelAndView.addObject("schedules", scheduleService.searchForFlightSchedules(fromLocation, toLocation, flightTime));
        return modelAndView;
    }
}
