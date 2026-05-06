package com.torneobgt.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.torneobgt.backend.model.Jugador;

public interface JugadorRepository extends JpaRepository<Jugador, Long> {
}