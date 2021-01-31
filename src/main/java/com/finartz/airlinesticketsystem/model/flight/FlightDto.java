package com.finartz.airlinesticketsystem.model.flight;

import com.finartz.airlinesticketsystem.model.route.RouteDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel(description="All details about the flight.")
public class FlightDto {
    @ApiModelProperty(notes="Id is unique property.")
    private Long id;

    @ApiModelProperty(notes="Status describes whether data is active or passive. Status should have false or true.")
    private Boolean status;

    @ApiModelProperty(notes="Airport route from describes where airplane flies")
    private RouteDto routeDto;

    @NotNull
    @Future
    @ApiModelProperty(notes="Flight departure time")
    private Date flightDate;

    @NotNull
    @Min(value = 1,  message = "The value must be minimum 1")
    @Max(value = 9999,  message = "The value must be maximum 9999")
    @ApiModelProperty(notes="Starting The number of passengers on the plane")
    private int passengerCount;

    @NotNull
    @ApiModelProperty(notes="Starting flight fee")
    private BigDecimal flightFee;

    @ApiModelProperty(notes="Present the number of passengers on the plane after increasement quato")
    private int passengerCountPresent;

    @ApiModelProperty(notes="Present flight fee after increasement quota")
    private BigDecimal flightFeePresent;
}
