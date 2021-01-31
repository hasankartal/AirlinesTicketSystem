package com.finartz.airlinesticketsystem.model.ticket;

import com.finartz.airlinesticketsystem.domain.flight.Flight;
import com.finartz.airlinesticketsystem.model.flight.FlightDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@ApiModel(description="All details about the ticket.")
public class TicketDto {
    @ApiModelProperty(notes="Id is unique property.")
    private Long id;

    @ApiModelProperty(notes="Status describes whether data is active or passive. Status should have false or true.")
    private Boolean status;

    @ApiModelProperty(notes="Transaction status describes whether data is canceled or not.")
    private String transactionStatus;

    @ApiModelProperty(notes="Flight ticket describes passenger flight")
    private FlightDto flightDto;

    @ApiModelProperty(notes="Flight fee for this passenger")
    private BigDecimal flightFee;

    @NotNull
    @ApiModelProperty(notes="Card number what passenger bought ticket")
    private String cardNo;

    @NotNull
    @ApiModelProperty(notes="Customer name")
    private String customerName;
}
