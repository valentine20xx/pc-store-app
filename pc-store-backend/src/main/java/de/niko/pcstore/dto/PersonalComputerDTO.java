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
public class PersonalComputerDTO extends DefaultDTOObject {
    @JsonProperty("computerCase")
    @ApiModelProperty(example = "MSI MAG Forge 100R", value = "Name of the case", required = true)
    private String computerCase;

    @JsonProperty("motherboard")
    @ApiModelProperty(example = "Gigabyte B550 Aorus Pro V2", value = "Name of the motherboard", required = true)
    private String motherboard;

    @JsonProperty("processor")
    @ApiModelProperty(example = "AMD Ryzen 7 5800X", value = "Name of the processor", required = true)
    private String processor;

    @JsonProperty("graphicsCard")
    @ApiModelProperty(example = "AMD Radeon RX 6700 XT 12 GB", value = "Name of the graphics card", required = true)
    private String graphicsCard;

    @JsonProperty("randomAccessMemory")
    @ApiModelProperty(example = "32GB Corsair Vengeance LPX DDR4-3000", value = "Name of the RAM", required = true)
    private String randomAccessMemory;

    @JsonProperty("storageDevice")
    @ApiModelProperty(example = "250GB Samsung 870 EVO", value = "Name of the storage device", required = true)
    private String storageDevice;

    @JsonProperty("powerSupplyUnit")
    @ApiModelProperty(example = "700W - be quiet! Pure power 11", value = "Name of the power supply unit", required = true)
    private String powerSupplyUnit;
}
