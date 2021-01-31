package com.finartz.airlinesticketsystem.api.flight;

import com.finartz.airlinesticketsystem.exception.DataIntegrityViolationDbException;
import com.finartz.airlinesticketsystem.exception.flight.FlightNotFoundException;
import com.finartz.airlinesticketsystem.model.flight.FlightDto;
import com.finartz.airlinesticketsystem.service.flight.impl.FlightServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
public class FlightApi {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private FlightServiceImpl service;

    @GetMapping("/flights")
    public List<FlightDto> retrieveAllFlights() {
        logger.info("Flight service started");
        return service.findAll();
    }

    @GetMapping("/flights/{id}")
    public FlightDto retrieveFlight(@PathVariable int id) {
        logger.info("retrieveFlight with id param service started");
        FlightDto flight;
        try {
            flight = service.findById(id);
            return flight;
        } catch (FlightNotFoundException ex) {
            logger.error("Error in retrieveFlight service : {" + ex + " }");
            throw new FlightNotFoundException("id-" + id);
        }
    }

    @GetMapping("/flights/search")
    public List<FlightDto> retrieveFlights(@RequestParam(required = false) Boolean status,
                                           @RequestParam(required = false) BigDecimal flightFee,
                                           @RequestParam(required = false) Integer passengerCount,
                                           @RequestParam(required = false) Date flightDate,
                                           @RequestParam(required = false) Long routeFlight) {
        logger.info("retrieveFlights search service started");
        List<FlightDto> flights = service.findByParams(status, flightFee, passengerCount, flightDate, routeFlight);
        return flights;
    }

    @PostMapping("/flight")
    public ResponseEntity<Object> createFlight(@Valid @RequestBody FlightDto flightDto) {
        logger.info("createFlight service started");

        ResponseEntity<Object> savedFlight;
        try {
            savedFlight = service.createFlight(flightDto);
            return savedFlight;
        } catch (DataIntegrityViolationDbException ex) {
            logger.error("Error in createFlight service Could not create flight: {" + ex + " }");
            throw new DataIntegrityViolationDbException("Could not create flight");
        }
    }
}
