package com.java6.airlineservice.airlineservice.controlleradvice;

import com.java6.airlineservice.airlineservice.exception.ScheduleCapacityException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class CustomerControllerAdvice {

    @ExceptionHandler(ScheduleCapacityException.class)
    public ModelAndView scheduleCapacityExceptionHandler() {
        ModelAndView modelAndView = new ModelAndView();

        return modelAndView;
    }
}
