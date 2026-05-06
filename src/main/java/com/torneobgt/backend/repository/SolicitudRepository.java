package com.torneobgt.backend.repository;

import com.torneobgt.backend.model.Solicitud;
import com.torneobgt.backend.model.enums.EstadoSolicitud;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface SolicitudRepository extends JpaRepository<Solicitud, Long> {
    List<Solicitud> findByTorneoId(Long torneoId);
    List<Solicitud> findByEquipoId(Long equipoId);
    Optional<Solicitud> findByEquipoIdAndTorneoId(Long equipoId, Long torneoId);
    List<Solicitud> findByTorneoIdAndEstado(Long torneoId, EstadoSolicitud estado);
}