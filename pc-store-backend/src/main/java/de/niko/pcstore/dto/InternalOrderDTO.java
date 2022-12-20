package de.niko.pcstore.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class InternalOrderDTO extends DefaultDTOObject {
    @JsonProperty
    @Schema(description = "Client data", requiredMode = Schema.RequiredMode.REQUIRED)
    private ClientDataDTO clientData;

    @JsonProperty
    @Schema(description = "Personal computer", requiredMode = Schema.RequiredMode.REQUIRED)
    private PersonalComputerDTO personalComputer;

    @JsonProperty
    @Schema(description = "Data protection regulation agreement", example = "true", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean privacyPolicy;

    @JsonProperty
    @Schema(description = "files", requiredMode = Schema.RequiredMode.REQUIRED, name = "files")
    private List<InternalOrderFileDTO> internalOrderFiles;

    @JsonProperty
    @Schema(description = "Status of the order", example = "open", requiredMode = Schema.RequiredMode.REQUIRED)
    private Status status;

    public enum Status {
        OPEN("open"), CHECKED("checked"), PRODUCING("producing"), PRODUCED("produced"), SENT("sent"), CLOSED("closed");

        private final String status;

        Status(String status) {
            this.status = status;
        }

        public String getStatus() {
            return this.status;
        }

        @JsonCreator
        public static Status fromString(String status) {
            Optional<Status> statusOptional = Arrays.stream(Status.values()).filter(o -> o.status.equalsIgnoreCase(status)).findFirst();

            return statusOptional.orElse(null);
        }

        @Override
        @JsonValue
        public String toString() {
            return String.valueOf(status);
        }
    }

    @JsonProperty
    @Schema(description = "When was a candidate in the system created", example = "2020-09-11", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfReceiving;

    @EqualsAndHashCode(callSuper = true)
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @SuperBuilder
    public static class ClientDataDTO extends DefaultDTOObject {
        @JsonProperty
        @Schema(description = "Gender of the customer", example = "male", requiredMode = Schema.RequiredMode.REQUIRED)
        private Salutation salutation;

        @JsonProperty
        @Schema(description = "Name of the customer", example = "John", requiredMode = Schema.RequiredMode.REQUIRED)
        private String name;

        @JsonProperty
        @Schema(description = "Surname of the customer", example = "Smith", requiredMode = Schema.RequiredMode.REQUIRED)
        private String surname;

        @JsonProperty
        @Schema(description = "Name of the street", example = "Hauptstraße", requiredMode = Schema.RequiredMode.REQUIRED)
        private String street;

        @JsonProperty
        @Schema(description = "Number of the house, where the customer lives. Requires only when street specified", example = "110", requiredMode = Schema.RequiredMode.REQUIRED)
        private String houseNumber;

        @JsonProperty
        @Schema(description = "Zip code", example = "90459", requiredMode = Schema.RequiredMode.REQUIRED)
        private Integer zip;

        @JsonProperty
        @Schema(description = "Name of the city", example = "Nürnberg", requiredMode = Schema.RequiredMode.REQUIRED)
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

    @EqualsAndHashCode(callSuper = true)
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @SuperBuilder
    public static class PersonalComputerDTO extends DefaultDTOObject {
        @JsonProperty
        @Schema(description = "Name of the case", example = "MSI MAG Forge 100R", requiredMode = Schema.RequiredMode.REQUIRED)
        private String computerCase;

        @JsonProperty
        @Schema(description = "Name of the motherboard", example = "Gigabyte B550 Aorus Pro V2", requiredMode = Schema.RequiredMode.REQUIRED)
        private String motherboard;

        @JsonProperty
        @Schema(description = "Name of the processor", example = "AMD Ryzen 7 5800X", requiredMode = Schema.RequiredMode.REQUIRED)
        private String processor;

        @JsonProperty
        @Schema(description = "Name of the graphics card", example = "AMD Radeon RX 6700 XT 12 GB", requiredMode = Schema.RequiredMode.REQUIRED)
        private String graphicsCard;

        @JsonProperty
        @Schema(description = "Name of the RAM", example = "32GB Corsair Vengeance LPX DDR4-3000", requiredMode = Schema.RequiredMode.REQUIRED)
        private String randomAccessMemory;

        @JsonProperty
        @Schema(description = "Name of the storage device", example = "250GB Samsung 870 EVO", requiredMode = Schema.RequiredMode.REQUIRED)
        private String storageDevice;

        @JsonProperty
        @Schema(description = "Name of the power supply unit", example = "700W - be quiet! Pure power 11", requiredMode = Schema.RequiredMode.REQUIRED)
        private String powerSupplyUnit;
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @SuperBuilder
    public static class InternalOrderFileDTO extends DefaultDTOObject {
        @JsonProperty
        @Schema(description = "Name of the file", example = "somefile.pdf", requiredMode = Schema.RequiredMode.REQUIRED)
        private String name;

        @JsonProperty
        @Schema(description = "Notes with additional information", example = "File is encrypted")
        private String note;
    }
}


