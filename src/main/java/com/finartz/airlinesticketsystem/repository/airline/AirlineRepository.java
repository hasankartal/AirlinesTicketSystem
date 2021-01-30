package com.finartz.airlinesticketsystem.repository.airline;

import com.finartz.airlinesticketsystem.domain.airline.Airline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirlineRepository extends JpaRepository<Airline, Long> {
 //   @Query("SELECT u FROM Airline u WHERE u.status = :status  and u.name = :name ")
 //   List<Airline> findByParams(@Param("status") Boolean status,@Param("name") String name);
}
