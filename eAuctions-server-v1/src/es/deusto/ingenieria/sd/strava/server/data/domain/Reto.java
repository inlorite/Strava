package es.deusto.ingenieria.sd.strava.server.data.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Reto {
	@Id
	private String nombre;
	private Date fechaInicio;
	private Date fechaFin;
	private float distancia;
	private float tiempo;
	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
	private Usuario creador;
	@ManyToMany(mappedBy = "retosApuntados",fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
	private List<Usuario> participantes = new ArrayList<>();
	private String tipoReto;

	public String getTipoReto() {
		return tipoReto;
	}

	public void setTipoReto(String tipoReto) {
		this.tipoReto = tipoReto;
	}

	public List<Usuario> getParticipantes() {
		return participantes;
	}

	public void setParticipantes(List<Usuario> participantes) {
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
				+ distancia + ", tiempo=" + tiempo + ", creador=" + creador + ", participantes=" + participantes
				+ ", tipoReto=" + tipoReto + "]";
	}

}
