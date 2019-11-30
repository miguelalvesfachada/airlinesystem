package com.java6.airlineservice.airlineservice.repository;

import com.java6.airlineservice.airlineservice.models.Airport;
import com.java6.airlineservice.airlineservice.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Integer>{

    List<Airport> findAllByScheduleId (Long ScheduleId);
    List<Airport> findAllByName (String name);
    Optional<Reservation> findByBookingCode(String bookingCode);

}
