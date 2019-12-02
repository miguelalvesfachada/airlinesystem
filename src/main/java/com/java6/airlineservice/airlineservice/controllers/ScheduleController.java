package com.java6.airlineservice.airlineservice.controllers;

import com.java6.airlineservice.airlineservice.models.Schedule;
import com.java6.airlineservice.airlineservice.services.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

@Controller
public class ScheduleController {

    //TODO some sort of sorting mechanism to display schedules - by price, flight duration ... (possibly done in the front end by jquery)

}
