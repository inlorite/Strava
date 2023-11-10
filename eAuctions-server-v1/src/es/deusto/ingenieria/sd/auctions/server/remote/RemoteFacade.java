package es.deusto.ingenieria.sd.auctions.server.remote;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import es.deusto.ingenieria.sd.auctions.server.data.domain.Reto;
import es.deusto.ingenieria.sd.auctions.server.data.domain.SesionEntrenamiento;
import es.deusto.ingenieria.sd.auctions.server.data.domain.Usuario;
import es.deusto.ingenieria.sd.auctions.server.data.dto.RetoAssembler;
import es.deusto.ingenieria.sd.auctions.server.data.dto.RetoDTO;
import es.deusto.ingenieria.sd.auctions.server.data.dto.SesionEntrenamientoAssembler;
import es.deusto.ingenieria.sd.auctions.server.data.dto.SesionEntrenamientoDTO;
import es.deusto.ingenieria.sd.auctions.server.data.dto.UsuarioRegisterAssembler;
import es.deusto.ingenieria.sd.auctions.server.data.dto.UsuarioRegisterDTO;
import es.deusto.ingenieria.sd.auctions.server.services.SesionesEntrenamientoAppService;
import es.deusto.ingenieria.sd.auctions.server.services.AutenticacionAppService;
import es.deusto.ingenieria.sd.auctions.server.services.RetosAppService;

public class RemoteFacade extends UnicastRemoteObject implements IRemoteFacade {
	private static final long serialVersionUID = 1L;

	// Data structure for manage Server State
	private Map<Long, Usuario> serverState = new HashMap<>();

	// TODO: Remove this instances when Singleton Pattern is implemented
	private AutenticacionAppService autenticacionAppService = new AutenticacionAppService();
	private SesionesEntrenamientoAppService sesionesEntrenamientoAppService = new SesionesEntrenamientoAppService();
	private RetosAppService retosAppService = new RetosAppService();

	public RemoteFacade() throws RemoteException {
		super();
	}

	/////////////////////// METODOS AUTENTICACION ///////////////////////

