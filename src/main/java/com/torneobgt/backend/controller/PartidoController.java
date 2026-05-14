package com.torneobgt.backend.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.torneobgt.backend.dto.PartidoDTO;
import com.torneobgt.backend.dto.request.ActualizarMarcadorRequest;
import com.torneobgt.backend.model.Partido;
import com.torneobgt.backend.service.PartidoService;

@RestController
@RequestMapping("/api/partido")
@CrossOrigin(origins = "http://localhost:8080")
public class PartidoController {

    @Autowired
    private PartidoService partidoService;

    @PostMapping("/crear")
    public ResponseEntity<?> crearPartido(@RequestBody PartidoDTO request) {
        try {
            Partido saved = partidoService.crearPartido(request);
            return ResponseEntity.ok(Map.of(
                "message", "Partido programado exitosamente",
                "id", saved.getId()
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("message", e.getMessage()));
        }
    }

    @PutMapping("/marcador/{partidoId}")
    public ResponseEntity<?> actualizarMarcador(
            @PathVariable Long partidoId,
            @RequestBody ActualizarMarcadorRequest request) {
        try {
            Partido updated = partidoService.actualizarMarcador(partidoId, request);
            return ResponseEntity.ok(Map.of(
                "message", "Marcador actualizado",
                "golesLocal", updated.getGolesLocal(),
                "golesVisitante", updated.getGolesVisitante(),
                "estado", updated.getEstado().name()
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping("/torneo/{torneoId}")
    public ResponseEntity<?> getPartidos(@PathVariable Long torneoId) {
        return ResponseEntity.ok(partidoService.getPartidosByTorneo(torneoId));
    }
    
    @PutMapping("/estado/{partidoId}")
    public ResponseEntity<?> cambiarEstado(
            @PathVariable Long partidoId,
            @RequestBody Map<String, String> body) {
        try {
            Partido updated = partidoService.cambiarEstado(
                partidoId, body.get("estado"));
            return ResponseEntity.ok(Map.of(
                "message", "Estado actualizado",
                "estado", updated.getEstado().name()
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", e.getMessage()));
        }
    }
}