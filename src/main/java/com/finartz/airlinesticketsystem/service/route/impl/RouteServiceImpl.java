package com.finartz.airlinesticketsystem.service.route.impl;

import com.finartz.airlinesticketsystem.domain.airport.Airport;
import com.finartz.airlinesticketsystem.domain.route.Route;
import com.finartz.airlinesticketsystem.exception.DataIntegrityViolationDbException;
import com.finartz.airlinesticketsystem.exception.route.RouteNotFoundException;
import com.finartz.airlinesticketsystem.model.airport.AirportDto;
import com.finartz.airlinesticketsystem.model.route.RouteDto;
import com.finartz.airlinesticketsystem.repository.route.RouteRepository;
import com.finartz.airlinesticketsystem.service.route.RouteService;
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
public class RouteServiceImpl implements RouteService {

    private final RouteRepository routeRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<RouteDto> findAll() {
        List<Route> routes = routeRepository.findAll();
        List<RouteDto> routeDtos = new ArrayList<>();

        routes.forEach(item ->{
            RouteDto routeDto = new RouteDto();
            routeDto.setId(item.getId());

            AirportDto airportRouteFrom = new AirportDto();
            airportRouteFrom.setId(item.getAirportRouteFrom().getId());
            airportRouteFrom.setAirportCode(item.getAirportRouteFrom().getAirportCode());
            airportRouteFrom.setAirportName(item.getAirportRouteFrom().getAirportName());
            airportRouteFrom.setCity(item.getAirportRouteFrom().getCity());
            airportRouteFrom.setCountry(item.getAirportRouteFrom().getCountry());
            airportRouteFrom.setCountryAreaCode(item.getAirportRouteFrom().getCountryAreaCode());
            airportRouteFrom.setCountryCode(item.getAirportRouteFrom().getCountryCode());
            airportRouteFrom.setStatus(item.getAirportRouteFrom().getStatus());
            routeDto.setAirportRouteFrom(airportRouteFrom);

            AirportDto airportRouteTo = new AirportDto();
            airportRouteTo.setId(item.getAirportRouteTo().getId());
            airportRouteTo.setAirportCode(item.getAirportRouteTo().getAirportCode());
            airportRouteTo.setAirportName(item.getAirportRouteTo().getAirportName());
            airportRouteTo.setCity(item.getAirportRouteTo().getCity());
            airportRouteTo.setCountry(item.getAirportRouteTo().getCountry());
            airportRouteTo.setCountryAreaCode(item.getAirportRouteTo().getCountryAreaCode());
            airportRouteTo.setCountryCode(item.getAirportRouteTo().getCountryCode());
            airportRouteTo.setStatus(item.getAirportRouteTo().getStatus());
            routeDto.setAirportRouteTo(airportRouteTo);

            routeDto.setStatus(item.getStatus());

            routeDtos.add(routeDto);
        });

        return routeDtos;
    }

    @Override
    public RouteDto findById(long id) {
        Optional<Route> route = routeRepository.findById(id);
        if(!route.isPresent()) {
            throw new RouteNotFoundException("id-" + id);
        }

        RouteDto routeDto = new RouteDto();

        AirportDto airportRouteFrom = new AirportDto();
        airportRouteFrom.setId(route.get().getAirportRouteFrom().getId());
        airportRouteFrom.setAirportCode(route.get().getAirportRouteFrom().getAirportCode());
        airportRouteFrom.setAirportName(route.get().getAirportRouteFrom().getAirportName());
        airportRouteFrom.setCity(route.get().getAirportRouteFrom().getCity());
        airportRouteFrom.setCountry(route.get().getAirportRouteFrom().getCountry());
        airportRouteFrom.setCountryAreaCode(route.get().getAirportRouteFrom().getCountryAreaCode());
        airportRouteFrom.setCountryCode(route.get().getAirportRouteFrom().getCountryCode());
        airportRouteFrom.setStatus(route.get().getAirportRouteFrom().getStatus());
        routeDto.setAirportRouteFrom(airportRouteFrom);

        AirportDto airportRouteTo = new AirportDto();
        airportRouteTo.setId(route.get().getAirportRouteTo().getId());
        airportRouteTo.setAirportCode(route.get().getAirportRouteTo().getAirportCode());
        airportRouteTo.setAirportName(route.get().getAirportRouteTo().getAirportName());
        airportRouteTo.setCity(route.get().getAirportRouteTo().getCity());
        airportRouteTo.setCountry(route.get().getAirportRouteTo().getCountry());
        airportRouteTo.setCountryAreaCode(route.get().getAirportRouteTo().getCountryAreaCode());
        airportRouteTo.setCountryCode(route.get().getAirportRouteTo().getCountryCode());
        airportRouteTo.setStatus(route.get().getAirportRouteTo().getStatus());
        routeDto.setAirportRouteTo(airportRouteTo);

        routeDto.setStatus(route.get().getStatus());
        return routeDto;
    }

