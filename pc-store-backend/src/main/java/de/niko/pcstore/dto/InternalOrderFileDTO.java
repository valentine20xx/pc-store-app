package de.niko.pcstore.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
public class InternalOrderFileDTO extends DefaultDTOObject {
    @JsonProperty("name")
    @ApiModelProperty(example = "somefile.pdf", required = true, value = "Name of the file")
    private String name;

    @JsonProperty("notes")
    @ApiModelProperty(example = "File is encrypted", value = "Notes with additional information")
    private String notes;
}