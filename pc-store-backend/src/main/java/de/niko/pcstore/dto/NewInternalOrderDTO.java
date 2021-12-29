package de.niko.pcstore.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
@SuperBuilder
public class NewInternalOrderDTO {
    @JsonProperty("clientData")
    @ApiModelProperty(value = "Client data", required = true)
    private NewClientDataDTO clientData;

    @JsonProperty("personalComputer")
    @ApiModelProperty(value = "Personal computer", required = true)
    private NewPersonalComputerDTO personalComputer;

    @JsonProperty("privacyPolicy")
    @ApiModelProperty(example = "true", value = "Data protection regulation agreement", required = true)
    private Boolean privacyPolicy;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @ApiModel
    @SuperBuilder
    public static class NewClientDataDTO {
        @JsonProperty("salutation")
        @ApiModelProperty(example = "salutations-herr", value = "Gender of the candidate (global variables -> salutation)", required = true)
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


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @ApiModel
    @SuperBuilder
    public static class NewPersonalComputerDTO {
        @JsonProperty("computerCase")
        @ApiModelProperty(example = "MSI MAG Forge 100R", value = "Name of the case", required = true)
        private String computerCase;

        @JsonProperty("motherboard")
        @ApiModelProperty(example = "Gigabyte B550 Aorus Pro V2", value = "Name of the motherboard", required = true)
        private String motherboard;

        @JsonProperty("processor")
        @ApiModelProperty(example = "AMD Ryzen 7 5800X", value = "Name of the processor", required = true)
        private String processor;

        @JsonProperty("graphicsCard")
        @ApiModelProperty(example = "AMD Radeon RX 6700 XT 12 GB", value = "Name of the graphics card", required = true)
        private String graphicsCard;

        @JsonProperty("randomAccessMemory")
        @ApiModelProperty(example = "32GB Corsair Vengeance LPX DDR4-3000", value = "Name of the RAM", required = true)
        private String randomAccessMemory;

        @JsonProperty("storageDevice")
        @ApiModelProperty(example = "250GB Samsung 870 EVO", value = "Name of the storage device", required = true)
        private String storageDevice;

        @JsonProperty("powerSupplyUnit")
        @ApiModelProperty(example = "700W - be quiet! Pure power 11", value = "Name of the power supply unit", required = true)
        private String powerSupplyUnit;
    }


}
