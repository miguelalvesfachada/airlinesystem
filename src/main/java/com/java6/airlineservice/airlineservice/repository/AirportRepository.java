package com.java6.airlineservice.airlineservice.repository;

import com.java6.airlineservice.airlineservice.models.Airport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AirportRepository extends JpaRepository <Airport,Long> {

    List<Airport> findAllByCode(String code);
    List<Airport> findAllByName (String name);
    List<Airport> findAllByLocationId (Long locationId);
    List<Airport> findAllById (Long airportId);
}
