package com.torneobgt.backend.controller;

import com.torneobgt.backend.dto.EquipoDTO;
import com.torneobgt.backend.model.Equipo;
import com.torneobgt.backend.service.EquipoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/equipo")
@CrossOrigin(origins = "http://localhost:8080")
public class EquipoController {

    @Autowired
    private EquipoService equipoService;

    @PostMapping("/crear")
    public ResponseEntity<?> crearEquipo(
            @RequestBody EquipoDTO request,
            @RequestHeader("X-User-Email") String email) { // temporal hasta conectar JWT
        try {
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
}