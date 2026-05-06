package com.torneobgt.backend.dto;

import java.time.LocalDate;

public class TorneoDTO {

	private String nombre;
	private String deporte;
	private String estado;
	private Integer maxEquipos;
	private LocalDate fechaInicio;
	private String ubicacion;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDeporte() {
		return deporte;
	}

	public void setDeporte(String deporte) {
		this.deporte = deporte;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Integer getMaxEquipos() {
		return maxEquipos;
	}

	public void setMaxEquipos(Integer maxEquipos) {
		this.maxEquipos = maxEquipos;
	}

	public LocalDate getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(LocalDate fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	// getters y setters

}