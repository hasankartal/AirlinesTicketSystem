package com.finartz.airlinesticketsystem.service.airport;

import com.finartz.airlinesticketsystem.domain.airport.Airport;
import com.finartz.airlinesticketsystem.model.airport.AirportDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface AirportService {

    List<AirportDto> findAll();
    AirportDto findById(long id);
    List<AirportDto> findByParams(Boolean status, String airportName, String airportCode, String city,
                                  String country,String countryCode, String countryAreaCode);
    ResponseEntity<Object> createAirport(AirportDto airportDto);
}
