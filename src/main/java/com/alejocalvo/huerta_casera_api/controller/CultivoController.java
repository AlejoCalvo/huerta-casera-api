package com.alejocalvo.huerta_casera_api.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import com.alejocalvo.huerta_casera_api.dto.CultivoRequest;
import com.alejocalvo.huerta_casera_api.model.Cultivo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
@RestController
@RequestMapping("/api/cultivos")
public class CultivoController {@GetMapping
public List<Cultivo> obtenerCultivos() {
    return List.of(
        new Cultivo(1L, "Tomate", "Hortaliza"),
        new Cultivo(2L, "Lechuga", "Hortaliza"),
        new Cultivo(3L, "Cilantro", "Hortaliza"),
        new Cultivo(4L, "Zanahoria", "Hortaliza")
    );
}
@GetMapping("/{id}")
public ResponseEntity<Cultivo> obtenerCultivoPorId(@PathVariable Long id) {

    List<Cultivo> cultivos = List.of(
        new Cultivo(1L, "Tomate", "Hortaliza"),
        new Cultivo(2L, "Lechuga", "Hortaliza"),
        new Cultivo(3L, "Cilantro", "Hortaliza"),
        new Cultivo(4L, "Zanahoria", "Hortaliza")
    );
     for (Cultivo cultivo : cultivos) {
        if (cultivo.getId().equals(id)) {
            return ResponseEntity.ok(cultivo);
        }
        
}
return ResponseEntity.notFound().build();
}
@GetMapping("/buscar")
public List<Cultivo> buscarPorTipo(@RequestParam String tipo) {
    List<Cultivo> cultivos = List.of(
        new Cultivo(1L, "Tomate", "Hortaliza"),
        new Cultivo(2L, "Lechuga", "Hortaliza"),
        new Cultivo(3L, "Cilantro", "Hortaliza"),
        new Cultivo(4L, "Zanahoria", "Hortaliza")
    );
    return cultivos.stream()
            .filter(cultivo -> cultivo.getTipo().equalsIgnoreCase(tipo))
            .toList();
}
@PostMapping
public Cultivo crearCultivo(@RequestBody CultivoRequest cultivoRequest) {

    Cultivo nuevoCultivo = new Cultivo(
        5L,
        cultivoRequest.getNombre(),
        cultivoRequest.getTipo()
    );

    return nuevoCultivo;
}
}

