package com.finartz.airlinesticketsystem.service.route;

import com.finartz.airlinesticketsystem.model.route.RouteDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RouteService {

    List<RouteDto> findAll();
    RouteDto findById(long id);
    List<RouteDto> findByParams(Boolean status, Long airportRouteFrom, Long airportRouteTo);
    ResponseEntity<Object> createRoute(RouteDto routeDto);
}
