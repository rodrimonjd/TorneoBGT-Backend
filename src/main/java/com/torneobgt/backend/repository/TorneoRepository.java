package com.torneobgt.backend.repository;

import com.torneobgt.backend.model.Torneo;
import com.torneobgt.backend.model.enums.EstadoTorneo;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TorneoRepository extends JpaRepository<Torneo, Long> {
    List<Torneo> findByEstado(EstadoTorneo estado);
    List<Torneo> findByLiderEmail(String email);
    List<Torneo> findByEstadoNot(EstadoTorneo estado);
}