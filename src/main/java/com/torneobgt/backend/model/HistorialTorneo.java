package com.torneobgt.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "historial_torneos")
public class HistorialTorneo {

    @Id
    private String id;

    private Long torneoId;
    private String nombreTorneo;
    private String deporte;
    private String ubicacion;
    private LocalDateTime fechaCierre;
    private String liderEmail;

    private List<EquipoSnapshot> tablaPosiciones;
    private List<PartidoSnapshot> partidos;

    public static class EquipoSnapshot {
        private Long equipoId;
        private String nombreEquipo;
        private Integer puntos;
        private Integer ganados;
        private Integer empatados;
        private Integer perdidos;
        private Integer golesFavor;
        private Integer golesContra;
        private Integer diferenciaGoles;
        private Integer partidosJugados;
        
		public Long getEquipoId() {
			return equipoId;
		}
		public void setEquipoId(Long equipoId) {
			this.equipoId = equipoId;
		}
		public String getNombreEquipo() {
			return nombreEquipo;
		}
		public void setNombreEquipo(String nombreEquipo) {
			this.nombreEquipo = nombreEquipo;
		}
		public Integer getPuntos() {
			return puntos;
		}
		public void setPuntos(Integer puntos) {
			this.puntos = puntos;
		}
		public Integer getGanados() {
			return ganados;
		}
		public void setGanados(Integer ganados) {
			this.ganados = ganados;
		}
		public Integer getEmpatados() {
			return empatados;
		}
		public void setEmpatados(Integer empatados) {
			this.empatados = empatados;
		}
		public Integer getPerdidos() {
			return perdidos;
		}
		public void setPerdidos(Integer perdidos) {
			this.perdidos = perdidos;
		}
		public Integer getGolesFavor() {
			return golesFavor;
		}
		public void setGolesFavor(Integer golesFavor) {
			this.golesFavor = golesFavor;
		}
		public Integer getGolesContra() {
			return golesContra;
		}
		public void setGolesContra(Integer golesContra) {
			this.golesContra = golesContra;
		}
		public Integer getDiferenciaGoles() {
			return diferenciaGoles;
		}
		public void setDiferenciaGoles(Integer diferenciaGoles) {
			this.diferenciaGoles = diferenciaGoles;
		}
		public Integer getPartidosJugados() {
			return partidosJugados;
		}
		public void setPartidosJugados(Integer partidosJugados) {
			this.partidosJugados = partidosJugados;
		}

        
    }

    public static class PartidoSnapshot {
        private Long partidoId;
        private String equipoLocal;
        private String equipoVisitante;
        private Integer golesLocal;
        private Integer golesVisitante;
        private String lugar;
        private String estado;
        
		public Long getPartidoId() {
			return partidoId;
		}
		public void setPartidoId(Long partidoId) {
			this.partidoId = partidoId;
		}
		public String getEquipoLocal() {
			return equipoLocal;
		}
		public void setEquipoLocal(String equipoLocal) {
			this.equipoLocal = equipoLocal;
		}
		public String getEquipoVisitante() {
			return equipoVisitante;
		}
		public void setEquipoVisitante(String equipoVisitante) {
			this.equipoVisitante = equipoVisitante;
		}
		public Integer getGolesLocal() {
			return golesLocal;
		}
		public void setGolesLocal(Integer golesLocal) {
			this.golesLocal = golesLocal;
		}
		public Integer getGolesVisitante() {
			return golesVisitante;
		}
		public void setGolesVisitante(Integer golesVisitante) {
			this.golesVisitante = golesVisitante;
		}
		public String getLugar() {
			return lugar;
		}
		public void setLugar(String lugar) {
			this.lugar = lugar;
		}
		public String getEstado() {
			return estado;
		}
		public void setEstado(String estado) {
			this.estado = estado;
		}

        
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getTorneoId() {
		return torneoId;
	}

	public void setTorneoId(Long torneoId) {
		this.torneoId = torneoId;
	}

	public String getNombreTorneo() {
		return nombreTorneo;
	}

	public void setNombreTorneo(String nombreTorneo) {
		this.nombreTorneo = nombreTorneo;
	}

	public String getDeporte() {
		return deporte;
	}

	public void setDeporte(String deporte) {
		this.deporte = deporte;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	public LocalDateTime getFechaCierre() {
		return fechaCierre;
	}

	public void setFechaCierre(LocalDateTime fechaCierre) {
		this.fechaCierre = fechaCierre;
	}

	public String getLiderEmail() {
		return liderEmail;
	}

	public void setLiderEmail(String liderEmail) {
		this.liderEmail = liderEmail;
	}

	public List<EquipoSnapshot> getTablaPosiciones() {
		return tablaPosiciones;
	}

	public void setTablaPosiciones(List<EquipoSnapshot> tablaPosiciones) {
		this.tablaPosiciones = tablaPosiciones;
	}

	public List<PartidoSnapshot> getPartidos() {
		return partidos;
	}

	public void setPartidos(List<PartidoSnapshot> partidos) {
		this.partidos = partidos;
	}

    
}