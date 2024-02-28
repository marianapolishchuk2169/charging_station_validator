package org.example.service;

import static org.example.service.ChargingStationServiceTest.TestResources.ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Optional;
import org.example.dao.ChargingStationDao;
import org.example.models.ChargingConnector;
import org.example.models.ChargingConnectorType;
import org.example.models.ChargingStation;
import org.example.models.GeoCoordinates;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ChargingStationServiceTest {
    @Mock
    private ChargingStationDao dao;

    @InjectMocks
    private ChargingStationServiceImpl service;

    @Test
    void whenSave_thenCallDaoSave() {
        ChargingStation station = TestResources.createValidChargingStation();

        service.save(station);

        verify(dao).save(station);
    }

    @Test
    void whenFindById_thenCallDaoFindById() {
        ChargingStation station = TestResources.createValidChargingStation();

        when(dao.findById(ID)).thenReturn(Optional.of(station));

        Optional<ChargingStation> foundStation = service.findById(ID);

        assertTrue(foundStation.isPresent());
        assertEquals(ID, foundStation.get().getId());

        verify(dao).findById(ID);
    }

    static class TestResources {
        static final String ADDRESS = "Poland";
        static final Double LONGITUDE = 80.0;
        static final Double LATITUDE = 75.0;
        static final String TITLE = "SPEED-E";
        static final String DESCRIPTION = "description";
        static final Long ID = 1L;
        static final Double MAX_POWER = 230.0;

        static ChargingStation createValidChargingStation() {
            return ChargingStation.builder()
                    .id(ID)
                    .title(TITLE)
                    .description(DESCRIPTION)
                    .address(ADDRESS)
                    .geoCoordinates(new GeoCoordinates(LONGITUDE, LATITUDE))
                    .isPublic(true)
                    .connectors(Collections.singletonList(createValidConnector()))
                    .build();
        }

        static ChargingConnector createValidConnector() {
            return ChargingConnector.builder()
                    .id(ID)
                    .type(ChargingConnectorType.CSS)
                    .maxPower(MAX_POWER)
                    .build();
        }
    }
}
