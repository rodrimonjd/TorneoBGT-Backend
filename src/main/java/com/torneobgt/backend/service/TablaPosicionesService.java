package com.torneobgt.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.torneobgt.backend.model.Equipo;
import com.torneobgt.backend.model.Partido;
import com.torneobgt.backend.model.TablaPosiciones;
import com.torneobgt.backend.model.Torneo;
import com.torneobgt.backend.model.enums.EstadoPartido;
import com.torneobgt.backend.repository.EquipoRepository;
import com.torneobgt.backend.repository.TablaPosicionesRepository;
import com.torneobgt.backend.repository.TorneoRepository;

@Service
public class TablaPosicionesService {

    @Autowired
    private TablaPosicionesRepository tablaRepository;

    @Autowired
    private EquipoRepository equipoRepository;

    @Autowired
    private TorneoRepository torneoRepository;

    // inicializa la fila de un equipo en la tabla cuando se aprueba su solicitud
    @Transactional
    public void inicializarEquipo(Long equipoId, Long torneoId) {
        tablaRepository.findByEquipoIdAndTorneoId(equipoId, torneoId)
            .ifPresent(t -> { throw new IllegalStateException("El equipo ya está en la tabla"); });

        Equipo equipo = equipoRepository.findById(equipoId)
            .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));
        Torneo torneo = torneoRepository.findById(torneoId)
            .orElseThrow(() -> new RuntimeException("Torneo no encontrado"));

        TablaPosiciones fila = new TablaPosiciones();
        fila.setEquipo(equipo);
        fila.setTorneo(torneo);
        tablaRepository.save(fila);
    }

    // actualiza la tabla cuando se registra un resultado
    @Transactional
    public void actualizarTabla(Partido partido, int golesLocalAnt, int golesVisitanteAnt, boolean teniaMarcador) {
        Long torneoId = partido.getTorneo().getId();
        Long localId = partido.getEquipoLocal().getId();
        Long visitanteId = partido.getEquipoVisitante().getId();
        int golesLocal = partido.getGolesLocal();
        int golesVisitante = partido.getGolesVisitante();

        TablaPosiciones local = tablaRepository
            .findByEquipoIdAndTorneoId(localId, torneoId)
            .orElseThrow(() -> new RuntimeException("Equipo local no está en la tabla"));

        TablaPosiciones visitante = tablaRepository
            .findByEquipoIdAndTorneoId(visitanteId, torneoId)
            .orElseThrow(() -> new RuntimeException("Equipo visitante no está en la tabla"));

        // si ya tenía marcador, revierte el resultado anterior
        if (teniaMarcador) {
            local.setPartidosJugados(local.getPartidosJugados() - 1);
            local.setGolesFavor(local.getGolesFavor() - golesLocalAnt);
            local.setGolesContra(local.getGolesContra() - golesVisitanteAnt);
            visitante.setPartidosJugados(visitante.getPartidosJugados() - 1);
            visitante.setGolesFavor(visitante.getGolesFavor() - golesVisitanteAnt);
            visitante.setGolesContra(visitante.getGolesContra() - golesLocalAnt);

            if (golesLocalAnt > golesVisitanteAnt) {
                local.setGanados(local.getGanados() - 1);
                local.setPuntos(local.getPuntos() - 3);
                visitante.setPerdidos(visitante.getPerdidos() - 1);
            } else if (golesLocalAnt < golesVisitanteAnt) {
                visitante.setGanados(visitante.getGanados() - 1);
                visitante.setPuntos(visitante.getPuntos() - 3);
                local.setPerdidos(local.getPerdidos() - 1);
            } else {
                local.setEmpatados(local.getEmpatados() - 1);
                local.setPuntos(local.getPuntos() - 1);
                visitante.setEmpatados(visitante.getEmpatados() - 1);
                visitante.setPuntos(visitante.getPuntos() - 1);
            }
        }

        // suma el nuevo resultado
        local.setPartidosJugados(local.getPartidosJugados() + 1);
        local.setGolesFavor(local.getGolesFavor() + golesLocal);
        local.setGolesContra(local.getGolesContra() + golesVisitante);
        local.setDiferenciaGoles(local.getGolesFavor() - local.getGolesContra());

        visitante.setPartidosJugados(visitante.getPartidosJugados() + 1);
        visitante.setGolesFavor(visitante.getGolesFavor() + golesVisitante);
        visitante.setGolesContra(visitante.getGolesContra() + golesLocal);
        visitante.setDiferenciaGoles(visitante.getGolesFavor() - visitante.getGolesContra());

        if (golesLocal > golesVisitante) {
            local.setGanados(local.getGanados() + 1);
            local.setPuntos(local.getPuntos() + 3);
            visitante.setPerdidos(visitante.getPerdidos() + 1);
        } else if (golesLocal < golesVisitante) {
            visitante.setGanados(visitante.getGanados() + 1);
            visitante.setPuntos(visitante.getPuntos() + 3);
            local.setPerdidos(local.getPerdidos() + 1);
        } else {
            local.setEmpatados(local.getEmpatados() + 1);
            local.setPuntos(local.getPuntos() + 1);
            visitante.setEmpatados(visitante.getEmpatados() + 1);
            visitante.setPuntos(visitante.getPuntos() + 1);
        }

        tablaRepository.save(local);
        tablaRepository.save(visitante);
    }

    public List<TablaPosiciones> getTablaByTorneo(Long torneoId) {
        return tablaRepository.findByTorneoIdOrderByPuntosDescGolesFavorDesc(torneoId);
    }
}