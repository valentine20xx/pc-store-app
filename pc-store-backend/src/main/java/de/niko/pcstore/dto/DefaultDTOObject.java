package de.niko.pcstore.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class DefaultDTOObject {
    @JsonProperty("id")
    @Schema(description = "Unique UUID", example = "d290f1ee-6c54-4b01-90e6-d701748f0851", requiredMode = Schema.RequiredMode.REQUIRED)
    private String id;

    @JsonProperty("version")
    @Schema(description = "Date of the last change", example = "2021-11-23T10:03:38.538", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private Timestamp version;
}
