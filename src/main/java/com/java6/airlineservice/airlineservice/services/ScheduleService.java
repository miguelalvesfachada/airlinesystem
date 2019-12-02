package com.java6.airlineservice.airlineservice.services;

import com.java6.airlineservice.airlineservice.exception.ScheduleCapacityException;
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

    public List<Schedule> searchForAvailableFlightSchedules(String fromLocation, String toLocation,
                                                            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate flightTime,
                                                            Integer numberOfPeople) {
        LocalDateTime flightTimeFrom = flightTime.atStartOfDay();
        LocalDateTime flightTimeTo = flightTimeFrom.plusDays(1).minusSeconds(1);
        System.out.println(flightTimeFrom + "::" + flightTimeTo);
        return scheduleRepository.findAllByFromAirportCodeAndToAirportCodeAndDeptTimeBetweenAndRemCapacityGreaterThanEqual(fromLocation, toLocation, flightTimeFrom, flightTimeTo, numberOfPeople);
    }

    public Schedule getScheduleById (Long scheduleId){
        return scheduleRepository.findById(scheduleId).get();
    }

    public void reduceScheduleCapacity (Long scheduleId, int numberToReduce){
        Schedule schedule = getScheduleById(scheduleId);
        Integer remainingCapacity = schedule.getRemCapacity();
        if(remainingCapacity < numberToReduce) {
            throw new ScheduleCapacityException("Flight is fully booked", scheduleId, numberToReduce);
        }
        schedule.setRemCapacity(remainingCapacity - numberToReduce);
        scheduleRepository.save(schedule);
    }

    public Map<String, List<Schedule>> searchForFlightSchedulesWithReturn(String fromLocation, String toLocation, LocalDate flightTime, LocalDate returnFlightTime) {
        LocalDateTime flightTimeFrom = flightTime.atStartOfDay();
        LocalDateTime flightTimeTo = flightTimeFrom.plusDays(1).minusSeconds(1);
        LocalDateTime returnflightTimeFrom = returnFlightTime.atStartOfDay();
        LocalDateTime returnflightTimeTo = returnflightTimeFrom.plusDays(1).minusSeconds(1);
        List<Schedule> toFlightList = scheduleRepository.findAllByFromAirportCodeAndToAirportCodeAndDeptTimeBetweenAndRemCapacityGreaterThanEqual(fromLocation,toLocation,flightTimeFrom,flightTimeTo, 1);

        final String temp = fromLocation;
        fromLocation = toLocation;
        toLocation = temp;


        List<Schedule> returnFlightList = scheduleRepository.findAllByFromAirportCodeAndToAirportCodeAndDeptTimeBetweenAndRemCapacityGreaterThanEqual(fromLocation,toLocation,returnflightTimeFrom,returnflightTimeTo, 1);

        Map<String, List<Schedule>> flightsAndReturnFlights = new HashMap<String, List<Schedule>>();
        flightsAndReturnFlights.put("toflights", toFlightList);
        flightsAndReturnFlights.put("returnflights", returnFlightList);
        return flightsAndReturnFlights;

    }

    public Schedule addSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }
}
