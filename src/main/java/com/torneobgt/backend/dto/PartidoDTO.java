package com.torneobgt.backend.dto;

import java.time.LocalDateTime;

public class PartidoDTO {
	
	private Long torneoId;
    private Long equipoLocalId;
    private Long equipoVisitanteId;
    private LocalDateTime fechaHora;
    private String lugar;
    
	public Long getTorneoId() {
		return torneoId;
	}
	public void setTorneoId(Long torneoId) {
		this.torneoId = torneoId;
	}
	public Long getEquipoLocalId() {
		return equipoLocalId;
	}
	public void setEquipoLocalId(Long equipoLocalId) {
		this.equipoLocalId = equipoLocalId;
	}
	public Long getEquipoVisitanteId() {
		return equipoVisitanteId;
	}
	public void setEquipoVisitanteId(Long equipoVisitanteId) {
		this.equipoVisitanteId = equipoVisitanteId;
	}
	public LocalDateTime getFechaHora() {
		return fechaHora;
	}
	public void setFechaHora(LocalDateTime fechaHora) {
		this.fechaHora = fechaHora;
	}
	public String getLugar() {
		return lugar;
	}
	public void setLugar(String lugar) {
		this.lugar = lugar;
	}
    
    
}
