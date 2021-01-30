package com.finartz.airlinesticketsystem.repository.airport;

import com.finartz.airlinesticketsystem.domain.airport.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AirportRepository extends JpaRepository<Airport, Long> {

}