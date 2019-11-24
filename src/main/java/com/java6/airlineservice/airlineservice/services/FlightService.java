package com.java6.airlineservice.airlineservice.services;

import com.java6.airlineservice.airlineservice.models.Flight;
import com.java6.airlineservice.airlineservice.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class FlightService{

    @Autowired
    FlightRepository flightRepository;

    Optional<Flight> findByFlightName(String name) {
            Optional<Flight> flight = flightRepository.findByFlightName(name);
            return flight;
    }
}
