package org.example.models;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChargingConnector {
    @NotNull(message = "{id.mandatory}")
    private Long id;
    @NotNull(message = "{type.mandatory}")
    private ChargingConnectorType type;
    @NotNull(message = "{max.power.mandatory}")
    @Max(value = 240, message = "{max.power.exceed}")
    @Min(value = 7, message = "{min.power.low}")
    private Double maxPower;
}
