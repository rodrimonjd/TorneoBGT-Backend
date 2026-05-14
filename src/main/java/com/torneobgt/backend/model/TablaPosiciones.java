package com.torneobgt.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tabla_posiciones")
public class TablaPosiciones {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "equipo_id", nullable = false)
    private Equipo equipo;

    @ManyToOne
    @JoinColumn(name = "torneo_id", nullable = false)
    private Torneo torneo;

    @Column(nullable = false)
    private Integer puntos = 0;

    @Column(name = "goles_favor", nullable = false)
    private Integer golesFavor = 0;

    @Column(name = "goles_contra", nullable = false)
    private Integer golesContra = 0;

    @Column(name = "diferencia_goles", nullable = false)
    private Integer diferenciaGoles = 0;

    @Column(name = "partidos_jugados", nullable = false)
    private Integer partidosJugados = 0;

    @Column(nullable = false)
    private Integer ganados = 0;

    @Column(nullable = false)
    private Integer empatados = 0;

    @Column(nullable = false)
    private Integer perdidos = 0;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Equipo getEquipo() {
		return equipo;
	}

	public void setEquipo(Equipo equipo) {
		this.equipo = equipo;
	}

	public Torneo getTorneo() {
		return torneo;
	}

	public void setTorneo(Torneo torneo) {
		this.torneo = torneo;
	}

	public Integer getPuntos() {
		return puntos;
	}

	public void setPuntos(Integer puntos) {
		this.puntos = puntos;
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

    
}
