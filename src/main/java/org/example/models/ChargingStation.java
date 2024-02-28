package org.example.models;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.validator.ConditionalValidation;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ConditionalValidation(
        conditionalProperty = "isPublic",
        values = {"true"},
        requiredProperties = {"title", "description", "address", "geoCoordinates"},
        message = "{public.station.mandatory}"
)
public class ChargingStation {
    @NotNull(message = "{id.mandatory}")
    private Long id;
    private String title;
    private String description;
    private String address;
    @Valid
    private GeoCoordinates geoCoordinates;
    @NotNull(message = "{public.status.mandatory}")
    private Boolean isPublic;
    @NotEmpty(message = "{connectors.list.mandatory}")
    @Size(min = 1, max = 8)
    @Valid
    private List<ChargingConnector> connectors;
}
