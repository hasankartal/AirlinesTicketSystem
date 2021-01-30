package com.finartz.airlinesticketsystem.service.airline;

import com.finartz.airlinesticketsystem.domain.airline.Airline;
import com.finartz.airlinesticketsystem.model.airline.AirlineDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface AirlineService {

    List<AirlineDto> findAll();
    AirlineDto findById(long id);
    List<AirlineDto> findByParams(Boolean status, String name,Integer digitCode, String iataCode);
    ResponseEntity<Object> createAirline(AirlineDto airlineDto);
}
