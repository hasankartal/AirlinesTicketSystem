package com.finartz.airlinesticketsystem.service.flight.impl;

import com.finartz.airlinesticketsystem.domain.airport.Airport;
import com.finartz.airlinesticketsystem.domain.flight.Flight;
import com.finartz.airlinesticketsystem.domain.route.Route;
import com.finartz.airlinesticketsystem.exception.DataIntegrityViolationDbException;
import com.finartz.airlinesticketsystem.exception.flight.FlightNotFoundException;
import com.finartz.airlinesticketsystem.model.airport.AirportDto;
import com.finartz.airlinesticketsystem.model.flight.FlightDto;
import com.finartz.airlinesticketsystem.model.route.RouteDto;
import com.finartz.airlinesticketsystem.repository.flight.FlightRepository;
import com.finartz.airlinesticketsystem.service.flight.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<FlightDto> findAll() {
        List<Flight> flights = flightRepository.findAll();
        List<FlightDto> flightDtos = new ArrayList<>();

        flights.forEach(item ->{
            FlightDto flightDto = new FlightDto();

            RouteDto routeDto = new RouteDto();
            routeDto.setId(item.getRouteFlight().getId());
            routeDto.setStatus(item.getRouteFlight().getStatus());

            AirportDto airportRouteFrom = new AirportDto();
            airportRouteFrom.setId(item.getRouteFlight().getAirportRouteFrom().getId());
            airportRouteFrom.setAirportCode(item.getRouteFlight().getAirportRouteFrom().getAirportCode());
            airportRouteFrom.setAirportName(item.getRouteFlight().getAirportRouteFrom().getAirportName());
            airportRouteFrom.setCity(item.getRouteFlight().getAirportRouteFrom().getCity());
            airportRouteFrom.setCountry(item.getRouteFlight().getAirportRouteFrom().getCountry());
            airportRouteFrom.setCountryAreaCode(item.getRouteFlight().getAirportRouteFrom().getCountryAreaCode());
            airportRouteFrom.setCountryCode(item.getRouteFlight().getAirportRouteFrom().getCountryCode());
            airportRouteFrom.setStatus(item.getRouteFlight().getAirportRouteFrom().getStatus());
            routeDto.setAirportRouteFrom(airportRouteFrom);

            AirportDto airportRouteTo = new AirportDto();
            airportRouteTo.setId(item.getRouteFlight().getAirportRouteTo().getId());
            airportRouteTo.setAirportCode(item.getRouteFlight().getAirportRouteTo().getAirportCode());
            airportRouteTo.setAirportName(item.getRouteFlight().getAirportRouteTo().getAirportName());
            airportRouteTo.setCity(item.getRouteFlight().getAirportRouteTo().getCity());
            airportRouteTo.setCountry(item.getRouteFlight().getAirportRouteTo().getCountry());
            airportRouteTo.setCountryAreaCode(item.getRouteFlight().getAirportRouteTo().getCountryAreaCode());
            airportRouteTo.setCountryCode(item.getRouteFlight().getAirportRouteTo().getCountryCode());
            airportRouteTo.setStatus(item.getRouteFlight().getAirportRouteTo().getStatus());
            routeDto.setAirportRouteTo(airportRouteTo);

            flightDto.setRouteDto(routeDto);
            flightDto.setId(item.getId());
            flightDto.setFlightDate(item.getFlightDate());
            flightDto.setFlightFee(item.getFlightFee());
            flightDto.setPassengerCount(item.getPassengerCount());
            flightDto.setStatus(item.getStatus());
            flightDtos.add(flightDto);
        });

        return flightDtos;
    }

    @Override
    public FlightDto findById(long id) throws FlightNotFoundException {
        Optional<Flight> flight = flightRepository.findById(id);
        if(!flight.isPresent()) {
            throw new FlightNotFoundException("id-" + id);
        }

        FlightDto flightDto = new FlightDto();
        flightDto.setId(flight.get().getId());
        flightDto.setStatus(flight.get().getStatus());

        RouteDto routeDto = new RouteDto();
        routeDto.setId(flight.get().getRouteFlight().getId());

        AirportDto airportRouteFrom = new AirportDto();
        airportRouteFrom.setId(flight.get().getRouteFlight().getAirportRouteFrom().getId());
        airportRouteFrom.setAirportCode(flight.get().getRouteFlight().getAirportRouteFrom().getAirportCode());
        airportRouteFrom.setAirportName(flight.get().getRouteFlight().getAirportRouteFrom().getAirportName());
        airportRouteFrom.setCity(flight.get().getRouteFlight().getAirportRouteFrom().getCity());
        airportRouteFrom.setCountry(flight.get().getRouteFlight().getAirportRouteFrom().getCountry());
        airportRouteFrom.setCountryAreaCode(flight.get().getRouteFlight().getAirportRouteFrom().getCountryAreaCode());
        airportRouteFrom.setCountryCode(flight.get().getRouteFlight().getAirportRouteFrom().getCountryCode());
        airportRouteFrom.setStatus(flight.get().getRouteFlight().getAirportRouteFrom().getStatus());
        routeDto.setAirportRouteFrom(airportRouteFrom);

        AirportDto airportRouteTo = new AirportDto();
        airportRouteTo.setId(flight.get().getRouteFlight().getAirportRouteTo().getId());
        airportRouteTo.setAirportCode(flight.get().getRouteFlight().getAirportRouteTo().getAirportCode());
        airportRouteTo.setAirportName(flight.get().getRouteFlight().getAirportRouteTo().getAirportName());
        airportRouteTo.setCity(flight.get().getRouteFlight().getAirportRouteTo().getCity());
        airportRouteTo.setCountry(flight.get().getRouteFlight().getAirportRouteTo().getCountry());
        airportRouteTo.setCountryAreaCode(flight.get().getRouteFlight().getAirportRouteTo().getCountryAreaCode());
        airportRouteTo.setCountryCode(flight.get().getRouteFlight().getAirportRouteTo().getCountryCode());
        airportRouteTo.setStatus(flight.get().getRouteFlight().getAirportRouteTo().getStatus());
        routeDto.setAirportRouteTo(airportRouteTo);
        routeDto.setStatus(flight.get().getRouteFlight().getStatus());

        flightDto.setRouteDto(routeDto);
        flightDto.setFlightDate(flight.get().getFlightDate());
        flightDto.setFlightFee(flight.get().getFlightFee());
        flightDto.setPassengerCount(flight.get().getPassengerCount());
        flightDto.setStatus(flight.get().getStatus());

        return flightDto;
    }

    @Override
    public List<FlightDto> findByParams(Boolean status, BigDecimal flightFee, Integer passengerCount, Date flightDate, Long routeFlight) {
        String queryString = "Select e from Flight e where '1' = '1'";

        if (status != null) {
            queryString += " and e.status = :status";
        }
        if (flightFee != null) {
            queryString += " and e.flightFee = :flightFee";
        }
        if (passengerCount != null) {
            queryString += " and e.passengerCount = :passengerCount";
        }
        if (flightDate != null) {
            queryString += " and e.flightDate = :flightDate";
        }
        if(routeFlight != null) {
            queryString += " and e.routeFlight = :routeFlight";
        }
        Query query = entityManager.createQuery(queryString);

        if (status != null) {
            query.setParameter("status", status);
        }
        if (flightFee != null) {
            query.setParameter("flightFee", flightFee);
        }
        if (passengerCount != null) {
            query.setParameter("passengerCount", passengerCount);
        }
        if (flightDate != null) {
            query.setParameter("flightDate", flightDate);
        }
        if (routeFlight != null) {
            Route route = new Route();
            route.setId(routeFlight.longValue());
            query.setParameter("routeFlight",route);
        }

        List<Flight> flights = (List<Flight>) query.getResultList();

        List<FlightDto> flightDtos = new ArrayList<>();
        flights.forEach(item ->{
            FlightDto flightDto = new FlightDto();
            flightDto.setId(item.getId());

            //TO DO RouteDto will be added
            flightDto.setStatus(item.getStatus());
            flightDto.setPassengerCount(item.getPassengerCount());
            flightDto.setFlightFee(item.getFlightFee());
            flightDto.setFlightDate(item.getFlightDate());


            flightDtos.add(flightDto);
        });

        return flightDtos;
    }

    @Override
    public ResponseEntity<Object> createFlight(FlightDto flightDto) throws DataIntegrityViolationDbException {
        Flight flight = new Flight();
        flight.setFlightDate(flightDto.getFlightDate());
        flight.setFlightFee(flightDto.getFlightFee());
        flight.setPassengerCount(flightDto.getPassengerCount());

        Route route = new Route();
        route.setId(flightDto.getRouteDto().getId());
        flight.setRouteFlight(route);

        flight.setStatus(true);

        Flight savedFlight;
        try{
            savedFlight = flightRepository.save(flight);
        } catch (DataIntegrityViolationException ex) {
            throw new DataIntegrityViolationDbException("Could not create flight in db");
        }

        URI location =
                ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                        .buildAndExpand(savedFlight.getId())
                        .toUri();

        return ResponseEntity.created(location).build();
    }
}
