package es.deusto.ingenieria.sd.strava.server.remote;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import es.deusto.ingenieria.sd.strava.server.data.domain.Reto;
import es.deusto.ingenieria.sd.strava.server.data.domain.SesionEntrenamiento;
import es.deusto.ingenieria.sd.strava.server.data.domain.Usuario;
import es.deusto.ingenieria.sd.strava.server.data.dto.RetoAssembler;
import es.deusto.ingenieria.sd.strava.server.data.dto.RetoDTO;
import es.deusto.ingenieria.sd.strava.server.data.dto.SesionEntrenamientoAssembler;
import es.deusto.ingenieria.sd.strava.server.data.dto.SesionEntrenamientoDTO;
import es.deusto.ingenieria.sd.strava.server.data.dto.UsuarioAssembler;
import es.deusto.ingenieria.sd.strava.server.data.dto.UsuarioRegisterAssembler;
import es.deusto.ingenieria.sd.strava.server.data.dto.UsuarioRegisterDTO;
import es.deusto.ingenieria.sd.strava.server.services.AutenticacionAppService;
import es.deusto.ingenieria.sd.strava.server.services.RetosAppService;
import es.deusto.ingenieria.sd.strava.server.services.SesionesEntrenamientoAppService;

public class RemoteFacade extends UnicastRemoteObject implements IRemoteFacade {
	private static final long serialVersionUID = 1L;

	// Data structure for manage Server State
	private Map<Long, Usuario> serverState = new HashMap<>();

	public RemoteFacade() throws RemoteException {
		super();
	}

	/////////////////////// METODOS AUTENTICACION ///////////////////////

	@Override
	public synchronized long login(String email, String password) throws RemoteException {
		System.out.println(" * RemoteFacade login(): " + email + " / " + password);

		// Perform login() using LoginAppService
		Usuario user = AutenticacionAppService.getInstance().login(email, password);

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
		} else {
			throw new RemoteException("Could not register the user!");
		}

		if (AutenticacionAppService.getInstance().register(usuario)) {
			try {
				return login(usuario.getEmail(), usuario.getContrasena());
			} catch (RemoteException ex) {
				throw ex;
			}
		} else
			throw new RemoteException("Could not login the user!");
	}

	/////////////////////// METODOS SESIONENTRENAMIENTO ///////////////////////

	public boolean crearSesionEntrenamiento(SesionEntrenamientoDTO sesionEntrenamientoDTO, long token)
			throws RemoteException {
		System.out.println(" * RemoteFacade crearSesionEntrenamiento()");

		SesionEntrenamiento sesionEntrenamiento;
		sesionEntrenamientoDTO.setCreador(UsuarioAssembler.getInstance().usuarioToDTO(serverState.get(token)));
		
		if (sesionEntrenamientoDTO != null && this.serverState.containsKey(token)) {
			// Convert domain object to DTO
			sesionEntrenamiento = SesionEntrenamientoAssembler.getInstance()
					.dtoToSesionEntrenamiento(sesionEntrenamientoDTO);
		} else {
			throw new RemoteException("crearSesionEntrenamiento() fails!");
		}

		// Create SesionEntrenamiento using SesionesEntrenamientoAppService.getInstance()
		SesionesEntrenamientoAppService.getInstance().crearSesionEntrenamiento(AutenticacionAppService.getInstance().getUsuario(serverState.get(token).getNombre()), sesionEntrenamiento);
		
		return true;
	}

	public List<SesionEntrenamientoDTO> getSesionesEntrenamiento(long token) throws RemoteException {
		System.out.println(" * RemoteFacade getSesionesEntrenamiento(token)");

		List<SesionEntrenamiento> sesiones;

		// Get SesionEntrenamiento using SesionesEntrenamientoAppService.getInstance()
		if (this.serverState.containsKey(token)) {
			sesiones = SesionesEntrenamientoAppService.getInstance()
					.getSesionesEntrenamiento(AutenticacionAppService.getInstance().getUsuario(serverState.get(token).getNombre()));
		} else {
			throw new RemoteException("getSesionesEntrenamiento(token) fails!");
		}

		if (sesiones != null) {
			// Convert domain object to DTO
			return sesiones.stream().map(e -> SesionEntrenamientoAssembler.getInstance().sesionEntrenamientoToDTO(e))
					.collect(Collectors.toList());
		} else {
			throw new RemoteException("getSesionesEntrenamiento(token) fails!");
		}
	}

	public List<SesionEntrenamientoDTO> getSesionesEntrenamiento() throws RemoteException {
		System.out.println(" * RemoteFacade getSesionesEntrenamiento()");

		// Get SesionEntrenamiento using SesionesEntrenamientoAppService.getInstance()
		List<SesionEntrenamiento> sesiones = SesionesEntrenamientoAppService.getInstance().getSesionesEntrenamiento();

		if (sesiones != null) {
			// Convert domain object to DTO
			return SesionEntrenamientoAssembler.getInstance().sesionEntrenamientoToDTO(sesiones);
		} else {
			throw new RemoteException("getSesionesEntrenamiento() fails!");
		}
	}

	/////////////////////// METODOS RETO ///////////////////////

	public boolean crearReto(RetoDTO retoDTO, long token) throws RemoteException {
		System.out.println(" * RemoteFacade crearReto()");

		Reto reto;
		retoDTO.setCreador(UsuarioAssembler.getInstance().usuarioToDTO(serverState.get(token)));

		if (retoDTO != null && this.serverState.containsKey(token)) {
			// Convert domain object to DTO
			reto = RetoAssembler.getInstance().dtoToReto(retoDTO);
		} else {
			throw new RemoteException("crearReto() fails!");
		}

		// Create Reto using RetosAppService.getInstance()
		RetosAppService.getInstance().crearReto(reto,reto.getCreador());
		
		return true;
	}

	public List<RetoDTO> getRetos(long token) throws RemoteException {
		System.out.println(" * RemoteFacade getRetos(token)");

		List<Reto> retos;

		// Get SesionEntrenamiento using SesionesEntrenamientoAppService.getInstance()
		if (this.serverState.containsKey(token)) {
			retos = RetosAppService.getInstance().getRetos(this.serverState.get(token).getNombre());
			System.out.println("Fachada longitud "+ retos.size());
		} else {
			throw new RemoteException("getRetos(token) fails!");
		}

		if (retos != null) {
			// Convert domain object to DTO
			return RetoAssembler.getInstance().retoToDTO(retos);
		} else {
			throw new RemoteException("getRetos(token) fails!");
		}
	}

