package com.torneobgt.backend.service;

import com.torneobgt.backend.dto.TorneoDTO;
import com.torneobgt.backend.model.*;
import com.torneobgt.backend.model.enums.EstadoTorneo;
import com.torneobgt.backend.repository.TorneoRepository;
import com.torneobgt.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TorneoService {

    @Autowired
    private TorneoRepository torneoRepository;

    @Autowired
    private UserRepository userRepository;

    public Torneo crearTorneo(TorneoDTO request, String email) {
        User lider = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Torneo torneo = new Torneo();
        torneo.setNombre(request.getNombre());
        torneo.setDeporte(request.getDeporte());
        torneo.setEstado(EstadoTorneo.valueOf(request.getEstado().toUpperCase()));
        torneo.setMaxEquipos(request.getMaxEquipos());
        torneo.setFechaInicio(request.getFechaInicio());
        torneo.setUbicacion(request.getUbicacion());
        torneo.setLider(lider);

        return torneoRepository.save(torneo);
    }
    
    public Torneo cambiarEstado(Long torneoId, String email, String nuevoEstado) {
        Torneo torneo = torneoRepository.findById(torneoId)
            .orElseThrow(() -> new RuntimeException("Torneo no encontrado"));

        if (!torneo.getLider().getEmail().equals(email)) {
            throw new RuntimeException("No tienes permisos para modificar este torneo");
        }
        
        if (torneo.getEstado() == EstadoTorneo.FINALIZADO) {
            throw new IllegalStateException("No se puede modificar un torneo finalizado");
        }

        torneo.setEstado(EstadoTorneo.valueOf(nuevoEstado.toUpperCase()));
        return torneoRepository.save(torneo);
    }

    // todos los torneos públicos con inscripción abierta
    public List<Torneo> getTorneosPublicos() {
    	return torneoRepository.findByEstadoNot(EstadoTorneo.FINALIZADO);
    }

    // torneos del líder autenticado
    public List<Torneo> getMisTorneos(String email) {
        return torneoRepository.findByLiderEmail(email);
    }
}