package com.java6.airlineservice.airlineservice.services;

import com.java6.airlineservice.airlineservice.models.Schedule;
import com.java6.airlineservice.airlineservice.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

@Service
public class ScheduleService {

    @Autowired
    ScheduleRepository scheduleRepository;

    public List<Schedule> searchForFlightSchedules(String fromLocation, String toLocation, @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate flightTime) {
        LocalDateTime flightTimeFrom = flightTime.atStartOfDay();
        LocalDateTime flightTimeTo = flightTimeFrom.plusDays(1).minusSeconds(1);
        System.out.println(flightTimeFrom + "::" + flightTimeTo);
        return scheduleRepository.findAllByFromAirportCodeAndToAirportCodeAndDeptTimeBetween(fromLocation, toLocation, flightTimeFrom, flightTimeTo);
    }

    public void reduceScheduleCapacity (Long scheduleId, int numberToReduce){
        Schedule schedule = scheduleRepository.findById(scheduleId).get();
        Long remainingCapacity = schedule.getRemCapacity();
        schedule.setRemCapacity(remainingCapacity - numberToReduce);
        scheduleRepository.save(schedule);
    }

    public Map<String, List<Schedule>> searchForFlightSchedulesWithReturn(String fromLocation, String toLocation, LocalDate flightTime, LocalDate returnFlightTime) {
        LocalDateTime flightTimeFrom = flightTime.atStartOfDay();
        LocalDateTime flightTimeTo = flightTimeFrom.plusDays(1).minusSeconds(1);
        LocalDateTime returnflightTimeFrom = returnFlightTime.atStartOfDay();
        LocalDateTime returnflightTimeTo = returnflightTimeFrom.plusDays(1).minusSeconds(1);
        List<Schedule> toFlightList = scheduleRepository.findAllByFromAirportCodeAndToAirportCodeAndDeptTimeBetween(fromLocation,toLocation,flightTimeFrom,flightTimeTo);

        final String temp = fromLocation;
        fromLocation = toLocation;
        toLocation = temp;


        List<Schedule> returnFlightList = scheduleRepository.findAllByFromAirportCodeAndToAirportCodeAndDeptTimeBetween(fromLocation,toLocation,returnflightTimeFrom,returnflightTimeTo);

        Map<String, List<Schedule>> flightsAndReturnFlights = new HashMap<String, List<Schedule>>();
        flightsAndReturnFlights.put("toflights", toFlightList);
        flightsAndReturnFlights.put("returnflights", returnFlightList);
        return flightsAndReturnFlights;

    }
}
