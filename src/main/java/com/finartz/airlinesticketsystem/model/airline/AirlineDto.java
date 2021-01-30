package com.finartz.airlinesticketsystem.model.airline;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;

@Data
@ApiModel(description="All details about the airline.")
public class AirlineDto {

    @ApiModelProperty(notes="Id is unique property.")
    private Long id;

    @ApiModelProperty(notes="Status describes whether data is active or passive. Status should have false or true.")
    private Boolean status;

    @Size(min=1, max=255, message="Name should have at least 1 character and at most 255 characters")
    @ApiModelProperty(notes="Name describes company name.Name should have at least 1 character and at most 255 characters")
    @NotNull
    private String name;

    @Size(min=2, max=2, message="Iata code has to be 2 characters")
    @ApiModelProperty(notes="Iata code is one of the parameters for airline.Iata code has to be 2 characters")
    @NotNull
    private String iataCode;

    @ApiModelProperty(notes="Digit code is one of the parameters for airline.Digit code has to be 3 characters and contain digit characters")
    @NotNull
    @Min(value = 100,  message = "The value must be minimum 100")
    @Max(value = 999,  message = "The value must be maximum 999")
    private int digitCode;

    @Size(min=3, max=3, message="Icao code has to be 3 characters")
    @ApiModelProperty(notes="Icao code is one of the parameters for airline.Digit code has to be 3 characters")
    @NotNull
    private String icaoCode;

    @Size(min=1, max=255, message="Contact name should have at least 1 character and at most 255 characters")
    @ApiModelProperty(notes="Contact name describes contact name.Name should have at least 1 character and at most 255 characters")
    @NotNull
    private String contactName;

    @Size(max=255, message="Company address should have at most 255 characters")
    @ApiModelProperty(notes="Company address describes company main address.")
    private String companyAddress;
}
