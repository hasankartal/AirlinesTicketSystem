package com.finartz.airlinesticketsystem.domain.flight;

import com.finartz.airlinesticketsystem.domain.route.Route;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@ApiModel(description="All details about the flight.")
public class Flight {
    @Id
    @GeneratedValue
    private Long id;

    @ApiModelProperty(notes="Status describes whether data is active or passive. Status should have false or true.")
    @NotNull
    private Boolean status;

    @ManyToOne(fetch = FetchType.LAZY)
    @ApiModelProperty(notes="Route flight describes where from airplane flies to")
    @JoinColumn(name="route_flight_id", nullable=false)
    private Route routeFlight;

    @NotNull
    @ApiModelProperty(notes="Flight departure time")
    private Date flightDate;

    @NotNull
    @ApiModelProperty(notes="The number of passengers on the plane")
    private int passengerCount;

    @NotNull
    @ApiModelProperty(notes="Flight fee")
    private BigDecimal flightFee;

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

    public Route getRouteFlight() {
        return routeFlight;
    }

    public void setRouteFlight(Route routeFlight) {
        this.routeFlight = routeFlight;
    }

    public Date getFlightDate() {
        return flightDate;
    }

    public void setFlightDate(Date flightDate) {
        this.flightDate = flightDate;
    }

    public int getPassengerCount() {
        return passengerCount;
    }

    public void setPassengerCount(int passengerCount) {
        this.passengerCount = passengerCount;
    }

    public BigDecimal getFlightFee() {
        return flightFee;
    }

    public void setFlightFee(BigDecimal flightFee) {
        this.flightFee = flightFee;
    }
}
