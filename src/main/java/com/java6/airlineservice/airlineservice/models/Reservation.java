package com.java6.airlineservice.airlineservice.models;

import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
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
