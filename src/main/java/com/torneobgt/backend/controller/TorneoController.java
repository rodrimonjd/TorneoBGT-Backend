package com.torneobgt.backend.controller;

import com.torneobgt.backend.dto.TorneoDTO;
import com.torneobgt.backend.model.Torneo;
import com.torneobgt.backend.service.TorneoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/torneo")
@CrossOrigin(origins = "http://localhost:8080")
public class TorneoController {

    @Autowired
    private TorneoService torneoService;

    @PostMapping("/crear")
    public ResponseEntity<?> crearTorneo(
            @RequestBody TorneoDTO request,
            @RequestHeader("X-User-Email") String email) {
        try {
            Torneo saved = torneoService.crearTorneo(request, email);
            return ResponseEntity.ok(Map.of(
                "message", "Torneo creado exitosamente",
                "id", saved.getId()
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Estado inválido: " + e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping("/publicos")
    public ResponseEntity<?> getTorneosPublicos() {
        return ResponseEntity.ok(torneoService.getTorneosPublicos());
    }

    @GetMapping("/mis-torneos")
    public ResponseEntity<?> getMisTorneos(
            @RequestHeader("X-User-Email") String email) {
        return ResponseEntity.ok(torneoService.getMisTorneos(email));
    }
}