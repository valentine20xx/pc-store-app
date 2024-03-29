package de.niko.pcstore.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ErrorDTO {
    @JsonProperty
    @Schema(description = "Code of the error", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer code;

    @JsonProperty
    @Schema(description = "Message of the error", example = "Error occurred", requiredMode = Schema.RequiredMode.REQUIRED)
    private String message;
}
