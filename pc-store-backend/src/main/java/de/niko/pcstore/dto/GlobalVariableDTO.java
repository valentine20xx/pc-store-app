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
public class GlobalVariableDTO extends DefaultDTOObject {
    @JsonProperty("type")
    @ApiModelProperty(example = "order-status", required = true, value = "The type or dropdown-field this value is used for")
    private String type;

    @JsonProperty("subtype")
    @ApiModelProperty(example = "open", required = true, value = "The subtype or dropdown-field this value is used for")
    private String subtype;

    @JsonProperty("name")
    @ApiModelProperty(example = "Junior", required = true, value = "Short name of the constant, that will be showed to the end-user")
    private String name;

    @JsonProperty("description")
    @ApiModelProperty(example = "Der Bewerber hat max. 2 Jahre Berufserfahrung", value = "Description of the constant")
    private String description;

    @JsonProperty("deletable")
    @ApiModelProperty(example = "true", value = "Is it predefined constant and should not be deleted or by user created constant, that can be deleted")
    private Boolean deletable;
}
