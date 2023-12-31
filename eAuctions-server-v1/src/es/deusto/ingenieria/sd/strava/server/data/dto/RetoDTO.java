package es.deusto.ingenieria.sd.strava.server.data.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import es.deusto.ingenieria.sd.strava.server.data.domain.TipoReto;
import es.deusto.ingenieria.sd.strava.server.data.domain.Usuario;

public class RetoDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private String nombre;
	private Date fechaInicio;
	private Date fechaFin;
	private float distancia;
	private float tiempo;
	private UsuarioDTO creador;
	private List<UsuarioDTO> participantes = new ArrayList<>();
	private String tipoReto;

	public String getTipoReto() {
		return tipoReto;
	}

	public void setTipoReto(String tipoReto) {
		this.tipoReto = tipoReto;
	}

	public List<UsuarioDTO> getParticipantes() {
		return participantes;
	}

	public void setParticipantes(List<UsuarioDTO> participantes) {
		this.participantes = participantes;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public float getDistancia() {
		return distancia;
	}

	public void setDistancia(float distancia) {
		this.distancia = distancia;
	}

	public float getTiempo() {
		return tiempo;
	}

	public void setTiempo(float tiempo) {
		this.tiempo = tiempo;
	}
	
	public UsuarioDTO getCreador() {
		return creador;
	}

	public void setCreador(UsuarioDTO creador) {
		this.creador = creador;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "RetoDTO [nombre=" + nombre + ", fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin + ", distancia="
				+ distancia + ", tiempo=" + tiempo + ", creador=" + creador + ", participantes=" + participantes
				+ ", tipoReto=" + tipoReto + "]";
	}
	
}
