package com.alejocalvo.huerta_casera_api.repository;

import com.alejocalvo.huerta_casera_api.model.Zona;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ZonaRepository extends JpaRepository<Zona, Long> {
}