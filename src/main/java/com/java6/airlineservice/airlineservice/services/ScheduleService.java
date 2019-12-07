package com.java6.airlineservice.airlineservice.services;

import com.java6.airlineservice.airlineservice.exception.ScheduleCapacityException;
import com.java6.airlineservice.airlineservice.models.Airport;
import com.java6.airlineservice.airlineservice.models.Schedule;
import com.java6.airlineservice.airlineservice.models.SearchParameters;
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
    @Autowired
    AirportServices airportServices;

    public List<Schedule> searchForAvailableFlightSchedules(SearchParameters searchParameters) {
        Optional<Long> fromLocationId = Optional.ofNullable(searchParameters.getFromLocationId());
        Optional<Long> toLocationId = Optional.ofNullable(searchParameters.getToLocationId());
        LocalDateTime flightTimeFrom = searchParameters.getFlightTime().atStartOfDay();
        LocalDateTime flightTimeTo = flightTimeFrom.plusDays(1).minusSeconds(1);

        if (fromLocationId.isPresent() && toLocationId.isPresent()) {
            List<Airport> fromAirports = airportServices.findAllByLocationId(fromLocationId.get());
            List<Airport> toAirports = airportServices.findAllByLocationId(toLocationId.get());
            List<Schedule> schedules = new ArrayList<>();


            for (Airport fromAirport: fromAirports) {
                for (Airport toAirport: toAirports) {
                    schedules.addAll(scheduleRepository.findAllByFromAirportCodeAndToAirportCodeAndDeptTimeBetweenAndRemCapacityGreaterThanEqual(fromAirport.getCode(),
                            toAirport.getCode(),flightTimeFrom,flightTimeTo, searchParameters.getNumberOfPeople()));
                }
            }
            return schedules;

        } else if (fromLocationId.isPresent()) {
            List<Airport> fromAirports = airportServices.findAllByLocationId(fromLocationId.get());
            List<Schedule> schedules = new ArrayList<>();
            for (Airport fromAirport: fromAirports) {
                schedules.addAll(scheduleRepository.findAllByFromAirportCodeAndToAirportCodeAndDeptTimeBetweenAndRemCapacityGreaterThanEqual(fromAirport.getCode(),
                        searchParameters.getToAirport(), flightTimeFrom, flightTimeTo,searchParameters.getNumberOfPeople()));
            }
            return schedules;
        } else if (toLocationId.isPresent()) {
            List<Airport> toAirports = airportServices.findAllByLocationId(toLocationId.get());
            List<Schedule> schedules = new ArrayList<>();
            for (Airport toAirport: toAirports) {
                schedules.addAll(scheduleRepository.findAllByFromAirportCodeAndToAirportCodeAndDeptTimeBetweenAndRemCapacityGreaterThanEqual(searchParameters.getFromAirport(),
                        toAirport.getCode(), flightTimeFrom, flightTimeTo,searchParameters.getNumberOfPeople()));
            }
            return schedules;
        } else {
            return scheduleRepository.findAllByFromAirportCodeAndToAirportCodeAndDeptTimeBetweenAndRemCapacityGreaterThanEqual(searchParameters.getFromAirport(),
                    searchParameters.getToAirport(), flightTimeFrom, flightTimeTo, searchParameters.getNumberOfPeople());
        }
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

    public Map<String, List<Schedule>> searchForFlightSchedulesWithReturn(SearchParameters searchParameters) {
        Optional<Long> fromLocationId = Optional.ofNullable(searchParameters.getFromLocationId());
        Optional<Long> toLocationId = Optional.ofNullable(searchParameters.getToLocationId());
        LocalDateTime flightTimeFrom = searchParameters.getFlightTime().atStartOfDay();
        LocalDateTime flightTimeTo = flightTimeFrom.plusDays(1).minusSeconds(1);
        LocalDateTime returnflightTimeFrom = searchParameters.getReturnFlightTime().atStartOfDay();
        LocalDateTime returnflightTimeTo = returnflightTimeFrom.plusDays(1).minusSeconds(1);
        Map<String, List<Schedule>> flightsAndReturnFlights = new HashMap<>();
        List<Schedule> toSchedules = new ArrayList<>();
        List<Schedule> returnSchedules = new ArrayList<>();

        if (fromLocationId.isPresent() && toLocationId.isPresent()) {
            List<Airport> fromAirports = airportServices.findAllByLocationId(fromLocationId.get());
            List<Airport> toAirports = airportServices.findAllByLocationId(toLocationId.get());



            for (Airport fromAirport: fromAirports) {
                for (Airport toAirport: toAirports) {
                    toSchedules.addAll(scheduleRepository.findAllByFromAirportCodeAndToAirportCodeAndDeptTimeBetweenAndRemCapacityGreaterThanEqual(fromAirport.getCode(),
                            toAirport.getCode(),flightTimeFrom,flightTimeTo, searchParameters.getNumberOfPeople()));
                    returnSchedules.addAll(scheduleRepository.findAllByFromAirportCodeAndToAirportCodeAndDeptTimeBetweenAndRemCapacityGreaterThanEqual(toAirport.getCode(),
                            fromAirport.getCode(),returnflightTimeFrom,returnflightTimeTo,searchParameters.getNumberOfPeople()));
                }
            }
            flightsAndReturnFlights.put("toFlights", toSchedules);
            flightsAndReturnFlights.put("returnFlights", returnSchedules);

            return flightsAndReturnFlights;

        } else if (fromLocationId.isPresent()) {
            List<Airport> fromAirports = airportServices.findAllByLocationId(fromLocationId.get());
            List<Schedule> schedules = new ArrayList<>();
            for (Airport fromAirport: fromAirports) {
                toSchedules.addAll(scheduleRepository.findAllByFromAirportCodeAndToAirportCodeAndDeptTimeBetweenAndRemCapacityGreaterThanEqual(fromAirport.getCode(),
                        searchParameters.getToAirport(), flightTimeFrom, flightTimeTo,searchParameters.getNumberOfPeople()));
                returnSchedules.addAll(scheduleRepository.findAllByFromAirportCodeAndToAirportCodeAndDeptTimeBetweenAndRemCapacityGreaterThanEqual(
                        searchParameters.getToAirport(), fromAirport.getCode(), returnflightTimeFrom, returnflightTimeTo, searchParameters.getNumberOfPeople()
                ));
            }

            flightsAndReturnFlights.put("toFlights", toSchedules);
            flightsAndReturnFlights.put("returnFlights", returnSchedules);
            return flightsAndReturnFlights;
        } else if (toLocationId.isPresent()) {
            List<Airport> toAirports = airportServices.findAllByLocationId(toLocationId.get());
            List<Schedule> schedules = new ArrayList<>();
            for (Airport toAirport: toAirports) {
                toSchedules.addAll(scheduleRepository.findAllByFromAirportCodeAndToAirportCodeAndDeptTimeBetweenAndRemCapacityGreaterThanEqual(searchParameters.getFromAirport(),
                        toAirport.getCode(), flightTimeFrom, flightTimeTo,searchParameters.getNumberOfPeople()));
                returnSchedules.addAll(scheduleRepository.findAllByFromAirportCodeAndToAirportCodeAndDeptTimeBetweenAndRemCapacityGreaterThanEqual(
                        toAirport.getCode(), searchParameters.getFromAirport(), flightTimeFrom, flightTimeTo,searchParameters.getNumberOfPeople()));
            }
            flightsAndReturnFlights.put("toFlights", toSchedules);
            flightsAndReturnFlights.put("returnFlights", returnSchedules);
            return flightsAndReturnFlights;
        } else {
            toSchedules.addAll(scheduleRepository.findAllByFromAirportCodeAndToAirportCodeAndDeptTimeBetweenAndRemCapacityGreaterThanEqual(searchParameters.getFromAirport(),
                    searchParameters.getToAirport(), flightTimeFrom, flightTimeTo,searchParameters.getNumberOfPeople()));
            returnSchedules.addAll(scheduleRepository.findAllByFromAirportCodeAndToAirportCodeAndDeptTimeBetweenAndRemCapacityGreaterThanEqual(
                    searchParameters.getToAirport(), searchParameters.getFromAirport(), flightTimeFrom, flightTimeTo,searchParameters.getNumberOfPeople()));
            flightsAndReturnFlights.put("toFlights", toSchedules);
            flightsAndReturnFlights.put("returnFlights", returnSchedules);
            return flightsAndReturnFlights;
        }

//        List<Schedule> toFlightList = scheduleRepository.findAllByFromAirportCodeAndToAirportCodeAndDeptTimeBetweenAndRemCapacityGreaterThanEqual(fromLocation,toLocation,flightTimeFrom,flightTimeTo, 1);
//
//        final String temp = fromLocation;
//        fromLocation = toLocation;
//        toLocation = temp;
//
//
//        List<Schedule> returnFlightList = scheduleRepository.findAllByFromAirportCodeAndToAirportCodeAndDeptTimeBetweenAndRemCapacityGreaterThanEqual(fromLocation,toLocation,returnflightTimeFrom,returnflightTimeTo, 1);
//
//        Map<String, List<Schedule>> flightsAndReturnFlights = new HashMap<String, List<Schedule>>();
//        flightsAndReturnFlights.put("toflights", toFlightList);
//        flightsAndReturnFlights.put("returnflights", returnFlightList);
//       return flightsAndReturnFlights;

    }



    public Schedule addSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    public void deleteSchedule(Schedule schedule){ scheduleRepository.delete(schedule);}



}
