package com.torneobgt.backend.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.torneobgt.backend.model.HistorialTorneo;
import com.torneobgt.backend.model.Torneo;
import com.torneobgt.backend.model.enums.EstadoTorneo;
import com.torneobgt.backend.repository.HistorialTorneoRepository;
import com.torneobgt.backend.repository.PartidoRepository;
import com.torneobgt.backend.repository.TablaPosicionesRepository;
import com.torneobgt.backend.repository.TorneoRepository;

@Service
public class HistorialTorneoService {

    @Autowired
    private HistorialTorneoRepository historialRepository;

    @Autowired
    private TorneoRepository torneoRepository;

    @Autowired
    private TablaPosicionesRepository tablaRepository;

    @Autowired
    private PartidoRepository partidoRepository;

    @Transactional
    public HistorialTorneo finalizarTorneo(Long torneoId, String emailLider) {
        Torneo torneo = torneoRepository.findById(torneoId)
            .orElseThrow(() -> new RuntimeException("Torneo no encontrado"));

        if (!torneo.getLider().getEmail().equals(emailLider)) {
            throw new IllegalStateException("Solo el líder del torneo puede finalizarlo");
        }

        if (torneo.getEstado() == EstadoTorneo.FINALIZADO) {
            throw new IllegalStateException("El torneo ya está finalizado");
        }

        // cambia estado en MySQL
        torneo.setEstado(EstadoTorneo.FINALIZADO);
        torneoRepository.save(torneo);

        // construye snapshot de tabla de posiciones
        List<HistorialTorneo.EquipoSnapshot> tablaSnapshot = 
            tablaRepository.findByTorneoIdOrderByPuntosDescGolesFavorDesc(torneoId)
                .stream()
                .map(t -> {
                    HistorialTorneo.EquipoSnapshot snap = new HistorialTorneo.EquipoSnapshot();
                    snap.setEquipoId(t.getEquipo().getId());
                    snap.setNombreEquipo(t.getEquipo().getNombre());
                    snap.setPuntos(t.getPuntos());
                    snap.setGanados(t.getGanados());
                    snap.setEmpatados(t.getEmpatados());
                    snap.setPerdidos(t.getPerdidos());
                    snap.setGolesFavor(t.getGolesFavor());
                    snap.setGolesContra(t.getGolesContra());
                    snap.setDiferenciaGoles(t.getDiferenciaGoles());
                    snap.setPartidosJugados(t.getPartidosJugados());
                    return snap;
                }).collect(Collectors.toList());

        // construye snapshot de partidos
        List<HistorialTorneo.PartidoSnapshot> partidosSnapshot = 
            partidoRepository.findByTorneoId(torneoId)
                .stream()
                .map(p -> {
                    HistorialTorneo.PartidoSnapshot snap = new HistorialTorneo.PartidoSnapshot();
                    snap.setPartidoId(p.getId());
                    snap.setEquipoLocal(p.getEquipoLocal().getNombre());
                    snap.setEquipoVisitante(p.getEquipoVisitante().getNombre());
                    snap.setGolesLocal(p.getGolesLocal());
                    snap.setGolesVisitante(p.getGolesVisitante());
                    snap.setLugar(p.getLugar());
                    snap.setEstado(p.getEstado().name());
                    return snap;
                }).collect(Collectors.toList());

        // guarda historial en MongoDB
        HistorialTorneo historial = new HistorialTorneo();
        historial.setTorneoId(torneoId);
        historial.setNombreTorneo(torneo.getNombre());
        historial.setDeporte(torneo.getDeporte());
        historial.setUbicacion(torneo.getUbicacion());
        historial.setFechaCierre(LocalDateTime.now());
        historial.setLiderEmail(emailLider);
        historial.setTablaPosiciones(tablaSnapshot);
        historial.setPartidos(partidosSnapshot);

        return historialRepository.save(historial);
    }

    public List<HistorialTorneo> getHistorialByLider(String email) {
        return historialRepository.findByLiderEmail(email);
    }

    public HistorialTorneo getHistorialByTorneo(Long torneoId) {
        return historialRepository.findByTorneoId(torneoId)
            .orElseThrow(() -> new RuntimeException("No hay historial para este torneo"));
    }
}