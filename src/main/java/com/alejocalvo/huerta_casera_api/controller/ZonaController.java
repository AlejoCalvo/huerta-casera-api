package com.alejocalvo.huerta_casera_api.controller;

import com.alejocalvo.huerta_casera_api.model.Zona;
import com.alejocalvo.huerta_casera_api.repository.ZonaRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/zonas")
public class ZonaController {

    private final ZonaRepository zonaRepository;

    public ZonaController(ZonaRepository zonaRepository) {
        this.zonaRepository = zonaRepository;
    }

    @GetMapping
    public List<Zona> obtenerZonas() {
        return zonaRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Zona> crearZona(@RequestBody Zona zona) {

        Zona zonaGuardada = zonaRepository.save(zona);

        return ResponseEntity.status(201).body(zonaGuardada);
    }
}