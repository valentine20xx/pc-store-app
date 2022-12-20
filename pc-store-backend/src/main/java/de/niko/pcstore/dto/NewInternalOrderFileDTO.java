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
public class NewInternalOrderFileDTO {
    @JsonProperty
    @Schema(description = "Name of the file", example = "somefile.pdf", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @JsonProperty
    @Schema(description = "Notes with additional information", example = "File is encrypted")
    private String note;
}
