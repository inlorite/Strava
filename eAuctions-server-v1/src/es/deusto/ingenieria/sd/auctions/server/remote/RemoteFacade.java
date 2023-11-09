package es.deusto.ingenieria.sd.auctions.server.remote;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.deusto.ingenieria.sd.auctions.server.data.domain.Reto;
import es.deusto.ingenieria.sd.auctions.server.data.domain.SesionEntrenamiento;
import es.deusto.ingenieria.sd.auctions.server.data.domain.Usuario;
import es.deusto.ingenieria.sd.auctions.server.data.dto.RetoAssembler;
import es.deusto.ingenieria.sd.auctions.server.data.dto.RetoDTO;
import es.deusto.ingenieria.sd.auctions.server.data.dto.SesionEntrenamientoAssembler;
import es.deusto.ingenieria.sd.auctions.server.data.dto.SesionEntrenamientoDTO;
import es.deusto.ingenieria.sd.auctions.server.services.SesionesEntrenamientoAppService;
import es.deusto.ingenieria.sd.auctions.server.services.AutenticacionAppService;
import es.deusto.ingenieria.sd.auctions.server.services.RetosAppService;

public class RemoteFacade extends UnicastRemoteObject implements IRemoteFacade {	
	private static final long serialVersionUID = 1L;

	//Data structure for manage Server State
	private Map<Long, Usuario> serverState = new HashMap<>();
	
	//TODO: Remove this instances when Singleton Pattern is implemented
	private AutenticacionAppService autenticacionService = new AutenticacionAppService();
	private SesionesEntrenamientoAppService sesionesEntrenamientoAppService = new SesionesEntrenamientoAppService();
	private RetosAppService retosAppService = new RetosAppService();

	public RemoteFacade() throws RemoteException {
		super();		
	}
	
	// Métodos Autenticación
	
	@Override
	public synchronized long login(String email, String password) throws RemoteException {
		System.out.println(" * RemoteFacade login(): " + email + " / " + password);
				
		//Perform login() using LoginAppService
		Usuario user = autenticacionService.login(email, password);
			
		//If login() success user is stored in the Server State
		if (user != null) {
			//If user is not logged in 
			if (!this.serverState.values().contains(user)) {
				Long token = Calendar.getInstance().getTimeInMillis();		
				this.serverState.put(token, user);		
				return(token);
			} else {
				throw new RemoteException("User is already logged in!");
			}
		} else {
			throw new RemoteException("Login fails!");
		}
	}
	
	@Override
	public synchronized void logout(long token) throws RemoteException {
		System.out.println(" * RemoteFacade logout(): " + token);
		
		if (this.serverState.containsKey(token)) {
			//Logout means remove the User from Server State
			this.serverState.remove(token);
		} else {
			throw new RemoteException("User is not logged in!");
		}
	}
	
	public long register() throws RemoteException; // TODO

	// Métodos SesionEntrenamiento

	public void crearSesionEntrenamiento() throws RemoteException; // TODO

	public List<SesionEntrenamientoDTO> getSesionesEntrenamiento() throws RemoteException {
		System.out.println(" * RemoteFacade getSesionesEntrenamiento()");
		
		//Get Categories using BidAppService
		List<SesionEntrenamiento> sesiones = sesionesEntrenamientoAppService.getSesionesEntrenamiento();
		
		if (sesiones != null) {
			//Convert domain object to DTO
			return SesionEntrenamientoAssembler.getInstance().sesionEntrenamientoToDTO(sesiones);
		} else {
			throw new RemoteException("getSesionesEntrenamiento() fails!");
		}
	}

	public void eliminarSesionEntrenamiento() throws RemoteException; // TODO

	// Métodos Reto

	public void crearReto() throws RemoteException; // TODO

	public List<RetoDTO> getRetos() throws RemoteException {
		System.out.println(" * RemoteFacade getRetos()");
		
		//Get Categories using BidAppService
		List<Reto> retos = retosAppService.getRetos();
		
		if (retos != null) {
			//Convert domain object to DTO
			return RetoAssembler.getInstance().retoToDTO(retos);
		} else {
			throw new RemoteException("getRetos() fails!");
		}
	}

	public void apuntarseReto() throws RemoteException; // TODO

	public void desapuntarseReto() throws RemoteException; // TODO

	public void eliminarReto() throws RemoteException; // TODO
	
	///////////////////// METODOS ANTIGUOS ////////////////////////
	
	@Override
	public List<CategoryDTO> getCategories() throws RemoteException {
		System.out.println(" * RemoteFacade getCategories()");
		
		//Get Categories using BidAppService
		List<Category> categories = bidService.getCategories();
		
		if (categories != null) {
			//Convert domain object to DTO
			return CategoryAssembler.getInstance().categoryToDTO(categories);
		} else {
			throw new RemoteException("getCategories() fails!");
		}
	}

	@Override
	public List<ArticleDTO> getArticles(String category) throws RemoteException {
		System.out.println(" * RemoteFacade getArticle('" + category + "')");

		//Get Articles using BidAppService
		List<Article> articles = bidService.getArticles(category);
		
		if (articles != null) {
			//Convert domain object to DTO
			return ArticleAssembler.getInstance().articleToDTO(articles);
		} else {
			throw new RemoteException("getArticles() fails!");
		}
	}
	
	@Override
	public boolean makeBid(long token, int article, float amount) throws RemoteException {		
		System.out.println(" * RemoteFacade makeBid article : " + article + " / amount " + amount);
		
		if (this.serverState.containsKey(token)) {						
			//Make the bid using Bid Application Service
			if (bidService.makeBid(this.serverState.get(token), article, amount)) {
				return true;
			} else {
				throw new RemoteException("makeBid() fails!");
			}
		} else {
			throw new RemoteException("To place a bid you must first log in");
		}
	}

	@Override
	public float getUSDRate() throws RemoteException {
		System.out.println(" * RemoteFacade get USD rate");

		//Get rate using BidAppService
		float rate = bidService.getUSDRate();
		
		if (rate != -1) {
			return rate;
		} else {
			throw new RemoteException("getUSDRate() fails!");
		}
	}

	@Override
	public float getGBPRate() throws RemoteException {
		System.out.println(" * RemoteFacade get GBP rate");
		
		//Get rate using BidAppService
		float rate = bidService.getGBPRate();
		
		if (rate != -1) {
			return rate;
		} else {
			throw new RemoteException("getGBPRate() fails!");
		}
	}
}