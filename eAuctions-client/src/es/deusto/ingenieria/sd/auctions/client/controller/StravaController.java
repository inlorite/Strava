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

	public void crearSesionEntrenamiento(long token, String titulo, float distancia, Date fechaInicio, long horaInicio,
			float duracion) {

		SesionEntrenamientoDTO sesionEntrenamientoDTO = new SesionEntrenamientoDTO();
		sesionEntrenamientoDTO.setTitulo(titulo);
		sesionEntrenamientoDTO.setDistancia(distancia);
		sesionEntrenamientoDTO.setFechaInicio(fechaInicio);
		sesionEntrenamientoDTO.setHoraInicio(horaInicio);
		sesionEntrenamientoDTO.setDuracion(duracion);

		try {
			ServiceLocator.getInstance().getService().crearSesionEntrenamiento(sesionEntrenamientoDTO, token);
		} catch (RemoteException e) {
			System.out.println("# Error creating Sesion Entrenamiento: " + e);
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
			return ServiceLocator.getInstance().getService().getSesionesEntrenamiento();
		} catch (RemoteException e) {
			System.out.println("# Error getting Sesiones Entrenamiento: " + e);
			return null;
		}
	}

	// Métodos Reto

	public void crearReto(String nombre, Date fechaInicio, Date fechaFin, float distancia, float tiempo, long token) {

		RetoDTO retoDTO = new RetoDTO();
		retoDTO.setNombre(nombre);
		retoDTO.setFechaInicio(fechaInicio);
		retoDTO.setFechaFin(fechaFin);
		retoDTO.setDistancia(distancia);
		retoDTO.setTiempo(tiempo);

		try {
			ServiceLocator.getInstance().getService().crearReto(retoDTO, token);
		} catch (RemoteException e) {
			System.out.println("# Error creating Reto: " + e);
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

	public void apuntarseReto(String reto, long token) {
		try {
			ServiceLocator.getInstance().getService().apuntarseReto(reto, token);
		} catch (RemoteException e) {
			System.out.println("# Error apuntarseReto(): " + e);
		}
	}

	public void desapuntarseReto(String reto, long token) {
		try {
			ServiceLocator.getInstance().getService().desapuntarseReto(reto, token);
		} catch (RemoteException e) {
			System.out.println("# Error desapuntarseReto(): " + e);
		}
	}

	public void eliminarReto(String reto, long token) {
		try {
			ServiceLocator.getInstance().getService().eliminarReto(reto, token);
		} catch (RemoteException e) {
			System.out.println("# Error eliminating Reto: " + e);
		}
	}
	
	public static StravaController getInstance() {
		if (instance == null) {
			instance = new StravaController();
		}
		
		return instance;
	}
}