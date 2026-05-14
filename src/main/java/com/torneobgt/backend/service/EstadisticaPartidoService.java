package com.torneobgt.backend.service;

import com.torneobgt.backend.model.EstadisticaPartido;
import com.torneobgt.backend.repository.EstadisticaPartidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
public class EstadisticaPartidoService {

    @Autowired
    private EstadisticaPartidoRepository estadisticaRepository;

    public EstadisticaPartido guardarEstadisticas(EstadisticaPartido estadistica) {
        
        estadisticaRepository.findByPartidoId(estadistica.getPartidoId())
            .ifPresent(e -> estadistica.setId(e.getId()));

        if (estadistica.getGoles() == null)
            estadistica.setGoles(new ArrayList<>());
        if (estadistica.getTarjetas() == null)
            estadistica.setTarjetas(new ArrayList<>());

        return estadisticaRepository.save(estadistica);
    }

    public EstadisticaPartido getEstadisticasByPartido(Long partidoId) {
        return estadisticaRepository.findByPartidoId(partidoId)
            .orElseThrow(() -> new RuntimeException("No hay estadísticas para este partido"));
    }
}