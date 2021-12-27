package de.niko.pcstore.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
@SuperBuilder
public class ClientDataDTO extends DefaultDTOObject {
    @JsonProperty("salutation")
    @ApiModelProperty(example = "herr", value = "Gender of the candidate (global variables -> salutation)", required = true)
    private String salutationId;

    @JsonProperty("name")
    @ApiModelProperty(example = "John", value = "Name of the candidate", required = true)
    private String name;

    @JsonProperty("surname")
    @ApiModelProperty(example = "Smith", value = "Surname of the candidate", required = true)
    private String surname;

    @JsonProperty("street")
    @ApiModelProperty(example = "Hauptstraße", value = "Name of the street", required = true)
    private String street;

    @JsonProperty("houseNumber")
    @ApiModelProperty(example = "110", value = "Number of the house, where the candidate lives. Requires only when street specified", required = true)
    private String houseNumber;

    @JsonProperty("zip")
    @ApiModelProperty(example = "90459", value = "Zip code", required = true)
    private Integer zip;

    @JsonProperty("city")
    @ApiModelProperty(example = "Nürnberg", value = "Name of the city", required = true)
    private String city;

    @JsonProperty("telephone")
    @ApiModelProperty(example = "+49528252826", value = "Telephone of the candidate for communication")
    private String telephone;

    @JsonProperty("cellphone")
    @ApiModelProperty(example = "+49528252826", value = "Mobile phone of the candidate for communication")
    private String cellphone;

    @JsonProperty("email")
    @ApiModelProperty(example = "example@test.de", value = "Email of the candidate for communication")
    private String email;
}


