package es.deusto.ingenieria.sd.auctions.server.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import es.deusto.ingenieria.sd.auctions.server.data.domain.Usuario;

//TODO: Implement Singleton Pattern
public class AutenticacionAppService {

	private static AutenticacionAppService instance;
	private List<Usuario> usuarios = new ArrayList<Usuario>();

	public List<Usuario> getUsuarios() {
		return usuarios;
	}
	
	public Usuario getUsuario(String usuario) {
		for (Usuario u : usuarios) {
			if (u.getNombre().equals(usuario)) {
				return u;
			}
		}
		return null;
	}

	public Usuario login(String email, String password) {
		// TODO: Get User using DAO and check
		for (Usuario u : usuarios) {
			if (u.getEmail().equals(email)) {
				if (u.getContrasena().equals(org.apache.commons.codec.digest.DigestUtils.sha1Hex(password))) {
					return u;
				} else {
					System.out.println("Contrasena incorrecta.");
				}
			} else {
				System.out.println("El usuario no existe.");
			}
		}
		return null;
	}

	public boolean register(Usuario usuario) {
		// TODO: Get User using DAO and check
		if (!usuarios.contains(usuario)) {
			usuarios.add(usuario);
			return true;
		}
		return false;
	}

	public static AutenticacionAppService getInstance() {
		if (instance == null) {
			instance = new AutenticacionAppService();
		}

		return instance;
	}
}