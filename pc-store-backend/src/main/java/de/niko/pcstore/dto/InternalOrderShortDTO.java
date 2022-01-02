package de.niko.pcstore.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
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
    @JsonProperty("client")
    @ApiModelProperty(example = "Smith, John", value = "Short description (surname, name)")
    private String client;

    @JsonProperty("personalComputer")
    @ApiModelProperty(example = "AMD Ryzen 7 5800X, AMD Radeon RX 6700 XT 12 G", value = "Short description (Processor, Graphics card)")
    private String personalComputer;

    @JsonProperty("dateOfReceiving")
    @ApiModelProperty(example = "2020-09-11", required = true, value = "When was a candidate in the system created")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfReceiving;

    @JsonProperty("status")
    @ApiModelProperty(example = "order-status-open", required = true, value = "Status of the order")
    private String statusId;
}
