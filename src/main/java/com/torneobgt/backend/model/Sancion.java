package com.torneobgt.backend.model;

import java.time.LocalDateTime;

import com.torneobgt.backend.model.enums.TipoSancion;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "sanciones")
public class Sancion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "jugador_id", nullable = false)
    private Jugador jugador;

    @ManyToOne
    @JoinColumn(name = "partido_id", nullable = false)
    private Partido partido;

    @ManyToOne
    @JoinColumn(name = "torneo_id", nullable = false)
    private Torneo torneo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoSancion tipo;

    @Column(nullable = false)
    private Integer partidosSuspendido = 0;

    @Column(name = "cumplida", nullable = false)
    private Boolean cumplida = false;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Jugador getJugador() {
		return jugador;
	}

	public void setJugador(Jugador jugador) {
		this.jugador = jugador;
	}

	public Partido getPartido() {
		return partido;
	}

	public void setPartido(Partido partido) {
		this.partido = partido;
	}

	public Torneo getTorneo() {
		return torneo;
	}

	public void setTorneo(Torneo torneo) {
		this.torneo = torneo;
	}

	public TipoSancion getTipo() {
		return tipo;
	}

	public void setTipo(TipoSancion tipo) {
		this.tipo = tipo;
	}

	public Integer getPartidosSuspendido() {
		return partidosSuspendido;
	}

	public void setPartidosSuspendido(Integer partidosSuspendido) {
		this.partidosSuspendido = partidosSuspendido;
	}

	public Boolean getCumplida() {
		return cumplida;
	}

	public void setCumplida(Boolean cumplida) {
		this.cumplida = cumplida;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
}