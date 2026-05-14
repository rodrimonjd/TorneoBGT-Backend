package com.torneobgt.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Document(collection = "estadisticas_partidos")
public class EstadisticaPartido {

    @Id
    private String id;

    private Long partidoId;
    private Long torneoId;
    private String notas;

    private List<Gol> goles;
    private List<Tarjeta> tarjetas;

    public static class Gol {
        private Long equipoId;
        private Long jugadorId;
        private Integer minuto;
        private Long asistenciaJugadorId;
        
		public Long getEquipoId() {
			return equipoId;
		}
		public void setEquipoId(Long equipoId) {
			this.equipoId = equipoId;
		}
		public Long getJugadorId() {
			return jugadorId;
		}
		public void setJugadorId(Long jugadorId) {
			this.jugadorId = jugadorId;
		}
		public Integer getMinuto() {
			return minuto;
		}
		public void setMinuto(Integer minuto) {
			this.minuto = minuto;
		}
		public Long getAsistenciaJugadorId() {
			return asistenciaJugadorId;
		}
		public void setAsistenciaJugadorId(Long asistenciaJugadorId) {
			this.asistenciaJugadorId = asistenciaJugadorId;
		}

        
    }

    public static class Tarjeta {
        private Long equipoId;
        private Long jugadorId;
        private Integer minuto;
        private String tipo; // AMARILLA, ROJA
        
		public Long getEquipoId() {
			return equipoId;
		}
		public void setEquipoId(Long equipoId) {
			this.equipoId = equipoId;
		}
		public Long getJugadorId() {
			return jugadorId;
		}
		public void setJugadorId(Long jugadorId) {
			this.jugadorId = jugadorId;
		}
		public Integer getMinuto() {
			return minuto;
		}
		public void setMinuto(Integer minuto) {
			this.minuto = minuto;
		}
		public String getTipo() {
			return tipo;
		}
		public void setTipo(String tipo) {
			this.tipo = tipo;
		}

        
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getPartidoId() {
		return partidoId;
	}

	public void setPartidoId(Long partidoId) {
		this.partidoId = partidoId;
	}

	public Long getTorneoId() {
		return torneoId;
	}

	public void setTorneoId(Long torneoId) {
		this.torneoId = torneoId;
	}

	public String getNotas() {
		return notas;
	}

	public void setNotas(String notas) {
		this.notas = notas;
	}

	public List<Gol> getGoles() {
		return goles;
	}

	public void setGoles(List<Gol> goles) {
		this.goles = goles;
	}

	public List<Tarjeta> getTarjetas() {
		return tarjetas;
	}

	public void setTarjetas(List<Tarjeta> tarjetas) {
		this.tarjetas = tarjetas;
	}

}