package org.example.models;

import java.util.Arrays;
import lombok.Getter;

@Getter
public enum ChargingConnectorType {
    CSS("CSS"),
    CHADEMO("CHAdeMO"),
    TYPE1("Type1"),
    TYPE2("Type2");

    private final String value;

    ChargingConnectorType(String value) {
        this.value = value;
    }

    public static ChargingConnectorType parseValue(String value) {
        return Arrays.stream(values())
                .filter(v -> v.toString().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid charging"
                       + " connector type value: " + value));
    }
}
