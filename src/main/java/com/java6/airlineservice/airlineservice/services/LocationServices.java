package com.java6.airlineservice.airlineservice.services;

import com.java6.airlineservice.airlineservice.models.Location;
import com.java6.airlineservice.airlineservice.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationServices {


    @Autowired
    LocationRepository locationRepository;
    List<Location> findByName(String name){
        return locationRepository.findByName(name);
    }
}
