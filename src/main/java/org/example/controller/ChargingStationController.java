package org.example.controller;

import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.models.ChargingStation;
import org.example.service.ChargingStationService;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/charging-stations")
@RequiredArgsConstructor
public class ChargingStationController {
    private final ChargingStationService chargingStationService;
    private final MessageSource messageSource;

    @PostMapping
    public ResponseEntity<?> createChargingStation(
            @Valid @RequestBody ChargingStation chargingStation,
            BindingResult bindingResult,
            @RequestParam(name = "lang", required = false) Locale locale) {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldErrors().stream()
                    .map(fieldError -> messageSource.getMessage(fieldError, locale))
                    .collect(Collectors.joining("; "));
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
        ChargingStation savedStation = chargingStationService.save(chargingStation);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedStation);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getChargingStationById(@PathVariable Long id,
                                                    @RequestParam(name = "lang", required = false)
                                                    Locale locale) {
        Optional<ChargingStation> foundStation = chargingStationService.findById(id);
        if (foundStation.isPresent()) {
            return ResponseEntity.ok(foundStation.get());
        } else {
            String errorMessage = messageSource.getMessage("charging.station.not.found",
                    null, locale);
            return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
        }
    }
}
