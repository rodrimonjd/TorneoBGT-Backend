package com.torneobgt.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.torneobgt.backend.model.Equipo;

public interface EquipoRepository extends JpaRepository<Equipo, Long> {
	List<Equipo> findByDuenoEmail(String email);
}