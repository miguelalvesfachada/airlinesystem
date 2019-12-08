package com.java6.airlineservice.airlineservice.services;

import com.java6.airlineservice.airlineservice.models.Flight;
import com.java6.airlineservice.airlineservice.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FlightService{

    @Autowired
    FlightRepository flightRepository;

    public List<Flight> findAllFlights() {
        return flightRepository.findAll();
    }

    public Flight addFlight(Flight flight) {
        return flightRepository.save(flight);
    }

    public void deleteFlight(Flight flight){
        flightRepository.delete(flight);
    }

    public Flight editFlight(Flight flight) {
        return flightRepository.save(flight);
    }
}