//	public List<RetoDTO> getRetos() throws RemoteException {
//		System.out.println(" * RemoteFacade getRetos()");
//
//		// Get Reto using RetosAppService.getInstance()
//		List<Reto> retos = RetosAppService.getInstance().getRetos();
//
//		if (retos != null) {
//			// Convert domain object to DTO
//			return RetoAssembler.getInstance().retoToDTO(retos);
//		} else {
//			throw new RemoteException("getRetos() fails!");
//		}
//	}
	
	@Override
	public List<RetoDTO> getRetosApuntados(long token) throws RemoteException {
		System.out.println(" * RemoteFacade getRetosApuntados(token)");

		List<Reto> retos;

		// Get SesionEntrenamiento using SesionesEntrenamientoAppService.getInstance()
		if (this.serverState.containsKey(token)) {
			retos = RetosAppService.getInstance().getRetosApuntados(this.serverState.get(token));
		} else {
			throw new RemoteException("getRetos(token) fails!");
		}

		if (retos != null) {
			// Convert domain object to DTO
			return RetoAssembler.getInstance().retoToDTO(retos);
		} else {
			throw new RemoteException("getRetos(token) fails!");
		}
	}

	@Override
	public List<RetoDTO> getRetosDesapuntados(long token) throws RemoteException {
		System.out.println(" * RemoteFacade getRetosDesapuntados(token)");

		List<Reto> retos;

		// Get SesionEntrenamiento using SesionesEntrenamientoAppService.getInstance()
		if (this.serverState.containsKey(token)) {
			retos = RetosAppService.getInstance().getRetosDesapuntados(this.serverState.get(token));
		} else {
			throw new RemoteException("getRetos(token) fails!");
		}

		if (retos != null) {
			// Convert domain object to DTO
			return RetoAssembler.getInstance().retoToDTO(retos);
		} else {
			throw new RemoteException("getRetos(token) fails!");
		}
	}

	public boolean apuntarseReto(String reto, long token) throws RemoteException {
		System.out.println(" * RemoteFacade apuntarseReto()");

		if (reto != null && this.serverState.containsKey(token)) {
			// Apuntarse Reto using RetosAppService.getInstance()
			RetosAppService.getInstance().apuntarseReto(this.serverState.get(token), reto);
			return true;
		} else {
			throw new RemoteException("apuntarseReto() fails!");
		}
	}

	public boolean desapuntarseReto(String reto, long token) throws RemoteException {
		System.out.println(" * RemoteFacade desapuntarseReto()");

		if (reto != null && this.serverState.containsKey(token)) {
			// Desapuntarse Reto using RetosAppService.getInstance()
			RetosAppService.getInstance().desapuntarseReto(this.serverState.get(token), reto);
			return true;
		} else {
			throw new RemoteException("desapuntarseReto() fails!");
		}
	}

	public boolean eliminarReto(String reto, long token) throws RemoteException {
		System.out.println(" * RemoteFacade eliminarReto()");

		if (reto != null && this.serverState.containsKey(token)) {
			// Delete Reto using RetosAppService.getInstance()
			RetosAppService.getInstance().eliminarReto(serverState.get(token), reto);
			return true;
		} else {
			throw new RemoteException("eliminarReto() fails!");
		}
	}

	@Override
	public String getUsuario(long token) throws RemoteException {	
		return AutenticacionAppService.getInstance().getUsuario(serverState.get(token).getNombre()).getNombre();
	}

	

}