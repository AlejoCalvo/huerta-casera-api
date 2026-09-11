package com.alejocalvo.huerta_casera_api.repository;

import com.alejocalvo.huerta_casera_api.model.Cultivo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CultivoRepository extends JpaRepository<Cultivo, Long> {

    List<Cultivo> findByTipoIgnoreCase(String tipo);
}