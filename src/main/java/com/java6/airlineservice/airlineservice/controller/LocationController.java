package com.java6.airlineservice.airlineservice.controller;

import com.java6.airlineservice.airlineservice.models.Location;
import com.java6.airlineservice.airlineservice.service.LocationServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class LocationController {

    @Autowired
    LocationServices locationServices;

    public String findByName(String name)(@PathVariable("name") String name){
        List<Location> locations = locationServices.findByName(name);
        return "";

    }
}
