package com.finartz.airlinesticketsystem.repository.flight;

import com.finartz.airlinesticketsystem.domain.flight.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
}
