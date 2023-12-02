package es.deusto.ingenieria.sd.strava.server.gateway;

public interface IAuthGateway {
	
	boolean login(String email, String contrasena);
	boolean register(String email, String contrasena);
	
}
