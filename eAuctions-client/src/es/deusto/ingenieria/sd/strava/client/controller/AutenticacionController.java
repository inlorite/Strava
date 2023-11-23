package es.deusto.ingenieria.sd.strava.client.controller;

import java.rmi.RemoteException;
import java.util.Date;

import es.deusto.ingenieria.sd.strava.server.data.dto.UsuarioRegisterDTO;
import es.deusto.ingenieria.sd.strava.client.remote.ServiceLocator;

//This class implements Controller pattern.
public class AutenticacionController {	
	
	private static AutenticacionController instance;
	//This attribute stores the token when login success
	private static long token = -1; //-1 = login has not been done or fails
	
	public static long getToken() {
		return token;
	}

	public boolean login(String email, String password) {
		if (!email.isEmpty() && !password.isEmpty()) {
			try {
				AutenticacionController.token = ServiceLocator.getInstance().getService().login(email, password);			
				return true;
			} catch (RemoteException e) {
				System.out.println("# Error during login: " + e);
				AutenticacionController.token = -1;
				return false;
			}
		} else {
			return false;
		}
	}
	
	public boolean logout() {
		try {
			ServiceLocator.getInstance().getService().logout(AutenticacionController.token);
			AutenticacionController.token = -1;
			return true;
		} catch (RemoteException e) {
			System.out.println("# Error during logout: " + e);
			return false;
		}
	}
	
	public boolean register(String nombre, String email, Date fechaNacimiento, float peso, float altura, int frecuenciaCardiacaMax, int frecuenciaCardiacaReposo, String contrasena, String tipo) {
		if (!nombre.isEmpty() && !email.isEmpty() && fechaNacimiento != null && peso != 0 && altura != 0 && frecuenciaCardiacaMax != 0 && frecuenciaCardiacaReposo != 0 && !contrasena.isEmpty() && !tipo.isEmpty()) {
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
				usuario.setTipoServicio(tipo);
				
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
	
	public String getUsuario() {
			try {
				return ServiceLocator.getInstance().getService().getUsuario(AutenticacionController.token);
			} catch (RemoteException e) {
				e.printStackTrace();
				return null;
			}

	}
	
	public static AutenticacionController getInstance() {
		if (instance == null) {
			instance = new AutenticacionController();
		}
		
		return instance;
	}
}