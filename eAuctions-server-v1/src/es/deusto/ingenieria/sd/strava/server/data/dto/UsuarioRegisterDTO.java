package es.deusto.ingenieria.sd.strava.server.data.dto;

import java.io.Serializable;
import java.util.Date;

//This class implements DTO pattern
public class UsuarioRegisterDTO implements Serializable {
	// This attribute is needed to implement the "Serializable" interface.
	private static final long serialVersionUID = 1L;
	private String nombre;
	private String email;
	private Date fechaNacimiento;
	private float peso;
	private float altura;
	private int frecuenciaCardiacaMax;
	private int frecuenciaCardiacaReposo;
	private String contrasena;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}