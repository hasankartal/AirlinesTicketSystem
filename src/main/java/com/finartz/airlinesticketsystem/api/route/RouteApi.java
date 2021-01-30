package com.finartz.airlinesticketsystem.api.route;

import com.finartz.airlinesticketsystem.exception.DataIntegrityViolationDbException;
import com.finartz.airlinesticketsystem.exception.route.RouteNotFoundException;
import com.finartz.airlinesticketsystem.model.route.RouteDto;
import com.finartz.airlinesticketsystem.service.route.impl.RouteServiceImpl;
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
public class RouteApi {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RouteServiceImpl service;

    @GetMapping("/routes")
    public List<RouteDto> retrieveAllRoutes() {
        logger.info("Route service started");
        return service.findAll();
    }

    @GetMapping("/routes/{id}")
    public RouteDto retrieveRoute(@PathVariable int id) {
        logger.info("RetrieveRoute with id param service started");
        RouteDto route;
        try {
            route = service.findById(id);
            return route;
        } catch (RouteNotFoundException ex) {
            logger.error("Error in retrieveRoute service : {" + ex + " }");
            throw new RouteNotFoundException("id-" + id);
        }
    }

    @GetMapping("/routes/search")
    public List<RouteDto> retrieveRoutes(@RequestParam(required = false) Boolean status,
                                         @RequestParam(required = false) Long airportRouteFrom,
                                         @RequestParam(required = false) Long airportRouteTo) {
        logger.info("RetriveRoutes search service started");
        List<RouteDto> routes = service.findByParams(status, airportRouteFrom, airportRouteTo);
        return routes;
    }

    @PostMapping("/route")
    public ResponseEntity<Object> createRoute(@Valid @RequestBody RouteDto routeDto) {
        logger.info("createRoute service started");

        ResponseEntity<Object> savedRoute;
        try {
            savedRoute = service.createRoute(routeDto);
            return savedRoute;
        } catch (DataIntegrityViolationDbException ex) {
            logger.error("Error in createRoute service Could not create route: {" + ex + " }");
            throw new DataIntegrityViolationDbException("Could not create route");
        }
    }
}
