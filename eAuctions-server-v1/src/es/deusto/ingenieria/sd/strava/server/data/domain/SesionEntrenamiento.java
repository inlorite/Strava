package es.deusto.ingenieria.sd.strava.server.data.domain;

import java.util.Date;
import java.util.Objects;

import javax.swing.ImageIcon;

public class SesionEntrenamiento {
	private String titulo;
	private float distancia;
	private Date fechaInicio;
	private long horaInicio;
	private float duracion;
	private String deporte;
	private ImageIcon mapImg;

	public ImageIcon getMapImg() {
		return mapImg;
	}

	public void setMapImg(ImageIcon mapImg) {
		this.mapImg = mapImg;
	}

	public String getDeporte() {
		return deporte;
	}

	public void setDeporte(String deporte) {
		this.deporte = deporte;
	}

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

	@Override
	public int hashCode() {
		return Objects.hash(distancia, duracion, fechaInicio, horaInicio, titulo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SesionEntrenamiento other = (SesionEntrenamiento) obj;
		return Float.floatToIntBits(distancia) == Float.floatToIntBits(other.distancia)
				&& Float.floatToIntBits(duracion) == Float.floatToIntBits(other.duracion)
				&& Objects.equals(fechaInicio, other.fechaInicio) && horaInicio == other.horaInicio
				&& Objects.equals(titulo, other.titulo);
	}

	@Override
	public String toString() {
		return "SesionEntrenamiento [titulo=" + titulo + ", distancia=" + distancia + ", fechaInicio=" + fechaInicio
				+ ", horaInicio=" + horaInicio + ", duracion=" + duracion + ", deporte=" + deporte + "]";
	}

}
