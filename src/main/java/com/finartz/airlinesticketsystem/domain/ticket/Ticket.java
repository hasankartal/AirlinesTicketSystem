package com.finartz.airlinesticketsystem.domain.ticket;

import com.finartz.airlinesticketsystem.domain.flight.Flight;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@ApiModel(description="All details about the ticket.")
public class Ticket {
    @Id
    @GeneratedValue
    private Long id;

    @ApiModelProperty(notes="Status describes whether data is active or passive. Status should have false or true.")
    @NotNull
    private Boolean status;

    @ApiModelProperty(notes="Transaction status describes whether data is canceled or not.")
    @NotNull
    private String transactionStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @ApiModelProperty(notes="Flight ticket describes passenger flight")
    @JoinColumn(name="flight_ticket_id", nullable=false)
    private Flight flightTicket;

    @NotNull
    @ApiModelProperty(notes="Flight fee for this passenger")
    private BigDecimal flightFee;

    @NotNull
    @ApiModelProperty(notes="Card number what passenger bought ticket")
    private String cardNo;

    @NotNull
    @ApiModelProperty(notes="Customer name")
    private String customerName;

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

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public Flight getFlightTicket() {
        return flightTicket;
    }

    public void setFlightTicket(Flight flightTicket) {
        this.flightTicket = flightTicket;
    }

    public BigDecimal getFlightFee() {
        return flightFee;
    }

    public void setFlightFee(BigDecimal flightFee) {
        this.flightFee = flightFee;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
