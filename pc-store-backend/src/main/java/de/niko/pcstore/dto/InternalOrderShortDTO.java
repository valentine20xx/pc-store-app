package de.niko.pcstore.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel
@SuperBuilder
public class InternalOrderShortDTO extends DefaultDTOObject {
    @JsonProperty
    @ApiModelProperty(example = "Smith, John", value = "Short description (surname, name)")
    private String client;

    @JsonProperty
    @ApiModelProperty(example = "AMD Ryzen 7 5800X, AMD Radeon RX 6700 XT 12 G", value = "Short description (Processor, Graphics card)")
    private String personalComputer;

    @JsonProperty
    @ApiModelProperty(example = "2020-09-11", required = true, value = "When was a candidate in the system created")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfReceiving;

    @JsonProperty
    @ApiModelProperty(example = "open", required = true, value = "Status of the order")
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
