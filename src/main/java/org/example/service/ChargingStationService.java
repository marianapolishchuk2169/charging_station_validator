package org.example.service;

import java.util.Optional;
import org.example.models.ChargingStation;

public interface ChargingStationService {
    ChargingStation save(ChargingStation chargingStation);

    Optional<ChargingStation> findById(Long id);
}
