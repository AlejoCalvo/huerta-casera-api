package com.alejocalvo.huerta_casera_api.service;

import com.alejocalvo.huerta_casera_api.dto.ClimaResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class ClimaService {

    private static final Logger logger =
            LoggerFactory.getLogger(ClimaService.class);

    private final RestClient restClient;

    public ClimaService() {
        this.restClient = RestClient.builder()
                .baseUrl("https://api.open-meteo.com")
                .build();
    }

    public ClimaResponse obtenerClima(double latitud, double longitud) {

        try {
            return restClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/v1/forecast")
                            .queryParam("latitude", latitud)
                            .queryParam("longitude", longitud)
                            .queryParam(
                                    "current",
                                    "temperature_2m,relative_humidity_2m,precipitation,wind_speed_10m"
                            )
                            .queryParam("timezone", "America/Bogota")
                            .build())
                    .retrieve()
                    .body(ClimaResponse.class);

        } catch (Exception e) {

            logger.error(
                    "Error al consultar el servicio externo Open-Meteo para latitud {} y longitud {}: {}",
                    latitud,
                    longitud,
                    e.getMessage()
            );

            return null;
        }
    }
}