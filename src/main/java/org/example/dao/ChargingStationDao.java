package org.example.dao;

import java.util.Optional;
import org.example.models.ChargingStation;

public interface ChargingStationDao {
    ChargingStation save(ChargingStation chargingStation);

    Optional<ChargingStation> findById(Long id);
}
