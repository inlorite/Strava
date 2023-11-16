package es.deusto.ingenieria.sd.auctions.client.controller;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

import es.deusto.ingenieria.sd.auctions.client.remote.ServiceLocator;
import es.deusto.ingenieria.sd.auctions.server.data.dto.SesionEntrenamientoDTO;
import es.deusto.ingenieria.sd.auctions.server.data.dto.RetoDTO;
import es.deusto.ingenieria.sd.auctions.server.data.dto.UsuarioDTO;

//This class implements Controller pattern.
public class StravaController {

	private static StravaController instance;

	// Métodos SesionEntrenamiento

	public boolean crearSesionEntrenamiento(long token, String titulo, float distancia, Date fechaInicio, long horaInicio,
			float duracion, String deporte) {

		SesionEntrenamientoDTO sesionEntrenamientoDTO = new SesionEntrenamientoDTO();
		sesionEntrenamientoDTO.setTitulo(titulo);
		sesionEntrenamientoDTO.setDistancia(distancia);
		sesionEntrenamientoDTO.setFechaInicio(fechaInicio);
		sesionEntrenamientoDTO.setHoraInicio(horaInicio);
		sesionEntrenamientoDTO.setDuracion(duracion);
		sesionEntrenamientoDTO.setDeporte(deporte);

		try {
			return ServiceLocator.getInstance().getService().crearSesionEntrenamiento(sesionEntrenamientoDTO, token);
		} catch (RemoteException e) {
			System.out.println("# Error creating Sesion Entrenamiento: " + e);
			return false;
		}
	}

	public List<SesionEntrenamientoDTO> getSesionesEntrenamiento(long token) {
		try {
			return ServiceLocator.getInstance().getService().getSesionesEntrenamiento(token);
		} catch (RemoteException e) {
			System.out.println("# Error getting user's Sesiones Entrenamiento: " + e);
			return null;
		}
	}

	public List<SesionEntrenamientoDTO> getSesionesEntrenamiento() {
		try {
			System.out.println("PASO2");
			return ServiceLocator.getInstance().getService().getSesionesEntrenamiento();
		} catch (RemoteException e) {
			System.out.println("# Error getting Sesiones Entrenamiento: " + e);
			return null;
		}
	}

	// Métodos Reto

	public boolean crearReto(String nombre, Date fechaInicio, Date fechaFin, float distancia, float tiempo, long token, String tipoReto) {

		RetoDTO retoDTO = new RetoDTO();
		retoDTO.setNombre(nombre);
		retoDTO.setFechaInicio(fechaInicio);
		retoDTO.setFechaFin(fechaFin);
		retoDTO.setDistancia(distancia);
		retoDTO.setTiempo(tiempo);
		retoDTO.setTipoReto(tipoReto);

		try {
			return ServiceLocator.getInstance().getService().crearReto(retoDTO, token);
		} catch (RemoteException e) {
			System.out.println("# Error creating Reto: " + e);
			return false;
		}
	}

	public List<RetoDTO> getRetos(long token) {
		try {
			return ServiceLocator.getInstance().getService().getRetos(token);
		} catch (RemoteException e) {
			System.out.println("# Error getting user's Retos: " + e);
			return null;
		}
	}

	public List<RetoDTO> getRetos() {
		try {
			return ServiceLocator.getInstance().getService().getRetos();
		} catch (RemoteException e) {
			System.out.println("# Error getting Retos: " + e);
			return null;
		}
	}

	public boolean apuntarseReto(String reto, long token) {
		try {
			return ServiceLocator.getInstance().getService().apuntarseReto(reto, token);
		} catch (RemoteException e) {
			System.out.println("# Error apuntarseReto(): " + e);
			return false;
		}
	}

	public boolean desapuntarseReto(String reto, long token) {
		try {
			return ServiceLocator.getInstance().getService().desapuntarseReto(reto, token);
		} catch (RemoteException e) {
			System.out.println("# Error desapuntarseReto(): " + e);
			return false;
		}
	}

	public boolean eliminarReto(String reto, long token) {
		try {
			return ServiceLocator.getInstance().getService().eliminarReto(reto, token);
		} catch (RemoteException e) {
			System.out.println("# Error eliminating Reto: " + e);
			return false;
		}
	}
	
	public static StravaController getInstance() {
		if (instance == null) {
			instance = new StravaController();
		}
		
		return instance;
	}
}