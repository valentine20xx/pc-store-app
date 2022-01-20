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
public class ErrorDTO {
    @JsonProperty
    @ApiModelProperty(example = "1", required = true, value = "Code of the error")
    private Integer code;

    @JsonProperty
    @ApiModelProperty(example = "Error occurred", value = "Message of the error")
    private String message;
}
