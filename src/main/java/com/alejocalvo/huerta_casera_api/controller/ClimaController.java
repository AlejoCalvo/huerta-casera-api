package com.alejocalvo.huerta_casera_api.controller;

import com.alejocalvo.huerta_casera_api.dto.ClimaResponse;
import com.alejocalvo.huerta_casera_api.service.ClimaService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/clima")
public class ClimaController {

    private final ClimaService climaService;

    public ClimaController(ClimaService climaService) {
        this.climaService = climaService;
    }

    @GetMapping
    public ResponseEntity<?> obtenerClima(
            @RequestParam double latitud,
            @RequestParam double longitud) {

        ClimaResponse clima = climaService.obtenerClima(latitud, longitud);

        if (clima == null) {
            return ResponseEntity
                    .status(502)
                    .body("No fue posible obtener la información del clima.");
        }

        return ResponseEntity.ok(clima);
    }
}