package com.torneobgt.backend.service;

import com.torneobgt.backend.dto.EquipoDTO;
import com.torneobgt.backend.model.*;
import com.torneobgt.backend.repository.EquipoRepository;
import com.torneobgt.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipoService {

    @Autowired
    private EquipoRepository equipoRepository;

    @Autowired
    private UserRepository userRepository;

    public Equipo crearEquipo(EquipoDTO request, String email) {

        // valida cantidad de jugadores
        int cantidad = request.getJugadores().size();
        if (cantidad < 5 || cantidad > 12) {
            throw new IllegalArgumentException(
                "El equipo debe tener entre 5 y 12 jugadores");
        }

        // busca el usuario por email (viene del token después)
        User dueno = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // construye el equipo
        Equipo equipo = new Equipo();
        equipo.setNombre(request.getNombre());
        equipo.setDueno(dueno);

        // construye los jugadores y los asocia al equipo
        List<Jugador> jugadores = request.getJugadores().stream()
            .map(j -> {
                Jugador jugador = new Jugador();
                jugador.setNombre(j.getNombre());
                jugador.setCedula(j.getCedula());
                jugador.setTelefono(j.getTelefono());
                jugador.setNumeroCamiseta(j.getNumeroCamiseta());
                jugador.setEquipo(equipo);
                return jugador;
            }).toList();

        equipo.setJugadores(jugadores);
        return equipoRepository.save(equipo);
    }
}