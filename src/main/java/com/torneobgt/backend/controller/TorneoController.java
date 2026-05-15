package com.torneobgt.backend.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.torneobgt.backend.dto.TorneoDTO;
import com.torneobgt.backend.model.Torneo;
import com.torneobgt.backend.repository.TorneoRepository;
import com.torneobgt.backend.service.TorneoService;

@RestController
@RequestMapping("/api/torneo")
@CrossOrigin(origins = "http://localhost:8080")
public class TorneoController {

    @Autowired
    private TorneoService torneoService;
    
    @Autowired
    private TorneoRepository torneoRepository;

    @PostMapping("/crear")
    public ResponseEntity<?> crearTorneo(
            @RequestBody TorneoDTO request) {
        try {
        	String email = SecurityContextHolder.getContext().getAuthentication().getName();
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
    public ResponseEntity<?> getMisTorneos() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(torneoService.getMisTorneos(email));
    }
    
    @GetMapping("/{torneoId}")
    public ResponseEntity<?> getTorneoById(@PathVariable Long torneoId) {
        return torneoRepository.findById(torneoId)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}