package com.finartz.airlinesticketsystem.model.airport;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Size;

@Data
@ApiModel(description="All details about the airport.")
public class AirportDto {

    @ApiModelProperty(notes="Id is unique property.")
    private Long id;

    @ApiModelProperty(notes="Status describes whether data is active or passive. Status should have false or true.")
    private Boolean status;

    @Size(min=1, max=255, message="Airport name should have at least 1 character and at most 255 characters")
    @ApiModelProperty(notes="Airport name describes airport name.Name should have at least 1 character and at most 255 characters")
    private String airportName;

    @Size(min=3, max=3, message="Airport code has to be 3 characters")
    @ApiModelProperty(notes="Airport code is distinguished parameters for airport.Airport code has to be 3 characters")
    private String airportCode;

    @Size(min=1, max=255, message="City should have at least 1 character and at most 255 characters")
    @ApiModelProperty(notes="City describes where airport is.City should have at least 1 character and at most 255 characters")
    private String city;

    @Size(min=1, max=255, message="Country should have at least 1 character and at most 255 characters")
    @ApiModelProperty(notes="Country describes airport where it belongs.Country should have at least 1 character and at most 255 characters")
    private String country;

    @Size(min=2, max=2, message="Country code has to be 2 characters")
    @ApiModelProperty(notes="Country code is distinguished parameters for country.Country code should have at least 1 character and at most 2 characters")
    private String countryCode;

    @Size(min=2, max=3, message="Country area code should have at least 2 characters and at most 3 characters")
    @ApiModelProperty(notes="Country area code is distinguished parameters for country.Country area code should have at least 2 characters and at most 3 characters")
    private String countryAreaCode;

}
