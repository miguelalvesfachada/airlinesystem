package com.java6.airlineservice.airlineservice.repository;

import com.java6.airlineservice.airlineservice.models.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Long> {

    List<Location>findByName(String name);
}
