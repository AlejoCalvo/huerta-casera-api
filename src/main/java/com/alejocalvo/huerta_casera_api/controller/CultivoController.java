package com.alejocalvo.huerta_casera_api.controller;

import com.alejocalvo.huerta_casera_api.dto.CultivoRequest;
import com.alejocalvo.huerta_casera_api.model.Cultivo;
import com.alejocalvo.huerta_casera_api.repository.CultivoRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cultivos")
public class CultivoController {

    private final CultivoRepository cultivoRepository;

    public CultivoController(CultivoRepository cultivoRepository) {
        this.cultivoRepository = cultivoRepository;
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

    Cultivo nuevoCultivo = new Cultivo(
            null,
            cultivoRequest.getNombre(),
            cultivoRequest.getTipo(),
            cultivoRequest.getUbicacion()
    );

    Cultivo cultivoGuardado = cultivoRepository.save(nuevoCultivo);

    return ResponseEntity.status(201).body(cultivoGuardado);
}
    @PutMapping("/{id}")
public ResponseEntity<Cultivo> actualizarCultivo(
        @PathVariable Long id,
        @RequestBody CultivoRequest cultivoRequest) {

    return cultivoRepository.findById(id)
            .map(cultivo -> {
                cultivo.setNombre(cultivoRequest.getNombre());
                cultivo.setTipo(cultivoRequest.getTipo());
                cultivo.setUbicacion(cultivoRequest.getUbicacion());

                Cultivo cultivoActualizado = cultivoRepository.save(cultivo);

                return ResponseEntity.ok(cultivoActualizado);
            })
            .orElse(ResponseEntity.notFound().build());
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