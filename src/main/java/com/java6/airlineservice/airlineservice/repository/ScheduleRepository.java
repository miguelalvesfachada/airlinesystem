package com.java6.airlineservice.airlineservice.repository;

import com.java6.airlineservice.airlineservice.models.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule,Long> {

    List<Schedule> findAllByFromAirportCodeAndToAirportCodeAndDeptTime(String fromAirportCode, String toAirportCode, @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate flightTime);

}
