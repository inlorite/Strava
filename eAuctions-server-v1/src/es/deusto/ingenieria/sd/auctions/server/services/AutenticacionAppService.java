package es.deusto.ingenieria.sd.auctions.server.services;

import java.util.Date;

import es.deusto.ingenieria.sd.auctions.server.data.domain.Usuario;

//TODO: Implement Singleton Pattern
public class AutenticacionAppService {

	private static AutenticacionAppService instance;

	public Usuario login(String email, String password) {
		// TODO: Get User using DAO and check
		Usuario user = new Usuario();
		user.setEmail("test@gmail.com");
		user.setNombre("Test");
		// Generate the hash of the password
		String sha1 = org.apache.commons.codec.digest.DigestUtils.sha1Hex("test123");
		user.setContrasena(sha1);

		if (user.getEmail().equals(email) && user.checkContrasena(password)) {
			return user;
		} else {
			return null;
		}
	}

	public Usuario register(String nombre, String email, Date fechaNacimiento, float peso, float altura,
			int frecuenciaCardiacaMax, int frecuenciaCardiacaReposo, String contrasena) {
		// TODO: Get User using DAO and check
		Usuario user = new Usuario();
		user.setEmail(email);
		user.setNombre(nombre);
		String sha1 = org.apache.commons.codec.digest.DigestUtils.sha1Hex(contrasena);
		user.setContrasena(sha1);
		user.setFechaNacimiento(fechaNacimiento);
		user.setPeso(peso);
		user.setAltura(altura);
		user.setFrecuenciaCardiacaMax(frecuenciaCardiacaMax);
		user.setFrecuenciaCardiacaReposo(frecuenciaCardiacaReposo);

		if (user.getEmail().equals("test@gmail.com") && user.getNombre().equals("Test")) {
			return null;
		} else {
			return user;
		}
	}

	public static AutenticacionAppService getInstance() {
		if (instance == null) {
			instance = new AutenticacionAppService();
		}

		return instance;
	}
}