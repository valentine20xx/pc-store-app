package de.niko.pcstore.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.util.Arrays;
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
public class InternalOrderShortDTO extends DefaultDTOObject {
    @JsonProperty
    @Schema(description = "Short description (surname, name)", example = "Smith, John", requiredMode = Schema.RequiredMode.REQUIRED)
    private String client;

    @JsonProperty
    @Schema(description = "Short description (Processor, Graphics card)", example = "AMD Ryzen 7 5800X, AMD Radeon RX 6700 XT 12 GB", requiredMode = Schema.RequiredMode.REQUIRED)
    private String personalComputer;

    @JsonProperty
    @Schema(description = "When was a customer in the system created", example = "2020-09-11", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfReceiving;

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
            Optional<Status> statusOptional = Arrays.stream(Status.values()).filter(status1 -> status1.status.equalsIgnoreCase(status)).findFirst();

            return statusOptional.orElse(null);
        }

        @Override
        @JsonValue
        public String toString() {
            return String.valueOf(status);
        }
    }
}
