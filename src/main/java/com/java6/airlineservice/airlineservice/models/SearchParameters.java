package com.java6.airlineservice.airlineservice.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Min;
import java.time.LocalDate;
import java.util.Optional;

@Data
@NoArgsConstructor
public class SearchParameters {
    private String fromAirport;
    private String toAirport;
    private Long fromLocationId;
    private Long toLocationId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate flightTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate returnFlightTime;
    @Min(value = 1)
    private Integer numberOfPeople;

    public SearchParameters(String fromAirport, String toAirport, LocalDate flightTime, @Min(value = 1) Integer numberOfPeople) {
        this.fromAirport = fromAirport;
        this.toAirport = toAirport;
        this.flightTime = flightTime;
        this.numberOfPeople = numberOfPeople;
    }
}
