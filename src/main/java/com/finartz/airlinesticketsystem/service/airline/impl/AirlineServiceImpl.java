package com.finartz.airlinesticketsystem.service.airline.impl;

import com.finartz.airlinesticketsystem.domain.airline.Airline;
import com.finartz.airlinesticketsystem.exception.DataIntegrityViolationDbException;
import com.finartz.airlinesticketsystem.exception.airline.AirlineNotFoundException;
import com.finartz.airlinesticketsystem.model.airline.AirlineDto;
import com.finartz.airlinesticketsystem.repository.airline.AirlineRepository;
import com.finartz.airlinesticketsystem.service.airline.AirlineService;
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
public class AirlineServiceImpl  implements AirlineService {

    private final AirlineRepository airlineRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<AirlineDto> findAll() {
        List<Airline> airlines = airlineRepository.findAll();
        List<AirlineDto> airlineDtos = new ArrayList<>();

        airlines.forEach(item ->{
            AirlineDto airlineDto = new AirlineDto();
            airlineDto.setCompanyAddress(item.getCompanyAddress());
            airlineDto.setContactName(item.getContactName());
            airlineDto.setDigitCode(item.getDigitCode());
            airlineDto.setIataCode(item.getIataCode());
            airlineDto.setIcaoCode(item.getIcaoCode());
            airlineDto.setId(item.getId());
            airlineDto.setName(item.getName());
            airlineDto.setStatus(item.getStatus());
            airlineDtos.add(airlineDto);
        });

        return airlineDtos;
    }

    @Override
    public AirlineDto findById(long id) throws AirlineNotFoundException{
        Optional<Airline> airline = airlineRepository.findById(id);
        if(!airline.isPresent()) {
            throw new AirlineNotFoundException("id-" + id);
        }

        AirlineDto airlineDto = new AirlineDto();
        airlineDto.setCompanyAddress(airline.get().getCompanyAddress());
        airlineDto.setContactName(airline.get().getContactName());
        airlineDto.setDigitCode(airline.get().getDigitCode());
        airlineDto.setIataCode(airline.get().getIataCode());
        airlineDto.setIcaoCode(airline.get().getIcaoCode());
        airlineDto.setId(airline.get().getId());
        airlineDto.setName(airline.get().getName());
        airlineDto.setStatus(airline.get().getStatus());

        return airlineDto;
    }

    @Override
    public List<AirlineDto> findByParams(Boolean status, String name, Integer digitCode, String iataCode) {
        String queryString = "Select e from Airline e where '1' = '1'";

        if (status != null) {
            queryString += " and e.status = :status";
        }
        if (!Utils.isNullOrEmpty(name)) {
            queryString += " and e.name = :name";
        }
        if (digitCode != null) {
            queryString += " and e.digitCode = :digitCode";
        }
        if (!Utils.isNullOrEmpty(iataCode)) {
            queryString += " and e.iataCode = :iataCode";
        }
        Query query = entityManager.createQuery(queryString);

        if (status != null) {
            query.setParameter("status",status);
        }
        if (!Utils.isNullOrEmpty(name)) {
            query.setParameter("name",name);
        }
        if (digitCode != null) {
            query.setParameter("digitCode",digitCode);
        }
        if (!Utils.isNullOrEmpty(iataCode)) {
            query.setParameter("iataCode",iataCode);
        }
        List<Airline> airlines = (List<Airline>) query.getResultList();

        List<AirlineDto> airlineDtos = new ArrayList<>();
        airlines.forEach(item ->{
            AirlineDto airlineDto = new AirlineDto();
            airlineDto.setCompanyAddress(item.getCompanyAddress());
            airlineDto.setContactName(item.getContactName());
            airlineDto.setDigitCode(item.getDigitCode());
            airlineDto.setIataCode(item.getIataCode());
            airlineDto.setIcaoCode(item.getIcaoCode());
            airlineDto.setId(item.getId());
            airlineDto.setName(item.getName());
            airlineDto.setStatus(item.getStatus());
            airlineDtos.add(airlineDto);
        });

        return airlineDtos;
    }

    @Override
    public ResponseEntity<Object> createAirline(AirlineDto airlineDto) throws DataIntegrityViolationDbException{
        Airline airline = new Airline();
        airline.setCompanyAddress(airlineDto.getCompanyAddress());
        airline.setContactName(airlineDto.getContactName());
        airline.setDigitCode(airlineDto.getDigitCode());
        airline.setIataCode(airlineDto.getIataCode());
        airline.setIcaoCode(airlineDto.getIcaoCode());
        airline.setName(airlineDto.getName());
        airline.setStatus(true);

        Airline savedAirline;
        try{
            savedAirline = airlineRepository.save(airline);
        } catch (DataIntegrityViolationException ex) {
            throw new DataIntegrityViolationDbException("Could not create airline in db");
        }

        URI location =
                ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                        .buildAndExpand(savedAirline.getId())
                        .toUri();

        return ResponseEntity.created(location).build();
    }

}
