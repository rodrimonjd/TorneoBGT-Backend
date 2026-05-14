package com.torneobgt.backend.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.torneobgt.backend.model.Solicitud;
import com.torneobgt.backend.service.SolicitudService;

@RestController
@RequestMapping("/api/solicitud")
@CrossOrigin(origins = "http://localhost:8080")
public class SolicitudController {

    @Autowired
    private SolicitudService solicitudService;

    // usuario solicita inscripcion
    @PostMapping("/enviar/{torneoId}")
    public ResponseEntity<?> enviarSolicitud(
            @PathVariable Long torneoId) {
        try {
        	String email = SecurityContextHolder.getContext().getAuthentication().getName();
            Solicitud saved = solicitudService.enviarSolicitud(torneoId, email);
            return ResponseEntity.ok(Map.of(
                "message", "Solicitud enviada exitosamente",
                "id", saved.getId()
            ));
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("message", e.getMessage()));
        }
    }

    // lider ve solicitudes de su torneo
    @GetMapping("/torneo/{torneoId}")
    public ResponseEntity<?> getSolicitudes(@PathVariable Long torneoId) {
        return ResponseEntity.ok(solicitudService.getSolicitudesByTorneo(torneoId));
    }

    // lider aprueba o rechaza
    @PutMapping("/responder/{solicitudId}")
    public ResponseEntity<?> responderSolicitud(
            @PathVariable Long solicitudId,
            @RequestBody Map<String, String> body) {
        try {
            Solicitud updated = solicitudService.responderSolicitud(
                solicitudId, body.get("estado"));
            return ResponseEntity.ok(Map.of(
                "message", "Solicitud actualizada",
                "estado", updated.getEstado().name()
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", e.getMessage()));
        }
    }
}
