package com.torneobgt.backend.repository;

import com.torneobgt.backend.model.TablaPosiciones;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface TablaPosicionesRepository 
    extends JpaRepository<TablaPosiciones, Long> {
    
    List<TablaPosiciones> findByTorneoIdOrderByPuntosDescGolesFavorDesc(Long torneoId);
    Optional<TablaPosiciones> findByEquipoIdAndTorneoId(Long equipoId, Long torneoId);
}