    @Override
    public List<RouteDto> findByParams(Boolean status, Long airportRouteFrom, Long airportRouteTo) {
        String queryString = "Select e from Route e where '1' = '1'";

        if (status != null) {
            queryString += " and e.status = :status";
        }
        if (airportRouteFrom != null) {
            queryString += " and e.airportRouteFrom = :airportRouteFrom";
        }
        if (airportRouteTo != null) {
            queryString += " and e.airportRouteTo = :airportRouteTo";
        }

        Query query = entityManager.createQuery(queryString);

        if (status != null) {
            query.setParameter("status",status);
        }
        if (airportRouteFrom != null) {
            Airport airport = new Airport();
            airport.setId(airportRouteFrom.longValue());
            query.setParameter("airportRouteFrom",airport);
        }
        if (airportRouteTo != null) {
            Airport airport = new Airport();
            airport.setId(airportRouteTo.longValue());
            query.setParameter("airportRouteTo", airport);
        }
        List<Route> routes = (List<Route>) query.getResultList();

        List<RouteDto> routeDtos = new ArrayList<>();
        routes.forEach(item ->{
            RouteDto routeDto = new RouteDto();
            routeDto.setId(item.getId());

            AirportDto airportRouteFromDto = new AirportDto();
            airportRouteFromDto.setId(item.getAirportRouteFrom().getId());
            airportRouteFromDto.setAirportCode(item.getAirportRouteFrom().getAirportCode());
            airportRouteFromDto.setAirportName(item.getAirportRouteFrom().getAirportName());
            airportRouteFromDto.setCity(item.getAirportRouteFrom().getCity());
            airportRouteFromDto.setCountry(item.getAirportRouteFrom().getCountry());
            airportRouteFromDto.setCountryAreaCode(item.getAirportRouteFrom().getCountryAreaCode());
            airportRouteFromDto.setCountryCode(item.getAirportRouteFrom().getCountryCode());
            airportRouteFromDto.setStatus(item.getAirportRouteFrom().getStatus());
            routeDto.setAirportRouteFrom(airportRouteFromDto);

            AirportDto airportRouteToDto = new AirportDto();
            airportRouteToDto.setId(item.getAirportRouteTo().getId());
            airportRouteToDto.setAirportCode(item.getAirportRouteTo().getAirportCode());
            airportRouteToDto.setAirportName(item.getAirportRouteTo().getAirportName());
            airportRouteToDto.setCity(item.getAirportRouteTo().getCity());
            airportRouteToDto.setCountry(item.getAirportRouteTo().getCountry());
            airportRouteToDto.setCountryAreaCode(item.getAirportRouteTo().getCountryAreaCode());
            airportRouteToDto.setCountryCode(item.getAirportRouteTo().getCountryCode());
            airportRouteToDto.setStatus(item.getAirportRouteTo().getStatus());
            routeDto.setAirportRouteTo(airportRouteToDto);

            routeDto.setStatus(item.getStatus());
            routeDtos.add(routeDto);
        });

        return routeDtos;
    }

    @Override
    public ResponseEntity<Object> createRoute(RouteDto routeDto) throws DataIntegrityViolationDbException {
        Route route = new Route();
        Airport airportRouteFrom = new Airport();
        airportRouteFrom.setId(routeDto.getAirportRouteFrom().getId());
        airportRouteFrom.setAirportCode(routeDto.getAirportRouteFrom().getAirportCode());
        route.setAirportRouteFrom(airportRouteFrom);

        Airport airportRouteTo = new Airport();
        airportRouteTo.setId(routeDto.getAirportRouteTo().getId());
        airportRouteTo.setAirportCode(routeDto.getAirportRouteTo().getAirportCode());
        route.setAirportRouteTo(airportRouteTo);

        route.setStatus(true);

        Route savedRoute;
        try{
            savedRoute = routeRepository.save(route);
        } catch (DataIntegrityViolationException ex) {
            throw new DataIntegrityViolationDbException("Could not create route in db");
        }
        URI location =
                ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                        .buildAndExpand(savedRoute.getId())
                        .toUri();

        return ResponseEntity.created(location).build();
    }
}
