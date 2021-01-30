package com.finartz.airlinesticketsystem.api.airline;

import com.finartz.airlinesticketsystem.exception.DataIntegrityViolationDbException;
import com.finartz.airlinesticketsystem.exception.airline.AirlineNotFoundException;
import com.finartz.airlinesticketsystem.model.airline.AirlineDto;
import com.finartz.airlinesticketsystem.service.airline.impl.AirlineServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
public class AirlineApi {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AirlineServiceImpl service;

    @GetMapping("/airlines")
    public List<AirlineDto> retrieveAllAirlines() {
        logger.info("Airline service started");
        return service.findAll();
    }

    @GetMapping("/airlines/{id}")
    public AirlineDto retrieveAirline(@PathVariable int id) {
        logger.info("RetriveAirline with id param service started");
        AirlineDto airline;
        try {
            airline = service.findById(id);
            return airline;
        } catch (AirlineNotFoundException ex) {
            logger.error("Error in retrieveAirline service : {" + ex + " }");
            throw new AirlineNotFoundException("id-" + id);
        }
    }

    @GetMapping("/airlines/search")
    public List<AirlineDto> retrieveAirlines(@RequestParam(required = false) Boolean status,
                                          @RequestParam(required = false) String name,
                                          @RequestParam(required = false) Integer digitCode,
                                          @RequestParam(required = false) String iataCode) {
        logger.info("RetriveAirlines search service started");
        List<AirlineDto> airlines = service.findByParams(status, name, digitCode, iataCode);
        return airlines;
    }

    @PostMapping("/airline")
    public ResponseEntity<Object> createAirline(@Valid @RequestBody AirlineDto airlineDto) {
        logger.info("CreateAirline service started");

        ResponseEntity<Object> savedAirline;
        try {
            savedAirline = service.createAirline(airlineDto);
            return savedAirline;
        } catch (DataIntegrityViolationDbException ex) {
            logger.error("Error in createAirline service Could not create airline: {" + ex + " }");
            throw new DataIntegrityViolationDbException("Could not create airline");
        }
    }

}
