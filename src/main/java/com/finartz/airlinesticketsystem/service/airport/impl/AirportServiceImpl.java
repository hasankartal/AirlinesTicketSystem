package com.finartz.airlinesticketsystem.service.airport.impl;

import com.finartz.airlinesticketsystem.domain.airport.Airport;
import com.finartz.airlinesticketsystem.exception.DataIntegrityViolationDbException;
import com.finartz.airlinesticketsystem.exception.airport.AirportNotFoundException;
import com.finartz.airlinesticketsystem.model.airport.AirportDto;
import com.finartz.airlinesticketsystem.repository.airport.AirportRepository;
import com.finartz.airlinesticketsystem.service.airport.AirportService;
import com.finartz.airlinesticketsystem.util.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AirportServiceImpl implements AirportService {

    private final AirportRepository airportRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<AirportDto> findAll() {
        List<Airport> airports = airportRepository.findAll();
        List<AirportDto> airportDtos = new ArrayList<>();

        airports.forEach(item ->{
            AirportDto airportDto = new AirportDto();
            airportDto.setId(item.getId());
            airportDto.setAirportCode(item.getAirportCode());
            airportDto.setAirportName(item.getAirportName());
            airportDto.setCity(item.getCity());
            airportDto.setCountry(item.getCountry());
            airportDto.setCountryAreaCode(item.getCountryAreaCode());
            airportDto.setCountryCode(item.getCountryCode());
            airportDto.setStatus(item.getStatus());

            airportDtos.add(airportDto);
        });

        return airportDtos;
    }

    @Override
    public AirportDto findById(long id) throws AirportNotFoundException {
        Optional<Airport> airport = airportRepository.findById(id);
        if(!airport.isPresent()) {
            throw new AirportNotFoundException("id-" + id);
        }

        AirportDto airportDto = new AirportDto();
        airportDto.setAirportCode(airport.get().getAirportCode());
        airportDto.setAirportName(airport.get().getAirportName());
        airportDto.setCity(airport.get().getCity());
        airportDto.setCountry(airport.get().getCountry());
        airportDto.setCountryAreaCode(airport.get().getCountryAreaCode());
        airportDto.setCountryCode(airport.get().getCountryCode());
        airportDto.setStatus(airport.get().getStatus());

        return airportDto;
    }

    @Override
    public List<AirportDto> findByParams(Boolean status, String airportName, String airportCode, String city,
                                         String country,String countryCode, String countryAreaCode) {

        String queryString = "Select e from Airport e where '1' = '1'";

        if (status != null) {
            queryString += " and e.status = :status";
        }
        if (!Utils.isNullOrEmpty(airportName)) {
            queryString += " and e.airportName = :airportName";
        }
        if (!Utils.isNullOrEmpty(airportCode)) {
            queryString += " and e.airportCode = :airportCode";
        }
        if (!Utils.isNullOrEmpty(city)) {
            queryString += " and e.city = :city";
        }
        if (!Utils.isNullOrEmpty(country)) {
            queryString += " and e.country = :country";
        }
        if (!Utils.isNullOrEmpty(countryCode)) {
            queryString += " and e.countryCode = :countryCode";
        }
        if (!Utils.isNullOrEmpty(countryAreaCode)) {
            queryString += " and e.countryAreaCode = :countryAreaCode";
        }
        Query query = entityManager.createQuery(queryString);

        if (status != null) {
            query.setParameter("status",status);
        }
        if (!Utils.isNullOrEmpty(airportName)) {
            query.setParameter("airportName",airportName);
        }
        if (!Utils.isNullOrEmpty(airportCode)) {
            query.setParameter("airportCode",airportCode);
        }
        if (!Utils.isNullOrEmpty(city)) {
            query.setParameter("city",city);
        }
        if (!Utils.isNullOrEmpty(country)) {
            query.setParameter("country",country);
        }
        if (!Utils.isNullOrEmpty(countryCode)) {
            query.setParameter("countryCode",countryCode);
        }
        if (!Utils.isNullOrEmpty(countryAreaCode)) {
            query.setParameter("countryAreaCode",countryAreaCode);
        }
        List<Airport> airports = (List<Airport>) query.getResultList();

        List<AirportDto> airportDtos = new ArrayList<>();
        airports.forEach(item ->{
            AirportDto airportDto = new AirportDto();
            airportDto.setAirportCode(item.getAirportCode());
            airportDto.setAirportName(item.getAirportName());
            airportDto.setCity(item.getCity());
            airportDto.setCountry(item.getCountry());
            airportDto.setCountryAreaCode(item.getCountryAreaCode());
            airportDto.setCountryCode(item.getCountryCode());
            airportDto.setStatus(item.getStatus());
            airportDtos.add(airportDto);
        });

        return airportDtos;
    }

    @Override
    public ResponseEntity<Object> createAirport(AirportDto airportDto) throws DataIntegrityViolationDbException {
        Airport airport = new Airport();
        airport.setAirportCode(airportDto.getAirportCode());
        airport.setAirportName(airportDto.getAirportName());
        airport.setCity(airportDto.getCity());
        airport.setCountry(airportDto.getCountry());
        airport.setCountryAreaCode(airportDto.getCountryAreaCode());
        airport.setCountryCode(airportDto.getCountryCode());
        airport.setStatus(true);

        Airport savedAirport;
        try{
            savedAirport = airportRepository.save(airport);
        } catch (DataIntegrityViolationException ex) {
            throw new DataIntegrityViolationDbException("Could not create airport in db");
        }
        URI location =
                ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                        .buildAndExpand(savedAirport.getId())
                        .toUri();

        return ResponseEntity.created(location).build();
    }
}
