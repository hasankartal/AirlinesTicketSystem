package com.finartz.airlinesticketsystem.domain.route;

import com.finartz.airlinesticketsystem.domain.airport.Airport;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@ApiModel(description="All details about the route.")
@Table(uniqueConstraints={
        @UniqueConstraint(columnNames = {"airport_route_from_id", "airport_route_to_id"})
})
public class Route {
    @Id
    @GeneratedValue
    private Long id;

    @ApiModelProperty(notes="Status describes whether data is active or passive. Status should have false or true.")
    @NotNull
    private Boolean status;

    @ManyToOne(fetch = FetchType.LAZY)
    @ApiModelProperty(notes="Airport route from describes where airplane flies")
    @JoinColumn(name="airport_route_from_id", nullable=false)
    private Airport airportRouteFrom;

    @ManyToOne(fetch = FetchType.LAZY)
    @ApiModelProperty(notes="Airport route to describes where airplane lands")
    @JoinColumn(name="airport_route_to_id", nullable=false)
    private Airport airportRouteTo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Airport getAirportRouteFrom() {
        return airportRouteFrom;
    }

    public void setAirportRouteFrom(Airport airportRouteFrom) {
        this.airportRouteFrom = airportRouteFrom;
    }

    public Airport getAirportRouteTo() {
        return airportRouteTo;
    }

    public void setAirportRouteTo(Airport airportRouteTo) {
        this.airportRouteTo = airportRouteTo;
    }
}
