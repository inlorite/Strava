package es.deusto.ingenieria.sd.auctions.client;

import java.util.List;

import es.deusto.ingenieria.sd.auctions.client.controller.StravaController;
import es.deusto.ingenieria.sd.auctions.client.controller.AutenticacionController;
import es.deusto.ingenieria.sd.auctions.client.gui.StravaWindow;
import es.deusto.ingenieria.sd.auctions.client.gui.AutenticacionWindow;
import es.deusto.ingenieria.sd.auctions.client.remote.ServiceLocator;

public class MainProgram {

	public static void main(String[] args) {
		ServiceLocator serviceLocator = ServiceLocator.getInstance();

		System.out.println("test");
		System.out.println("test");
		// args[0] = RMIRegistry IP
		// args[1] = RMIRegistry Port
		// args[2] = Service Name
		serviceLocator.setService(args[0], args[1], args[2]);

		AutenticacionWindow autenticacionWindow = AutenticacionWindow.getInstance();
	}
}