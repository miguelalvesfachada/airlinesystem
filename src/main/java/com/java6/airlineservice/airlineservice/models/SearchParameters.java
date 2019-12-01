package com.java6.airlineservice.airlineservice.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class SearchParameters {
    private String fromLocation;
    private String toLocation;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate flightTime;
    @Min(value = 1)
    private Integer numberOfPeople;
}
