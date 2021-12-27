package de.niko.pcstore.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
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
public class InternalOrderDTO extends DefaultDTOObject {
    @JsonProperty("clientData")
    @ApiModelProperty(value = "Client data", required = true)
    private ClientDataDTO clientData;

    @JsonProperty("personalComputer")
    @ApiModelProperty(value = "Personal computer", required = true)
    private PersonalComputerDTO personalComputer;

    @JsonProperty("privacyPolicy")
    @ApiModelProperty(example = "true", value = "Data protection regulation agreement", required = true)
    private Boolean privacyPolicy;

    @JsonProperty("internalOrderFiles")
    @ApiModelProperty("Files")
    private List<InternalOrderFileDTO> internalOrderFiles;
}


