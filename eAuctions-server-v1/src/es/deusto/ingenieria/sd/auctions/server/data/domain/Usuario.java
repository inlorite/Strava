package es.deusto.ingenieria.sd.auctions.server.data.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Usuario {
	private String nombre;
	private String email;
	private Date fechaNacimiento;
	private float peso;
	private float altura;
	private int frecuenciaCardiacaMax;
	private int frecuenciaCardiacaReposo;
	private String contrasena;

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
		return Objects.hash(altura, contrasena, email, fechaNacimiento, frecuenciaCardiacaMax, frecuenciaCardiacaReposo,
				nombre, peso);
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
		return Float.floatToIntBits(altura) == Float.floatToIntBits(other.altura)
				&& Objects.equals(contrasena, other.contrasena) && Objects.equals(email, other.email)
				&& Objects.equals(fechaNacimiento, other.fechaNacimiento)
				&& frecuenciaCardiacaMax == other.frecuenciaCardiacaMax
				&& frecuenciaCardiacaReposo == other.frecuenciaCardiacaReposo && Objects.equals(nombre, other.nombre)
				&& Float.floatToIntBits(peso) == Float.floatToIntBits(other.peso);
	}

	@Override
	public String toString() {
		return "Usuario [nombre=" + nombre + ", email=" + email + ", fechaNacimiento=" + fechaNacimiento + ", peso="
				+ peso + ", altura=" + altura + ", frecuenciaCardiacaMax=" + frecuenciaCardiacaMax
				+ ", frecuenciaCardiacaReposo=" + frecuenciaCardiacaReposo + ", contrasena=" + contrasena + "]";
	}
}