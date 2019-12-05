package com.java6.airlineservice.airlineservice.controlleradvice;

import com.java6.airlineservice.airlineservice.exception.ScheduleCapacityException;
import com.java6.airlineservice.airlineservice.models.Schedule;
import com.java6.airlineservice.airlineservice.models.SearchParameters;
import com.java6.airlineservice.airlineservice.services.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;

@ControllerAdvice
public class CustomerControllerAdvice {

    @Autowired
    private ScheduleService scheduleService;

    @ExceptionHandler(ScheduleCapacityException.class)
    public ModelAndView scheduleCapacityExceptionHandler(ScheduleCapacityException ex) {
        ModelAndView modelAndView = new ModelAndView();
        Long scheduleId = ex.getScheduleId();
        Integer numberOfPeople = ex.getNumberToReduce();
        Schedule schedule = scheduleService.getScheduleById(scheduleId);
        String fromAirportCode = schedule.getFromAirportCode();
        String toAirportCode = schedule.getToAirportCode();
        LocalDateTime deptTime = schedule.getDeptTime();
        modelAndView.addObject("schedules", scheduleService.searchForAvailableFlightSchedules(new SearchParameters(fromAirportCode, toAirportCode,
                deptTime.toLocalDate(), numberOfPeople)));
        modelAndView.addObject("bookingError", ex.getMessage());
        modelAndView.setViewName("flights");
        return modelAndView;
    }
}
