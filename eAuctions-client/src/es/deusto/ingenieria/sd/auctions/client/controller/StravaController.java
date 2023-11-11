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

	// Reference to the Service Locator
	private ServiceLocator serviceLocator;

	public StravaController(ServiceLocator serviceLocator) {
		this.serviceLocator = serviceLocator;
	}

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
			this.serviceLocator.getService().crearSesionEntrenamiento(sesionEntrenamientoDTO, token);
		} catch (RemoteException e) {
			System.out.println("# Error creating Sesion Entrenamiento: " + e);
		}
	}

	public List<SesionEntrenamientoDTO> getSesionesEntrenamiento(long token) {
		try {
			return this.serviceLocator.getService().getSesionesEntrenamiento(token);
		} catch (RemoteException e) {
			System.out.println("# Error getting user's Sesiones Entrenamiento: " + e);
			return null;
		}
	}

	public List<SesionEntrenamientoDTO> getSesionesEntrenamiento() {
		try {
			return this.serviceLocator.getService().getSesionesEntrenamiento();
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
			this.serviceLocator.getService().crearReto(retoDTO, token);
		} catch (RemoteException e) {
			System.out.println("# Error creating Reto: " + e);
		}
	}

	public List<RetoDTO> getRetos(long token) {
		try {
			return this.serviceLocator.getService().getRetos(token);
		} catch (RemoteException e) {
			System.out.println("# Error getting user's Retos: " + e);
			return null;
		}
	}

	public List<RetoDTO> getRetos() {
		try {
			return this.serviceLocator.getService().getRetos();
		} catch (RemoteException e) {
			System.out.println("# Error getting Retos: " + e);
			return null;
		}
	}

	public void apuntarseReto(String reto, long token) {
		try {
			this.serviceLocator.getService().apuntarseReto(reto, token);
		} catch (RemoteException e) {
			System.out.println("# Error apuntarseReto(): " + e);
		}
	}

	public void desapuntarseReto(String reto, long token) {
		try {
			this.serviceLocator.getService().desapuntarseReto(reto, token);
		} catch (RemoteException e) {
			System.out.println("# Error desapuntarseReto(): " + e);
		}
	}

	public void eliminarReto(String reto, long token) {
		try {
			this.serviceLocator.getService().eliminarReto(reto, token);
		} catch (RemoteException e) {
			System.out.println("# Error eliminating Reto: " + e);
		}
	}

	// Metodos antiguos

	public List<CategoryDTO> getCategories() {
		try {
			return this.serviceLocator.getService().getCategories();
		} catch (RemoteException e) {
			System.out.println("# Error getting all categories: " + e);
			return null;
		}
	}

	public List<ArticleDTO> getArticles(String category) {
		try {
			return this.serviceLocator.getService().getArticles(category);
		} catch (RemoteException e) {
			System.out.println("# Error getting articles of a category: " + e);
			return null;
		}
	}

	public boolean makeBid(long token, int article, float bid) {
		try {
			return this.serviceLocator.getService().makeBid(token, article, bid);
		} catch (RemoteException e) {
			System.out.println("# Error making a bid: " + e);
			return false;
		}
	}

	public float getUSDRate() {
		try {
			return this.serviceLocator.getService().getUSDRate();
		} catch (RemoteException e) {
			System.out.println("# Error getting USD rate: " + e);
			return -1;
		}
	}

	public float getGBPRate() {
		try {
			return this.serviceLocator.getService().getGBPRate();
		} catch (RemoteException e) {
			System.err.println("# Error getting GBP rate: " + e);
			return -1;
		}
	}
}