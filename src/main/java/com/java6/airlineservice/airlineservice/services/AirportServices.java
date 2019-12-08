package com.java6.airlineservice.airlineservice.services;

import com.java6.airlineservice.airlineservice.models.Airport;
import com.java6.airlineservice.airlineservice.repository.AirportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AirportServices {

    @Autowired
    AirportRepository airportRepository;

    public List<Airport> findAllByLocationId(Long locationId) {
        return airportRepository.findAllByLocationId(locationId);
    }

    public List<Airport> findAllByName(String name) {
        return airportRepository.findAllByName(name);
    }

    public List<Airport> findAllByCode (String code) {
        return airportRepository.findAllByCode(code);
    }


    public Airport addAirport(Airport airport) {
        return airportRepository.save(airport);
    }

    public void deleteAirport(Airport airport){
        airportRepository.delete(airport);
    }

    public List<Airport> findAllAirports() {
        return airportRepository.findAll();
    }

    public Airport editAirport(Airport airport) {
        return airportRepository.save(airport);
    }
}
