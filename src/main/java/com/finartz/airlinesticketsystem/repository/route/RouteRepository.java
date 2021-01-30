package com.finartz.airlinesticketsystem.repository.route;

import com.finartz.airlinesticketsystem.domain.route.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {
}
