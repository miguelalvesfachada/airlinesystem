package com.java6.airlineservice.airlineservice.models;

import com.sun.javafx.beans.IDProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fromAirportCode;
    private String toAirportCode;
    private LocalDate deptTime;
    private LocalDate arrivalTime;
    private Long remCapacity;

}
