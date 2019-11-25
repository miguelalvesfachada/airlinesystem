package com.java6.airlineservice.airlineservice.services;

import com.java6.airlineservice.airlineservice.models.Schedule;
import com.java6.airlineservice.airlineservice.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {

    @Autowired
    ScheduleRepository scheduleRepository;

    public List<Schedule> searchForFlightSchedules(String fromLocation, String toLocation, @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate flightTime) {
        return scheduleRepository.findAllByFromAirportCodeAndToAirportCodeAndDeptTime(fromLocation, toLocation, flightTime);
    }

}
