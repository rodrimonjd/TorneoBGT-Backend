package com.torneobgt.backend.repository;

import com.torneobgt.backend.model.EstadisticaPartido;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface EstadisticaPartidoRepository 
    extends MongoRepository<EstadisticaPartido, String> {
    Optional<EstadisticaPartido> findByPartidoId(Long partidoId);
}
