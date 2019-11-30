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
import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {

    @Autowired
    ScheduleRepository scheduleRepository;

    public List<Schedule> searchForAvailableFlightSchedules(String fromLocation, String toLocation,
                                                            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate flightTime,
                                                            Long numberOfPeople) {
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
        Long remainingCapacity = schedule.getRemCapacity();
        if(remainingCapacity < numberToReduce) {
            throw new ScheduleCapacityException("Flight is fully booked");
        }
        schedule.setRemCapacity(remainingCapacity - numberToReduce);
        scheduleRepository.save(schedule);
    }

}
