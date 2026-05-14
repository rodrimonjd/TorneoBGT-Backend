package com.torneobgt.backend.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.torneobgt.backend.dto.EquipoDTO;
import com.torneobgt.backend.model.Equipo;
import com.torneobgt.backend.service.EquipoService;

@RestController
@RequestMapping("/api/equipo")
@CrossOrigin(origins = "http://localhost:8080")
public class EquipoController {

    @Autowired
    private EquipoService equipoService;

    @PostMapping("/crear")
    public ResponseEntity<?> crearEquipo(@RequestBody EquipoDTO request) {
        try {
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            Equipo saved = equipoService.crearEquipo(request, email);
            return ResponseEntity.ok(Map.of(
                "message", "Equipo creado exitosamente",
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
    
    @GetMapping("/mi-equipo")
    public ResponseEntity<?> getMiEquipo() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Equipo> equipos = equipoService.getEquiposByEmail(email);
        if (equipos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(equipos.get(0));
    }
}