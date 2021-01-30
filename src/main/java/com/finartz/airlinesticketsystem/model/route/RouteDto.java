package com.finartz.airlinesticketsystem.model.route;

import com.finartz.airlinesticketsystem.model.airport.AirportDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description="All details about the route.")
public class RouteDto {

    @ApiModelProperty(notes="Id is unique property.")
    private Long id;

    @ApiModelProperty(notes="Status describes whether data is active or passive. Status should have false or true.")
    private Boolean status;

    @ApiModelProperty(notes="Airport route from describes where airplane flies")
    private AirportDto airportRouteFrom;

    @ApiModelProperty(notes="Airport route to describes where airplane lands")
    private AirportDto airportRouteTo;
}
