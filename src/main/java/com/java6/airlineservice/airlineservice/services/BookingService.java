package com.java6.airlineservice.airlineservice.services;

import com.java6.airlineservice.airlineservice.models.Reservation;
import com.java6.airlineservice.airlineservice.models.ReservationStatus;
import com.java6.airlineservice.airlineservice.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.stream.Collectors;

@Service
public class BookingService {

    @Autowired
    private ReservationRepository reservationRepository;

    public Reservation addBooking (Reservation reservation) {
        reservation.setStatus(ReservationStatus.BOOKED);
        reservation.setBookingCode(generateBookingCode());
        return reservationRepository.save(reservation);
    }

    private String generateBookingCode() {

        int length = 8;
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "abcdefghijklmnopqrstuvwxyz"
                + "0123456789";
        String str = new Random().ints(length, 0, chars.length())
                .mapToObj(i -> "" + chars.charAt(i))
                .collect(Collectors.joining());

        return str.toUpperCase();
    }

}
