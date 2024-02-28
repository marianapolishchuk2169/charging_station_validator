package org.example.controller;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import java.util.Optional;
import org.example.models.ChargingConnector;
import org.example.models.ChargingConnectorType;
import org.example.models.ChargingStation;
import org.example.models.GeoCoordinates;
import org.example.service.ChargingStationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ChargingStationController.class)
class ChargingStationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChargingStationService chargingStationService;

    @MockBean
    private MessageSource messageSource;

    @Test
    void createChargingStation_ValidStation_ReturnsCreated() throws Exception {
        ChargingStation validStation = TestResources.createValidChargingStation();

        when(chargingStationService.save(eq(validStation))).thenReturn(validStation);

        mockMvc.perform(post("/api/charging-stations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(validStation)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(validStation.getId()))
                .andExpect(jsonPath("$.title").value(validStation.getTitle()));

        verify(chargingStationService).save(eq(validStation));
    }

    @Test
    void getChargingStationById_ValidId_ReturnsStation() throws Exception {
        ChargingStation validStation = TestResources.createValidChargingStation();

        when(chargingStationService.findById(eq(validStation.getId())))
                .thenReturn(Optional.of(validStation));

        mockMvc.perform(get("/api/charging-stations/{id}", validStation.getId())
                        .param("lang", "en"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(validStation.getId()))
                .andExpect(jsonPath("$.title").value(validStation.getTitle()));

        verify(chargingStationService).findById(eq(validStation.getId()));
    }

    @Test
    void createChargingStation_InvalidGeoCoordinates_ReturnsBadRequest() throws Exception {
        ChargingStation invalidStation = TestResources.createStationWithInvalidCoordinates();
        when(messageSource.getMessage(any(), any())).thenReturn("Invalid geo coordinates");

        mockMvc.perform(post("/api/charging-stations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(invalidStation)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Invalid geo coordinates")));
    }

    @Test
    void createChargingStation_EmptyId_ReturnsBadRequest() throws Exception {
        ChargingStation invalidStation = TestResources.createStationWithEmptyID();
        when(messageSource.getMessage(any(), any())).thenReturn("ID is mandatory");

        mockMvc.perform(post("/api/charging-stations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(invalidStation)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("ID is mandatory")));
    }

    @Test
    void createPublicChargingStation_EmptyParameters_ReturnsBadRequest() throws Exception {
        ChargingStation invalidStation = TestResources.createPublicStationWithoutParameters();
        when(messageSource.getMessage(any(), any())).thenReturn(
                "Title, description, address, and geoCoordinates"
                + " are mandatory for public stations");

        mockMvc.perform(post("/api/charging-stations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(invalidStation)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(
                        "Title, description, address, and geoCoordinates "
                        + "are mandatory for public stations")));
    }

    @Test
    void createChargingStation_InvalidPower_ReturnsBadRequest() throws Exception {
        ChargingStation invalidStation = TestResources.createStationWithInvalidPower();
        when(messageSource.getMessage(any(), any())).thenReturn("Max power cannot exceed 240 kW");

        mockMvc.perform(post("/api/charging-stations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(invalidStation)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Max power cannot exceed 240 kW")));
    }

    @Test
    void getChargingStationById_InvalidId_ReturnsNotFound() throws Exception {
        when(chargingStationService.findById(eq(TestResources.INVALID_ID)))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/api/charging-stations/{id}", TestResources.INVALID_ID)
                        .param("lang", "en"))
                .andExpect(status().isNotFound());

        verify(chargingStationService).findById(eq(TestResources.INVALID_ID));
    }

    static class TestResources {
        static final String ADDRESS = "Poland";
        static final Double LONGITUDE = 80.0;
        static final Double INVALID_LONGITUDE = 183.0;
        static final Double LATITUDE = 75.0;
        static final Double INVALID_LATITUDE = 75.0;
        static final String TITLE = "SPEED-E";
        static final String DESCRIPTION = "description";
        static final Long ID = 1L;
        static final Long INVALID_ID = 2L;
        static final Double MAX_POWER = 230.0;
        static final Double INVALID_MAX_POWER = 250.0;

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

        static ChargingConnector createInvalidConnector() {
            return ChargingConnector.builder()
                    .id(ID)
                    .type(ChargingConnectorType.CSS)
                    .maxPower(INVALID_MAX_POWER)
                    .build();
        }

        static ChargingStation createStationWithEmptyID() {
            return ChargingStation.builder()
                    .title(TITLE)
                    .description(DESCRIPTION)
                    .address(ADDRESS)
                    .geoCoordinates(new GeoCoordinates(LONGITUDE, LATITUDE))
                    .isPublic(true)
                    .connectors(Collections.singletonList(createValidConnector()))
                    .build();
        }

        static ChargingStation createPublicStationWithoutParameters() {
            return ChargingStation.builder()
                    .id(ID)
                    .geoCoordinates(new GeoCoordinates(LONGITUDE, LATITUDE))
                    .isPublic(true)
                    .connectors(Collections.singletonList(createValidConnector()))
                    .build();
        }

        static ChargingStation createStationWithInvalidCoordinates() {
            return ChargingStation.builder()
                    .id(ID)
                    .geoCoordinates(new GeoCoordinates(INVALID_LONGITUDE, INVALID_LATITUDE))
                    .isPublic(false)
                    .connectors(Collections.singletonList(createValidConnector()))
                    .build();
        }

        static ChargingStation createStationWithInvalidPower() {
            return ChargingStation.builder()
                    .id(ID)
                    .geoCoordinates(new GeoCoordinates(INVALID_LONGITUDE, INVALID_LATITUDE))
                    .isPublic(false)
                    .connectors(Collections.singletonList(createInvalidConnector()))
                    .build();
        }
    }
}
