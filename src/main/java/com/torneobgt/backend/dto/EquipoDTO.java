package com.torneobgt.backend.dto;

import java.util.List;

public class EquipoDTO {

	private String nombre;
	private List<JugadorRequest> jugadores;

	public static class JugadorRequest {
		private String nombre;
		private String cedula;
		private String telefono;
		private Integer numeroCamiseta;

		public String getNombre() {
			return nombre;
		}

		public void setNombre(String nombre) {
			this.nombre = nombre;
		}

		public String getCedula() {
			return cedula;
		}

		public void setCedula(String cedula) {
			this.cedula = cedula;
		}

		public String getTelefono() {
			return telefono;
		}

		public void setTelefono(String telefono) {
			this.telefono = telefono;
		}

		public Integer getNumeroCamiseta() {
			return numeroCamiseta;
		}

		public void setNumeroCamiseta(Integer numeroCamiseta) {
			this.numeroCamiseta = numeroCamiseta;
		}

		// getters y setters
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<JugadorRequest> getJugadores() {
		return jugadores;
	}

	public void setJugadores(List<JugadorRequest> jugadores) {
		this.jugadores = jugadores;
	}

	// getters y setters

}