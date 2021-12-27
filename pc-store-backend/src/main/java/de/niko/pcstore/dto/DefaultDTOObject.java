package de.niko.pcstore.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
//@ApiModel
@SuperBuilder
public class DefaultDTOObject {
    @JsonProperty("id")
    @ApiModelProperty(example = "d290f1ee-6c54-4b01-90e6-d701748f0851", value = "Unique id")
    private String id;

    @JsonProperty("version")
    @ApiModelProperty(example = "2021-11-23T10:03:38.538", value = "Date of the last change")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private Timestamp version;
}
