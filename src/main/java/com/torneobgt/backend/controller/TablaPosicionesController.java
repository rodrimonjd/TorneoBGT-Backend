package com.torneobgt.backend.controller;

import com.torneobgt.backend.service.TablaPosicionesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tabla")
@CrossOrigin(origins = "http://localhost:8080")
public class TablaPosicionesController {

    @Autowired
    private TablaPosicionesService tablaPosicionesService;

    @GetMapping("/torneo/{torneoId}")
    public ResponseEntity<?> getTabla(@PathVariable Long torneoId) {
        return ResponseEntity.ok(
            tablaPosicionesService.getTablaByTorneo(torneoId));
    }
}