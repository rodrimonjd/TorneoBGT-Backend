package com.torneobgt.backend.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.torneobgt.backend.model.EstadisticaPartido;
import com.torneobgt.backend.model.enums.EstadoPartido;
import com.torneobgt.backend.service.EstadisticaPartidoService;
import com.torneobgt.backend.service.PartidoService;

@RestController
@RequestMapping("/api/estadistica")
@CrossOrigin(origins = "http://localhost:8080")
public class EstadisticaPartidoController {

    @Autowired
    private EstadisticaPartidoService estadisticaService;

    @Autowired
    private PartidoService partidoService;

    @PostMapping("/guardar")
    public ResponseEntity<?> guardarEstadisticas(
            @RequestBody EstadisticaPartido estadistica) {
        try {
            // verifica que el partido esté finalizado
            partidoService.getPartidoById(estadistica.getPartidoId())
                .filter(p -> p.getEstado() == EstadoPartido.FINALIZADO)
                .orElseThrow(() -> new IllegalStateException(
                    "El partido debe estar finalizado para ingresar estadísticas"));

            EstadisticaPartido saved = estadisticaService
                .guardarEstadisticas(estadistica);
            return ResponseEntity.ok(Map.of(
                "message", "Estadísticas guardadas",
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

    @GetMapping("/partido/{partidoId}")
    public ResponseEntity<?> getEstadisticas(@PathVariable Long partidoId) {
        try {
            return ResponseEntity.ok(
                estadisticaService.getEstadisticasByPartido(partidoId));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", e.getMessage()));
        }
    }
}