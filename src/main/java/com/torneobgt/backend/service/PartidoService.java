package com.torneobgt.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.torneobgt.backend.dto.PartidoDTO;
import com.torneobgt.backend.dto.request.ActualizarMarcadorRequest;
import com.torneobgt.backend.model.Equipo;
import com.torneobgt.backend.model.Partido;
import com.torneobgt.backend.model.Torneo;
import com.torneobgt.backend.model.enums.EstadoPartido;
import com.torneobgt.backend.model.enums.EstadoTorneo;
import com.torneobgt.backend.repository.EquipoRepository;
import com.torneobgt.backend.repository.PartidoRepository;
import com.torneobgt.backend.repository.TorneoRepository;

@Service
public class PartidoService {

    @Autowired
    private PartidoRepository partidoRepository;
    
    @Autowired
    private Torneo torneo;

    @Autowired
    private TorneoRepository torneoRepository;

    @Autowired
    private EquipoRepository equipoRepository;
    
    @Autowired
    private TablaPosicionesService tablaPosicionesService;

    public Partido crearPartido(PartidoDTO request) {
    	
    	if (torneo.getEstado() == EstadoTorneo.FINALIZADO) {
    	    throw new IllegalStateException("No se pueden crear partidos en un torneo finalizado");
    	}
    	
        Torneo torneo = torneoRepository.findById(request.getTorneoId())
            .orElseThrow(() -> new RuntimeException("Torneo no encontrado"));

        Equipo local = equipoRepository.findById(request.getEquipoLocalId())
            .orElseThrow(() -> new RuntimeException("Equipo local no encontrado"));

        Equipo visitante = equipoRepository.findById(request.getEquipoVisitanteId())
            .orElseThrow(() -> new RuntimeException("Equipo visitante no encontrado"));

        if (local.getId().equals(visitante.getId())) {
            throw new IllegalArgumentException("Un equipo no puede jugar contra sí mismo");
        }
        
        Partido partido = new Partido();
        partido.setTorneo(torneo);
        partido.setEquipoLocal(local);
        partido.setEquipoVisitante(visitante);
        partido.setFechaHora(request.getFechaHora());
        partido.setLugar(request.getLugar());

        return partidoRepository.save(partido);
        
        
    }

    public Partido actualizarMarcador(Long partidoId, ActualizarMarcadorRequest request) {
        Partido partido = partidoRepository.findById(partidoId)
            .orElseThrow(() -> new RuntimeException("Partido no encontrado"));

        if (partido.getEstado() != EstadoPartido.FINALIZADO) {
            throw new IllegalStateException("El partido debe estar finalizado para ingresar el marcador");
        }

        // guarda marcador anterior antes de actualizar
        int golesLocalAnterior = partido.getGolesLocal() != null ? partido.getGolesLocal() : -1;
        int golesVisitanteAnterior = partido.getGolesVisitante() != null ? partido.getGolesVisitante() : -1;
        boolean teniaMarcador = golesLocalAnterior >= 0;

        partido.setGolesLocal(request.getGolesLocal());
        partido.setGolesVisitante(request.getGolesVisitante());

        Partido saved = partidoRepository.save(partido);

        // actualiza tabla pasando el marcador anterior
        tablaPosicionesService.actualizarTabla(
            saved, golesLocalAnterior, golesVisitanteAnterior, teniaMarcador);

        return saved;
    }

    public List<Partido> getPartidosByTorneo(Long torneoId) {
        return partidoRepository.findByTorneoId(torneoId);
    }
    
    public Partido cambiarEstado(Long partidoId, String estado) {
        Partido partido = partidoRepository.findById(partidoId)
            .orElseThrow(() -> new RuntimeException("Partido no encontrado"));

        partido.setEstado(EstadoPartido.valueOf(estado.toUpperCase()));
        return partidoRepository.save(partido);
    }
    
    public Optional<Partido> getPartidoById(Long id) {
        return partidoRepository.findById(id);
    }
}
