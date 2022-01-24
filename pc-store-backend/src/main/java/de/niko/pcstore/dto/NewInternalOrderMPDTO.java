package de.niko.pcstore.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
@SuperBuilder
public class NewInternalOrderMPDTO {
    @JsonProperty
    @ApiModelProperty(value = "Client data", required = true)
    private NewClientDataMPDTO clientData;

    @JsonProperty
    @ApiModelProperty(value = "Personal computer", required = true)
    private NewPersonalComputerMPDTO personalComputer;

    @JsonProperty
    @ApiModelProperty(example = "true", value = "Data protection regulation agreement", required = true)
    private Boolean privacyPolicy;

    @JsonProperty("files")
    @ApiModelProperty("files")
    private List<NewInternalOrderFileMPDTO> internalOrderFiles;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @ApiModel
    @SuperBuilder
    public static class NewClientDataMPDTO {
        @JsonProperty
        @ApiModelProperty(example = "male", value = "Gender of the candidate", required = true)
        private Salutation salutation;

        @JsonProperty
        @ApiModelProperty(example = "John", value = "Name of the candidate", required = true)
        private String name;

        @JsonProperty
        @ApiModelProperty(example = "Smith", value = "Surname of the candidate", required = true)
        private String surname;

        @JsonProperty
        @ApiModelProperty(example = "Hauptstraße", value = "Name of the street", required = true)
        private String street;

        @JsonProperty
        @ApiModelProperty(example = "110", value = "Number of the house, where the candidate lives. Requires only when street specified", required = true)
        private String houseNumber;

        @JsonProperty
        @ApiModelProperty(example = "90459", value = "Zip code", required = true)
        private Integer zip;

        @JsonProperty
        @ApiModelProperty(example = "Nürnberg", value = "Name of the city", required = true)
        private String city;

        @JsonProperty
        @ApiModelProperty(example = "+49528252826", value = "Telephone of the candidate for communication")
        private String telephone;

        @JsonProperty
        @ApiModelProperty(example = "+49528252826", value = "Mobile phone of the candidate for communication")
        private String cellphone;

        @JsonProperty
        @ApiModelProperty(example = "example@test.de", value = "Email of the candidate for communication")
        private String email;

        public enum Salutation {
            MALE("male"), FEMALE("female");

            private final String salutation;

            Salutation(String salutation) {
                this.salutation = salutation;
            }

            public String getStatus() {
                return this.salutation;
            }

            @JsonCreator
            public static Salutation fromString(String status) {
                Optional<Salutation> statusOptional = Arrays.stream(Salutation.values()).filter(status1 -> status1.salutation.equalsIgnoreCase(status)).findFirst();

                return statusOptional.orElse(null);
            }

            @Override
            @JsonValue
            public String toString() {
                return String.valueOf(salutation);
            }
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @ApiModel
    @SuperBuilder
    public static class NewPersonalComputerMPDTO {
        @JsonProperty
        @ApiModelProperty(example = "MSI MAG Forge 100R", value = "Name of the case", required = true)
        private String computerCase;

        @JsonProperty
        @ApiModelProperty(example = "Gigabyte B550 Aorus Pro V2", value = "Name of the motherboard", required = true)
        private String motherboard;

        @JsonProperty
        @ApiModelProperty(example = "AMD Ryzen 7 5800X", value = "Name of the processor", required = true)
        private String processor;

        @JsonProperty
        @ApiModelProperty(example = "AMD Radeon RX 6700 XT 12 GB", value = "Name of the graphics card", required = true)
        private String graphicsCard;

        @JsonProperty
        @ApiModelProperty(example = "32GB Corsair Vengeance LPX DDR4-3000", value = "Name of the RAM", required = true)
        private String randomAccessMemory;

        @JsonProperty
        @ApiModelProperty(example = "250GB Samsung 870 EVO", value = "Name of the storage device", required = true)
        private String storageDevice;

        @JsonProperty
        @ApiModelProperty(example = "700W - be quiet! Pure power 11", value = "Name of the power supply unit", required = true)
        private String powerSupplyUnit;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @ApiModel
    @SuperBuilder
    public static class NewInternalOrderFileMPDTO {
        @JsonProperty("id")
        @ApiModelProperty(example = "d290f1ee-6c54-4b01-90e6-d701748f0851", value = "Unique UUID", required = true)
        private String id;

        @JsonProperty
        @ApiModelProperty(example = "somefile.pdf", required = true, value = "Name of the file")
        private String name;

        @JsonProperty
        @ApiModelProperty(example = "File is encrypted", value = "Notes with additional information")
        private String note;
    }
}
