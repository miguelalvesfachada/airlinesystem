package com.java6.airlineservice.airlineservice.controllers;

import com.java6.airlineservice.airlineservice.models.Airport;
import com.java6.airlineservice.airlineservice.models.Schedule;
import com.java6.airlineservice.airlineservice.repository.AirportRepository;
import com.java6.airlineservice.airlineservice.services.AirportServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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




    @GetMapping("/admin")
    public String returnAdminPage() {
        return "admin";
    }

    @GetMapping("/airport-admin")
    public String returnAirportAdmin() {
        return "airport-admin";
    }

    @PostMapping("/addairport")
    public ModelAndView addAirport(Airport airport) {
        ModelAndView model = new ModelAndView();
        model.addObject(airportRepository.save(airport));
        model.setViewName("airport-add-successful");
        return model;
    }
}
