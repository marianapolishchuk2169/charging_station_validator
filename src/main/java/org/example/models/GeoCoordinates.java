package org.example.models;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GeoCoordinates {
    @NotNull(message = "{longitude.empty}")
    @DecimalMin(value = "-180.0", message = "{longitude.range}")
    @DecimalMax(value = "180.0", message = "{longitude.range}")
    private Double longitude;
    @NotNull(message = "{latitude.empty}")
    @DecimalMin(value = "-90.0", message = "{latitude.range}")
    @DecimalMax(value = "90.0", message = "{latitude.range}")
    private Double latitude;
}
