package com.java6.airlineservice.airlineservice.repository;

import com.java6.airlineservice.airlineservice.models.Flight;
import com.java6.airlineservice.airlineservice.models.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule,Long> {

}
