package com.java6.airlineservice.airlineservice.controllers;

import com.java6.airlineservice.airlineservice.models.Airport;
import com.java6.airlineservice.airlineservice.services.AirportServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class AiportController {

    @Autowired
    AirportServices airportServices;

    @GetMapping("/location/id/{locationId}")
    public String findAllByLocationId(@PathVariable("locationId") Long locationId) {
        List<Airport> airports = airportServices.findAllByLocationId(locationId);
        return "sample";
    }

    public String findAllByName(@PathVariable("name") String name) {
        List<Airport> airports = airportServices.findAllByName(name);
        return "sample";
    }

    public  String findAllByCode(@PathVariable("code") String code) {
        List<Airport> airports = airportServices.findAllByCode(code);
    return "sample";
}

}