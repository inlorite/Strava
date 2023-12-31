package es.deusto.ingenieria.sd.strava.client.remote;

import java.rmi.Naming;

import es.deusto.ingenieria.sd.strava.server.remote.IRemoteFacade;

//This class implements Service Locator pattern
public class ServiceLocator {
	
	private static ServiceLocator instance;
	//Remote Facade reference
	private IRemoteFacade service;
	
	private ServiceLocator() {
	}

	@SuppressWarnings({ "removal", "deprecation" })
	public void setService(String ip, String port, String serviceName) {
		//Activate Security Manager. It is needed for RMI.
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
		
		//Get Remote Facade reference using RMIRegistry (IP + Port) and the service name.
		try {		
			String URL = "//" + ip + ":" + port + "/" + serviceName;
			this.service = (IRemoteFacade) Naming.lookup(URL);
		} catch (Exception ex) {
			System.err.println("# Error locating remote facade: " + ex);
		}		
	}

	public IRemoteFacade getService() {
		return this.service;
	}
	
	public static ServiceLocator getInstance() {
		if (instance == null) {
			instance = new ServiceLocator();
		}
		
		return instance;
	}
}