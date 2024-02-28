package org.example.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.dao.ChargingStationDao;
import org.example.models.ChargingStation;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChargingStationServiceImpl implements ChargingStationService {
    private final ChargingStationDao dao;

    @Override
    public ChargingStation save(ChargingStation chargingStation) {
        return dao.save(chargingStation);
    }

    @Override
    public Optional<ChargingStation> findById(Long id) {
        return dao.findById(id);
    }
}
