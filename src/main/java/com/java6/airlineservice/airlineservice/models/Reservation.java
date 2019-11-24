package com.java6.airlineservice.airlineservice.models;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long scheduleId;
    private String name;
    private String bookingCode;
    @Enumerated (value = EnumType.STRING)
    private ReservationStatus status;
}
