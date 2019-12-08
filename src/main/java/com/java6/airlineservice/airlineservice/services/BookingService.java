package com.java6.airlineservice.airlineservice.services;

import com.java6.airlineservice.airlineservice.models.Reservation;
import com.java6.airlineservice.airlineservice.models.ReservationStatus;
import com.java6.airlineservice.airlineservice.models.Schedule;
import com.java6.airlineservice.airlineservice.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class BookingService {

    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private ScheduleService scheduleService;

    public Reservation addBooking (Reservation reservation) {
        reservation.setStatus(ReservationStatus.BOOKED);
        reservation.setBookingCode(generateBookingCode());

        //TODO prevent infinite loop
        while (checkIfBookingCodeExists(reservation.getBookingCode())) {
            reservation.setBookingCode(generateBookingCode());
        }

        Long selectedScheduledId = reservation.getScheduleId();
        scheduleService.reduceScheduleCapacity(selectedScheduledId, 1);

        return reservationRepository.save(reservation);
    }

    public Reservation editReservationName(Reservation reservation) {
        Reservation changedReservation = reservationRepository.findByBookingCode(reservation.getBookingCode()).get();
        changedReservation.setName(reservation.getName());
        return reservationRepository.save(changedReservation);
    }

    //TODO reduce schedule capacity after cancelling reservation
    public Reservation cancelReservation(Reservation reservation){
       Reservation reservation1 = reservationRepository.findByBookingCode(reservation.getBookingCode()).get();
       if(reservation1.getStatus().equals(ReservationStatus.CANCELLED)){
           return reservation1;
       }

       reservation1.setStatus(ReservationStatus.CANCELLED);

       return reservationRepository.save(reservation1);
    }

    public Reservation getBookingByCode(String bookingCode) {
        return reservationRepository.findByBookingCode(bookingCode).get();
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

    private boolean checkIfBookingCodeExists(String bookingCode){
        return reservationRepository.findByBookingCode(bookingCode).isPresent();
    }

    public List<Reservation> findAllReservations() {
        return reservationRepository.findAll();
    }

    public Page<Reservation> findAllReservations(int page) {
        Pageable pageable = PageRequest.of(page, 2);
        return reservationRepository.findAll(pageable);
    }

}
