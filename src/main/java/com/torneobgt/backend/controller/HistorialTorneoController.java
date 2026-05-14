package com.torneobgt.backend.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.torneobgt.backend.model.HistorialTorneo;
import com.torneobgt.backend.service.HistorialTorneoService;

@RestController
@RequestMapping("/api/historial")
@CrossOrigin(origins = "http://localhost:8080")
public class HistorialTorneoController {

    @Autowired
    private HistorialTorneoService historialService;

    @PutMapping("/finalizar/{torneoId}")
    public ResponseEntity<?> finalizarTorneo(
            @PathVariable Long torneoId){
        try {
        	String email = SecurityContextHolder.getContext().getAuthentication().getName();
            HistorialTorneo historial = historialService.finalizarTorneo(torneoId, email);
            return ResponseEntity.ok(Map.of(
                "message", "Torneo finalizado y archivado",
                "historialId", historial.getId()
            ));
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping("/torneo/{torneoId}")
    public ResponseEntity<?> getHistorial(@PathVariable Long torneoId) {
        try {
            return ResponseEntity.ok(historialService.getHistorialByTorneo(torneoId));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping("/mis-torneos")
    public ResponseEntity<?> getHistorialLider() {
    	 String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(historialService.getHistorialByLider(email));
    }
}