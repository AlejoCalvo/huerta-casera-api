package com.alejocalvo.huerta_casera_api.health;

import com.alejocalvo.huerta_casera_api.repository.CultivoRepository;

import org.springframework.boot.health.contributor.Health;
import org.springframework.boot.health.contributor.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class HuertaHealthIndicator implements HealthIndicator {

    private final CultivoRepository cultivoRepository;

    public HuertaHealthIndicator(CultivoRepository cultivoRepository) {
        this.cultivoRepository = cultivoRepository;
    }

    @Override
    public Health health() {

        try {
            long cantidadCultivos = cultivoRepository.count();

            return Health.up()
                    .withDetail("servicio", "Huerta Casera API")
                    .withDetail("baseDeDatos", "Disponible")
                    .withDetail("cultivosRegistrados", cantidadCultivos)
                    .build();

        } catch (Exception e) {

            return Health.down()
                    .withDetail("servicio", "Huerta Casera API")
                    .withDetail("baseDeDatos", "No disponible")
                    .build();
        }
    }
}
