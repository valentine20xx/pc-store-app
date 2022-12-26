package de.niko.pcstore.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class JwtResponse implements Serializable {
    @JsonProperty
    @Schema(description = "JWT token", requiredMode = Schema.RequiredMode.REQUIRED)
    private String token;
    @JsonProperty
    @Schema(description = "Username", requiredMode = Schema.RequiredMode.REQUIRED)
    private String fullname;
}