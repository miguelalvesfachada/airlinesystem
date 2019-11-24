package com.java6.airlineservice.airlineservice.repository;

import com.java6.airlineservice.airlineservice.models.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FlightRepository extends JpaRepository<Flight,Long> {
}
