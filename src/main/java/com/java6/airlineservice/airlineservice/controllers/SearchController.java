package com.java6.airlineservice.airlineservice.controllers;

import com.java6.airlineservice.airlineservice.services.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.Instant;
import java.time.LocalDate;

@Controller
public class SearchController {

    @Autowired
    private ScheduleService scheduleService;

    @GetMapping("/search")
    public ModelAndView searchForFlightSchedules(String fromLocation, String toLocation, @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate flightTime) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("schedules", scheduleService.searchForFlightSchedules(fromLocation, toLocation, flightTime));
        modelAndView.setViewName("flights");
        return modelAndView;
    }
}
