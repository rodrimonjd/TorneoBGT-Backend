package com.torneobgt.backend.controller;

import com.torneobgt.backend.model.HistorialTorneo;
import com.torneobgt.backend.service.HistorialTorneoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/historial")
@CrossOrigin(origins = "http://localhost:8080")
public class HistorialTorneoController {

    @Autowired
    private HistorialTorneoService historialService;

    @PutMapping("/finalizar/{torneoId}")
    public ResponseEntity<?> finalizarTorneo(
            @PathVariable Long torneoId,
            @RequestHeader("X-User-Email") String email) {
        try {
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
    public ResponseEntity<?> getHistorialLider(
            @RequestHeader("X-User-Email") String email) {
        return ResponseEntity.ok(historialService.getHistorialByLider(email));
    }
}