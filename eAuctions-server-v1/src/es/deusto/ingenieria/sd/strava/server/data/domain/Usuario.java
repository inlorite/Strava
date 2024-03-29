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
import javax.persistence.OneToMany;

@Entity
public class Usuario {
	private String nombre;
	@Id
	private String email;
	private Date fechaNacimiento;
	private float peso;
	private float altura;
	private int frecuenciaCardiacaMax;
	private int frecuenciaCardiacaReposo;
	private String contrasena;
	private String tipoServicio;
	@OneToMany(mappedBy = "creador", fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
	private List<SesionEntrenamiento> sesionesEntrenamiento = new ArrayList<>();
	@OneToMany(mappedBy = "creador", fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
	private List<Reto> retosCreados = new ArrayList<>();
	@ManyToMany
    @JoinTable(name = "usuario_reto",joinColumns = @JoinColumn(name = "usuario_email"),inverseJoinColumns = @JoinColumn(name = "reto_nombre"))
	private List<Reto> retosApuntados = new ArrayList<>();
	
	public List<Reto> getRetosCreados() {
		return retosCreados;
	}

	public void setRetosCreados(List<Reto> retosCreados) {
		this.retosCreados = retosCreados;
	}

	public List<Reto> getRetosApuntados() {
		return retosApuntados;
	}

	public void setRetosApuntados(List<Reto> retosApuntados) {
		this.retosApuntados = retosApuntados;
	}
	
	public String getTipoServicio() {
		return tipoServicio;
	}

	public void setTipoServicio(String tipoServicio) {
		this.tipoServicio = tipoServicio;
	}

	public List<SesionEntrenamiento> getSesionesEntrenamiento() {
		return sesionesEntrenamiento;
	}

	public void setSesionesEntrenamiento(List<SesionEntrenamiento> sesionesEntrenamiento) {
		this.sesionesEntrenamiento = sesionesEntrenamiento;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public float getPeso() {
		return peso;
	}

	public void setPeso(float peso) {
		this.peso = peso;
	}

	public float getAltura() {
		return altura;
	}

	public void setAltura(float altura) {
		this.altura = altura;
	}

	public int getFrecuenciaCardiacaMax() {
		return frecuenciaCardiacaMax;
	}

	public void setFrecuenciaCardiacaMax(int frecuenciaCardiacaMax) {
		this.frecuenciaCardiacaMax = frecuenciaCardiacaMax;
	}

	public int getFrecuenciaCardiacaReposo() {
		return frecuenciaCardiacaReposo;
	}

	public void setFrecuenciaCardiacaReposo(int frecuenciaCardiacaReposo) {
		this.frecuenciaCardiacaReposo = frecuenciaCardiacaReposo;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public boolean checkContrasena(String contrasena) {
		return this.contrasena.equals(contrasena);
	}

	@Override
	public int hashCode() {
		return Objects.hash(email, nombre);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(email, other.email) && Objects.equals(nombre, other.nombre);
	}

	@Override
	public String toString() {
		return "Usuario [nombre=" + nombre + ", email=" + email + ", fechaNacimiento=" + fechaNacimiento + ", peso="
				+ peso + ", altura=" + altura + ", frecuenciaCardiacaMax=" + frecuenciaCardiacaMax
				+ ", frecuenciaCardiacaReposo=" + frecuenciaCardiacaReposo + ", contrasena=" + contrasena
				+ ", tipoServicio=" + tipoServicio + "]";
	}
	
}