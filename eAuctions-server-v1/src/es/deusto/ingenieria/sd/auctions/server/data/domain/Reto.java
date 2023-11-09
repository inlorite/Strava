package es.deusto.ingenieria.sd.auctions.server.data.domain;

import java.util.Date;
import java.util.Objects;

public class Reto {
	private String nombre;
	private Date fechaInicio;
	private Date fechaFin;
	private float distancia;
	private float tiempo;
	private Usuario creador;

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

	public Usuario getCreador() {
		return creador;
	}

	public void setCreador(Usuario creador) {
		this.creador = creador;
	}

	@Override
	public int hashCode() {
		return Objects.hash(creador, distancia, fechaFin, fechaInicio, nombre, tiempo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reto other = (Reto) obj;
		return Objects.equals(creador, other.creador)
				&& Float.floatToIntBits(distancia) == Float.floatToIntBits(other.distancia)
				&& Objects.equals(fechaFin, other.fechaFin) && Objects.equals(fechaInicio, other.fechaInicio)
				&& Objects.equals(nombre, other.nombre)
				&& Float.floatToIntBits(tiempo) == Float.floatToIntBits(other.tiempo);
	}

	@Override
	public String toString() {
		return "Reto [nombre=" + nombre + ", fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin + ", distancia="
				+ distancia + ", tiempo=" + tiempo + ", creador=" + creador + "]";
	}

}
