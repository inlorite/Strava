package es.deusto.ingenieria.sd.auctions.server.data.dto;

import java.io.Serializable;
import java.util.Date;

public class SesionEntrenamientoDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private String titulo;
	private float distancia;
	private Date fechaInicio;
	private long horaInicio;
	private float duracion;

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public float getDistancia() {
		return distancia;
	}

	public void setDistancia(float distancia) {
		this.distancia = distancia;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public long getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(long horaInicio) {
		this.horaInicio = horaInicio;
	}

	public float getDuracion() {
		return duracion;
	}

	public void setDuracion(float duracion) {
		this.duracion = duracion;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "SesionEntrenamientoDTO [titulo=" + titulo + ", distancia=" + distancia + ", fechaInicio=" + fechaInicio
				+ ", horaInicio=" + horaInicio + ", duracion=" + duracion + "]";
	}
	
}
