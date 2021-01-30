package com.finartz.airlinesticketsystem.api.airport;

import com.finartz.airlinesticketsystem.exception.DataIntegrityViolationDbException;
import com.finartz.airlinesticketsystem.exception.airport.AirportNotFoundException;
import com.finartz.airlinesticketsystem.model.airport.AirportDto;
import com.finartz.airlinesticketsystem.service.airport.impl.AirportServiceImpl;
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
public class AirportApi {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AirportServiceImpl service;

    @GetMapping("/airports")
    public List<AirportDto> retrieveAllAirports() {
        logger.info("Airport service started");
        return service.findAll();
    }

    @GetMapping("/airports/{id}")
    public AirportDto retrieveAirport(@PathVariable int id) {
        logger.info("RetriveAirport with id param service started");

        AirportDto airport;
        try {
            airport = service.findById(id);
            return airport;
        } catch (AirportNotFoundException ex) {
            logger.error("Error in retrieveAirport service : {}" + ex);
            throw new AirportNotFoundException("id-" + id);
        }
    }

    @GetMapping("/airports/search")
    public List<AirportDto> retrieveAirports(@RequestParam(required = false) Boolean status,
                                             @RequestParam(required = false) String airportName,
                                             @RequestParam(required = false) String airportCode,
                                             @RequestParam(required = false) String city,
                                             @RequestParam(required = false) String country,
                                             @RequestParam(required = false) String countryCode,
                                             @RequestParam(required = false) String countryAreaCode) {
        logger.info("RetriveAirports search service started");
        List<AirportDto> airportDtos = service.findByParams(status, airportName, airportCode, city, country, countryCode, countryAreaCode);
        return airportDtos;
    }

    @PostMapping("/airport")
    public ResponseEntity<Object> createAirport(@Valid @RequestBody AirportDto airportDto) {
        logger.info("CreateAirport service started");

        ResponseEntity<Object> savedAirport;
        try {
            savedAirport = service.createAirport(airportDto);
            return savedAirport;
        } catch (DataIntegrityViolationDbException ex) {
            logger.error("Error in createAirport service Could not create airline: {" + ex + " }");
            throw new DataIntegrityViolationDbException("Could not create airline");
        }
    }
}
