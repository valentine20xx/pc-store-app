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
public class NewInternalOrderFileDTO {
    @JsonProperty
    @ApiModelProperty(example = "somefile.pdf", required = true, value = "Name of the file")
    private String name;

    @JsonProperty
    @ApiModelProperty(example = "File is encrypted", value = "Notes with additional information")
    private String note;
}
