package com.torneobgt.backend.service;

import com.torneobgt.backend.model.*;
import com.torneobgt.backend.model.enums.EstadoSolicitud;
import com.torneobgt.backend.model.enums.EstadoTorneo;
import com.torneobgt.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SolicitudService {

    @Autowired
    private SolicitudRepository solicitudRepository;

    @Autowired
    private EquipoRepository equipoRepository;

    @Autowired
    private TorneoRepository torneoRepository;
    
    @Autowired
    private TablaPosicionesService tablaPosicionesService;


    public Solicitud enviarSolicitud(Long torneoId, String emailUsuario) {

     
        Equipo equipo = equipoRepository.findByDuenoEmail(emailUsuario)
            .orElseThrow(() -> new RuntimeException("No tienes un equipo registrado"));

        Torneo torneo = torneoRepository.findById(torneoId)
            .orElseThrow(() -> new RuntimeException("Torneo no encontrado"));

       
        if (torneo.getEstado() != EstadoTorneo.INSCRIPCION_ABIERTA) {
            throw new IllegalStateException("El torneo no está abierto para inscripciones");
        }

   
        solicitudRepository.findByEquipoIdAndTorneoId(equipo.getId(), torneoId)
            .ifPresent(s -> { throw new IllegalStateException("Ya enviaste una solicitud a este torneo"); });

      
        long aprobadas = solicitudRepository
            .findByTorneoIdAndEstado(torneoId, EstadoSolicitud.APROBADA).size();
        if (aprobadas >= torneo.getMaxEquipos()) {
            throw new IllegalStateException("El torneo ya alcanzó el máximo de equipos");
        }

        Solicitud solicitud = new Solicitud();
        solicitud.setEquipo(equipo);
        solicitud.setTorneo(torneo);
        solicitud.setEstado(EstadoSolicitud.PENDIENTE);

        return solicitudRepository.save(solicitud);
    }


    public List<Solicitud> getSolicitudesByTorneo(Long torneoId) {
        return solicitudRepository.findByTorneoId(torneoId);
    }
    
    public Solicitud responderSolicitud(Long solicitudId, String nuevoEstado) {
        Solicitud solicitud = solicitudRepository.findById(solicitudId)
            .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        solicitud.setEstado(EstadoSolicitud.valueOf(nuevoEstado.toUpperCase()));

        if (solicitud.getEstado() == EstadoSolicitud.APROBADA) {
            tablaPosicionesService.inicializarEquipo(
                solicitud.getEquipo().getId(),
                solicitud.getTorneo().getId()
            );
        }

        return solicitudRepository.save(solicitud);
    }
}