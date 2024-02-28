package org.example.dao;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.example.models.ChargingStation;

public class DummyChargingStationDaoImpl implements ChargingStationDao {
    private final Map<Long, ChargingStation> chargingStations = new HashMap<>();

    @Override
    public ChargingStation save(ChargingStation chargingStation) {
        chargingStations.put(chargingStation.getId(), chargingStation);
        return chargingStation;
    }

    @Override
    public Optional<ChargingStation> findById(Long id) {
        return Optional.ofNullable(chargingStations.get(id));
    }
}
