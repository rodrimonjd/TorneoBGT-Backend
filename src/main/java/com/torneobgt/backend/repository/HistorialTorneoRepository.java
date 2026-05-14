package com.torneobgt.backend.repository;

import com.torneobgt.backend.model.HistorialTorneo;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;
import java.util.List;

public interface HistorialTorneoRepository 
    extends MongoRepository<HistorialTorneo, String> {
    Optional<HistorialTorneo> findByTorneoId(Long torneoId);
    List<HistorialTorneo> findByLiderEmail(String email);
}