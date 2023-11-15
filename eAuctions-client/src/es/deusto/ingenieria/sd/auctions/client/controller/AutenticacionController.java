package es.deusto.ingenieria.sd.auctions.client.controller;

import java.rmi.RemoteException;
import java.util.Date;

import es.deusto.ingenieria.sd.auctions.client.remote.ServiceLocator;
import es.deusto.ingenieria.sd.auctions.server.data.dto.UsuarioRegisterDTO;

//This class implements Controller pattern.
public class AutenticacionController {	
	
	private static AutenticacionController instance;
	//This attribute stores the token when login success
	private long token = -1; //-1 = login has not been done or fails
	
	public long getToken() {
		return token;
	}

	public boolean login(String email, String password) {
		if (!email.isEmpty() && !password.isEmpty()) {
			try {
				this.token = ServiceLocator.getInstance().getService().login(email, password);			
				return true;
			} catch (RemoteException e) {
				System.out.println("# Error during login: " + e);
				this.token = -1;
				return false;
			}
		} else {
			return false;
		}
	}
	
	public boolean logout() {
		try {
			ServiceLocator.getInstance().getService().logout(this.token);
			this.token = -1;
			return true;
		} catch (RemoteException e) {
			System.out.println("# Error during logout: " + e);
			return false;
		}
	}
	
	public boolean register(String nombre, String email, Date fechaNacimiento, float peso, float altura, int frecuenciaCardiacaMax, int frecuenciaCardiacaReposo, String contrasena) {
		if (!nombre.isEmpty() && !email.isEmpty() && fechaNacimiento != null && peso != 0 && altura != 0 && frecuenciaCardiacaMax != 0 && frecuenciaCardiacaReposo != 0 && !contrasena.isEmpty()) {
			try {
				UsuarioRegisterDTO usuario = new UsuarioRegisterDTO();
				usuario.setNombre(nombre);
				usuario.setEmail(email);
				usuario.setFechaNacimiento(fechaNacimiento);
				usuario.setPeso(peso);
				usuario.setAltura(altura);
				usuario.setFrecuenciaCardiacaMax(frecuenciaCardiacaMax);
				usuario.setFrecuenciaCardiacaReposo(frecuenciaCardiacaReposo);
				usuario.setContrasena(contrasena);
				
				this.token = ServiceLocator.getInstance().getService().register(usuario);
				return true;
			} catch (RemoteException e) {
				System.out.println("# Error during register: " + e);
				this.token = -1;
				return false;
			}
		} else {
			return false;
		}
	}
	
	public static AutenticacionController getInstance() {
		if (instance == null) {
			instance = new AutenticacionController();
		}
		
		return instance;
	}
}