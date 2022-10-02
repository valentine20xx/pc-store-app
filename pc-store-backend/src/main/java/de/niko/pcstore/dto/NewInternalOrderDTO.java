package de.niko.pcstore.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Arrays;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class NewInternalOrderDTO {
    @JsonProperty
    @Schema(description = "Client data", required = true)
    private NewClientDataDTO clientData;

    @JsonProperty
    @Schema(description = "Personal computer", required = true)
    private NewPersonalComputerDTO personalComputer;

    @JsonProperty
    @Schema(description = "Data protection regulation agreement", example = "true", required = true)
    private Boolean privacyPolicy;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @SuperBuilder
    public static class NewClientDataDTO {
        @JsonProperty
        @Schema(description = "Gender of the customer", example = "male", required = true)
        private Salutation salutation;

        @JsonProperty
        @Schema(description = "Name of the customer", example = "John", required = true)
        private String name;

        @JsonProperty
        @Schema(description = "Surname of the customer", example = "Smith", required = true)
        private String surname;

        @JsonProperty
        @Schema(description = "Name of the street", example = "Hauptstraße", required = true)
        private String street;

        @JsonProperty
        @Schema(description = "Number of the house, where the customer lives. Requires only when street specified", example = "110", required = true)
        private String houseNumber;

        @JsonProperty
        @Schema(description = "Zip code", example = "90459", required = true)
        private Integer zip;

        @JsonProperty
        @Schema(description = "Name of the city", example = "Nürnberg", required = true)
        private String city;

        @JsonProperty
        @Schema(description = "Telephone of the customer for communication", example = "+49528252826")
        private String telephone;

        @JsonProperty
        @Schema(description = "Mobile phone of the customer for communication", example = "+49528252826")
        private String cellphone;

        @JsonProperty
        @Schema(description = "Email of the customer for communication", example = "example@test.de")
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
    @SuperBuilder
    public static class NewPersonalComputerDTO {
        @JsonProperty
        @Schema(description = "Name of the case", example = "MSI MAG Forge 100R", required = true)
        private String computerCase;

        @JsonProperty
        @Schema(description = "Name of the motherboard", example = "Gigabyte B550 Aorus Pro V2", required = true)
        private String motherboard;

        @JsonProperty
        @Schema(description = "Name of the processor", example = "AMD Ryzen 7 5800X", required = true)
        private String processor;

        @JsonProperty
        @Schema(description = "Name of the graphics card", example = "AMD Radeon RX 6700 XT 12 GB", required = true)
        private String graphicsCard;

        @JsonProperty
        @Schema(description = "Name of the RAM", example = "32GB Corsair Vengeance LPX DDR4-3000", required = true)
        private String randomAccessMemory;

        @JsonProperty
        @Schema(description = "Name of the storage device", example = "250GB Samsung 870 EVO", required = true)
        private String storageDevice;

        @JsonProperty
        @Schema(description = "Name of the power supply unit", example = "700W - be quiet! Pure power 11", required = true)
        private String powerSupplyUnit;
    }
}