	@Override
	public synchronized long login(String email, String password) throws RemoteException {
		System.out.println(" * RemoteFacade login(): " + email + " / " + password);

		// Perform login() using LoginAppService
		Usuario user = autenticacionAppService.login(email, password);

		// If login() success user is stored in the Server State
		if (user != null) {
			// If user is not logged in
			if (!this.serverState.values().contains(user)) {
				Long token = Calendar.getInstance().getTimeInMillis();
				this.serverState.put(token, user);
				return (token);
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
			// Logout means remove the User from Server State
			this.serverState.remove(token);
		} else {
			throw new RemoteException("User is not logged in!");
		}
	}

	public long register(UsuarioRegisterDTO usuarioRegisterDTO) throws RemoteException {
		System.out.println(" * RemoteFacade register(): " + usuarioRegisterDTO.getNombre());
		
		Usuario usuario;
		
		if (usuarioRegisterDTO != null) {
			usuario = UsuarioRegisterAssembler.getInstance().dtoToUsuario(usuarioRegisterDTO);
		} else
			throw new RemoteException("Could not register the user!");
		
		if (autenticacionAppService.register(usuario)) {
			try {
				return login(usuario.getEmail(), usuario.getContrasena());
			} catch (RemoteException ex) {
				throw ex;
			}
		}
		else
			throw new RemoteException("Could not login the user!");
	}

	/////////////////////// METODOS SESIONENTRENAMIENTO ///////////////////////

	public void crearSesionEntrenamiento(SesionEntrenamientoDTO sesionEntrenamientoDTO, long token)
			throws RemoteException {
		System.out.println(" * RemoteFacade crearSesionEntrenamiento()");

		SesionEntrenamiento sesionEntrenamiento;

		if (sesionEntrenamientoDTO != null && this.serverState.containsKey(token)) {
			// Convert domain object to DTO
			sesionEntrenamiento = SesionEntrenamientoAssembler.getInstance()
					.dtoToSesionEntrenamiento(sesionEntrenamientoDTO);
		} else {
			throw new RemoteException("crearSesionEntrenamiento() fails!");
		}

		// Create SesionEntrenamiento using SesionesEntrenamientoAppService
		sesionesEntrenamientoAppService.crearSesionEntrenamiento(this.serverState.get(token), sesionEntrenamiento);
	}

	public List<SesionEntrenamientoDTO> getSesionesEntrenamiento(long token) throws RemoteException {
		System.out.println(" * RemoteFacade getSesionesEntrenamiento()");

		List<SesionEntrenamiento> sesiones;

		// Get SesionEntrenamiento using SesionesEntrenamientoAppService
		if (this.serverState.containsKey(token)) {
			sesiones = sesionesEntrenamientoAppService
					.getSesionesEntrenamiento(this.serverState.get(token).getNombre());
		} else {
			throw new RemoteException("getSesionesEntrenamiento() fails!");
		}

		if (sesiones != null && this.serverState.containsKey(token)) {
			// Convert domain object to DTO
			return sesiones.stream().map(e -> SesionEntrenamientoAssembler.getInstance().sesionEntrenamientoToDTO(e))
					.collect(Collectors.toList());
		} else {
			throw new RemoteException("getSesionesEntrenamiento() fails!");
		}
	}

	public void eliminarSesionEntrenamiento(SesionEntrenamientoDTO sesionEntrenamientoDTO, long token)
			throws RemoteException {
		System.out.println(" * RemoteFacade eliminarSesionEntrenamiento()");

		SesionEntrenamiento sesionEntrenamiento;

		if (sesionEntrenamientoDTO != null && this.serverState.containsKey(token)) {
			// Convert domain object to DTO
			sesionEntrenamiento = SesionEntrenamientoAssembler.getInstance()
					.dtoToSesionEntrenamiento(sesionEntrenamientoDTO);
		} else {
			throw new RemoteException("eliminarSesionEntrenamiento() fails!");
		}

		// Delete SesionEntrenamiento using SesionesEntrenamientoAppService
		sesionesEntrenamientoAppService.eliminarSesionEntrenamiento(sesionEntrenamiento.getTitulo(),
				this.serverState.get(token).getNombre());
	}

	/////////////////////// METODOS RETO ///////////////////////

	public void crearReto(RetoDTO retoDTO, long token) throws RemoteException {
		System.out.println(" * RemoteFacade crearReto()");

		Reto reto;

		if (retoDTO != null && this.serverState.containsKey(token)) {
			// Convert domain object to DTO
			reto = RetoAssembler.getInstance().dtoToReto(retoDTO);
		} else {
			throw new RemoteException("crearReto() fails!");
		}

		// Create Reto using RetosAppService
		retosAppService.crearReto(reto);
	}

	public List<RetoDTO> getRetos() throws RemoteException {
		System.out.println(" * RemoteFacade getRetos()");

		// Get Reto using RetosAppService
		List<Reto> retos = retosAppService.getRetos();

		if (retos != null) {
			// Convert domain object to DTO
			return retos.stream().map(e -> RetoAssembler.getInstance().retoToDTO(e)).collect(Collectors.toList());
		} else {
			throw new RemoteException("getRetos() fails!");
		}
	}

	public void apuntarseReto(RetoDTO retoDTO, long token) throws RemoteException {
		System.out.println(" * RemoteFacade apuntarseReto()");

		Reto reto;

		if (retoDTO != null && this.serverState.containsKey(token)) {
			// Convert domain object to DTO
			reto = RetoAssembler.getInstance().dtoToReto(retoDTO);
		} else {
			throw new RemoteException("apuntarseReto() fails!");
		}

		// Apuntarse Reto using RetosAppService
		retosAppService.apuntarseReto(this.serverState.get(token).getNombre(), reto.getNombre());
	}

	public void desapuntarseReto(RetoDTO retoDTO, long token) throws RemoteException {
		System.out.println(" * RemoteFacade desapuntarseReto()");

		Reto reto;

		if (retoDTO != null && this.serverState.containsKey(token)) {
			// Convert domain object to DTO
			reto = RetoAssembler.getInstance().dtoToReto(retoDTO);
		} else {
			throw new RemoteException("desapuntarseReto() fails!");
		}

		// Desapuntarse Reto using RetosAppService
		retosAppService.desapuntarseReto(this.serverState.get(token).getNombre(), reto.getNombre());
	}

	public void eliminarReto(RetoDTO retoDTO, long token) throws RemoteException {
		System.out.println(" * RemoteFacade eliminarReto()");

		Reto reto;

		if (retoDTO != null && this.serverState.containsKey(token)) {
			// Convert domain object to DTO
			reto = RetoAssembler.getInstance().dtoToReto(retoDTO);
		} else {
			throw new RemoteException("eliminarReto() fails!");
		}

		// Delete Reto using RetosAppService
		retosAppService.eliminarReto(reto.getNombre());
	}

	///////////////////// METODOS ANTIGUOS ////////////////////////

	@Override
	public List<CategoryDTO> getCategories() throws RemoteException {
		System.out.println(" * RemoteFacade getCategories()");

		// Get Categories using BidAppService
		List<Category> categories = sesionesEntrenamientoAppService.getCategories();

		if (categories != null) {
			// Convert domain object to DTO
			return CategoryAssembler.getInstance().categoryToDTO(categories);
		} else {
			throw new RemoteException("getCategories() fails!");
		}
	}

	@Override
	public List<ArticleDTO> getArticles(String category) throws RemoteException {
		System.out.println(" * RemoteFacade getArticle('" + category + "')");

		// Get Articles using BidAppService
		List<Article> articles = sesionesEntrenamientoAppService.getArticles(category);

		if (articles != null) {
			// Convert domain object to DTO
			return ArticleAssembler.getInstance().articleToDTO(articles);
		} else {
			throw new RemoteException("getArticles() fails!");
		}
	}

	@Override
	public boolean makeBid(long token, int article, float amount) throws RemoteException {
		System.out.println(" * RemoteFacade makeBid article : " + article + " / amount " + amount);

		if (this.serverState.containsKey(token)) {
			// Make the bid using Bid Application Service
			if (sesionesEntrenamientoAppService.makeBid(this.serverState.get(token), article, amount)) {
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

		// Get rate using BidAppService
		float rate = sesionesEntrenamientoAppService.getUSDRate();

		if (rate != -1) {
			return rate;
		} else {
			throw new RemoteException("getUSDRate() fails!");
		}
	}

	@Override
	public float getGBPRate() throws RemoteException {
		System.out.println(" * RemoteFacade get GBP rate");

		// Get rate using BidAppService
		float rate = sesionesEntrenamientoAppService.getGBPRate();

		if (rate != -1) {
			return rate;
		} else {
			throw new RemoteException("getGBPRate() fails!");
		}
	}
}