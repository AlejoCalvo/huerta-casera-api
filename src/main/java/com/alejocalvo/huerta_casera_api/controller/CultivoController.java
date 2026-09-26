package com.alejocalvo.huerta_casera_api.controller;

import com.alejocalvo.huerta_casera_api.dto.CultivoRequest;
import com.alejocalvo.huerta_casera_api.model.Cultivo;
import com.alejocalvo.huerta_casera_api.model.Zona;
import com.alejocalvo.huerta_casera_api.repository.CultivoRepository;
import com.alejocalvo.huerta_casera_api.repository.ZonaRepository;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cultivos")
public class CultivoController {

    private static final Logger logger =
            LoggerFactory.getLogger(CultivoController.class);

    private final CultivoRepository cultivoRepository;
    private final ZonaRepository zonaRepository;
    private final Counter cultivosCreados;

    public CultivoController(
            CultivoRepository cultivoRepository,
            ZonaRepository zonaRepository,
            MeterRegistry meterRegistry) {

        this.cultivoRepository = cultivoRepository;
        this.zonaRepository = zonaRepository;

        this.cultivosCreados = Counter.builder("cultivos.creados")
                .description("Cantidad de cultivos creados")
                .register(meterRegistry);
    }

    @GetMapping
    public List<Cultivo> obtenerCultivos() {
        return cultivoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cultivo> obtenerCultivoPorId(@PathVariable Long id) {

        return cultivoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/buscar")
    public List<Cultivo> buscarPorTipo(@RequestParam String tipo) {

        return cultivoRepository.findByTipoIgnoreCase(tipo);
    }

    @PostMapping
    public ResponseEntity<Cultivo> crearCultivo(
            @RequestBody CultivoRequest cultivoRequest) {

        Zona zona = zonaRepository.findById(cultivoRequest.getZonaId())
                .orElse(null);

        if (zona == null) {

            logger.warn(
                    "No se pudo crear el cultivo: la zona con ID {} no existe",
                    cultivoRequest.getZonaId()
            );

            return ResponseEntity.badRequest().build();
        }

        Cultivo nuevoCultivo = new Cultivo(
                null,
                cultivoRequest.getNombre(),
                cultivoRequest.getTipo(),
                zona
        );

        Cultivo cultivoGuardado = cultivoRepository.save(nuevoCultivo);

        cultivosCreados.increment();

        logger.info(
                "Cultivo creado correctamente. ID: {}, nombre: {}",
                cultivoGuardado.getId(),
                cultivoGuardado.getNombre()
        );

        return ResponseEntity.status(201).body(cultivoGuardado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cultivo> actualizarCultivo(
            @PathVariable Long id,
            @RequestBody CultivoRequest cultivoRequest) {

        Cultivo cultivo = cultivoRepository.findById(id)
                .orElse(null);

        if (cultivo == null) {
            return ResponseEntity.notFound().build();
        }

        Zona zona = zonaRepository.findById(cultivoRequest.getZonaId())
                .orElse(null);

        if (zona == null) {
            return ResponseEntity.badRequest().build();
        }

        cultivo.setNombre(cultivoRequest.getNombre());
        cultivo.setTipo(cultivoRequest.getTipo());
        cultivo.setZona(zona);

        Cultivo cultivoActualizado = cultivoRepository.save(cultivo);

        return ResponseEntity.ok(cultivoActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCultivo(@PathVariable Long id) {

        if (!cultivoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        cultivoRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}