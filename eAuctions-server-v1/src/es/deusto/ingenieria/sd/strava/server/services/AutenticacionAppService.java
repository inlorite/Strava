package es.deusto.ingenieria.sd.strava.server.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import es.deusto.ingenieria.sd.strava.server.data.dao.UserDAO;
import es.deusto.ingenieria.sd.strava.server.data.domain.SesionEntrenamiento;
import es.deusto.ingenieria.sd.strava.server.data.domain.Usuario;
import es.deusto.ingenieria.sd.strava.server.factory.GatewayFactory;
import es.deusto.ingenieria.sd.strava.server.gateway.IAuthGateway;

//TODO: Implement Singleton Pattern
public class AutenticacionAppService {

	private static AutenticacionAppService instance;
	
	private AutenticacionAppService() {
		
	}
	
	public Usuario getUsuario(String usuario) {
		for (Usuario u : UserDAO.getInstance().findAll()) {
			if (u.getNombre().equals(usuario)) {
				return u;
			}
		}
		return null;
	}

	public Usuario login(String email, String password) {
		System.out.println(UserDAO.getInstance().findAll());
		for (Usuario u : UserDAO.getInstance().findAll()) {
			if (u.getEmail().equals(email)) {
				IAuthGateway gateway = GatewayFactory.getInstance().getGateway(u.getTipoServicio());
				Boolean result = gateway.login(email, password);
				
				if (result) {
					return u;
				} else {
					return null;
				}
			}
		}
		return null;
	}

	public boolean register(Usuario usuario) {
		try {
			if (!UserDAO.getInstance().findAll().contains(usuario)) {			
				IAuthGateway gateway = GatewayFactory.getInstance().getGateway(usuario.getTipoServicio());
				Boolean result = gateway.register(usuario.getEmail(), usuario.getContrasena());
				
				System.out.println(usuario);
				if (result) {
					UserDAO.getInstance().store(usuario);
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
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