package es.deusto.ingenieria.sd.strava.server.factory;

import es.deusto.ingenieria.sd.strava.server.gateway.FacebookGateway;
import es.deusto.ingenieria.sd.strava.server.gateway.GoogleGateway;
import es.deusto.ingenieria.sd.strava.server.gateway.IAuthGateway;

public class GatewayFactory {

	private static GatewayFactory instance;
	
	private GatewayFactory() {
	}
	
	public IAuthGateway getGateway(String nombre) {
		switch (nombre) {
		case "FACEBOOK":
			return FacebookGateway.getInstance();
		case "GOOGLE": 
			return GoogleGateway.getInstance();
		default:
			throw new IllegalArgumentException("Unexpected value: " + nombre);
		}
	}
	
	public static GatewayFactory getInstance() {
		if (instance == null) {
			instance = new GatewayFactory();
		}
		return instance;
	}
}
