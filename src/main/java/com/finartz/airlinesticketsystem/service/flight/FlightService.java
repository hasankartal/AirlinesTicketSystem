package com.finartz.airlinesticketsystem.service.flight;

import com.finartz.airlinesticketsystem.model.flight.FlightDto;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface FlightService {

    List<FlightDto> findAll();
    FlightDto findById(long id);
    List<FlightDto> findByParams(Boolean status, BigDecimal flightFee, Integer passengerCount, Date flightDate, Long routeFlight);
    ResponseEntity<Object> createFlight(FlightDto flightDto);
    ResponseEntity<Object> increaseFlightQuota(Long flightNo, Integer passengerCount);
}