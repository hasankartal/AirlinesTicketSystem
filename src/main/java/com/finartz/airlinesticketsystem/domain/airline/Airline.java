package com.finartz.airlinesticketsystem.domain.airline;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Entity
@EqualsAndHashCode(of = "id")
@ToString
@ApiModel(description="All details about the airline.")
public class Airline {

    @Id
    @GeneratedValue
    private Long id;

    @ApiModelProperty(notes="Status describes whether data is active or passive. Status should have false or true.")
    @NotNull
    private Boolean status;

    @Size(max=255, message="Name should have at least 1 character and at most 255 characters")
    @NotNull
    private String name;

    @Size(min=2, max=2, message="Iata code has to be 2 characters")
    @NotNull
    @Column(unique=true)
    private String iataCode;

    @NotNull
    @Column(unique=true)
    private int digitCode;

    @Size(min=3, max=3, message="Icao code has to be 3 characters")
    @NotNull
    @Column(unique=true)
    private String icaoCode;

    @Size(max=255, message="Contact name should have at least 1 character and at most 255 characters")
    @NotNull
    private String contactName;

    @Size(max=255, message="Company address should have at least 1 character and at most 255 characters")
    private String companyAddress;
}
