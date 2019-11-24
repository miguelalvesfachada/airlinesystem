package com.java6.airlineservice.airlineservice.services;

import com.java6.airlineservice.airlineservice.models.Airport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository{

    List<Airport> findAllByScheduleId (Long ScheduleId);
    List<Airport> findAllByName (String name);

}
