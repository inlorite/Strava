package es.deusto.ingenieria.sd.auctions.server.services;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.deusto.ingenieria.sd.auctions.server.data.domain.SesionEntrenamiento;
import es.deusto.ingenieria.sd.auctions.server.data.domain.Usuario;

//TODO: Implement Singleton Pattern
public class SesionesEntrenamientoAppService {

	// TODO: remove when DAO Pattern is implemented
	private List<SesionEntrenamiento> sesionesEntrenamiento = new ArrayList<>();

	public SesionesEntrenamientoAppService() {
		// TODO: remove when DAO Pattern is implemented
		this.initilizeData();
	}

	// TODO: remove when DAO Pattern is implemented
	private void initilizeData() {
		// Create Users

	}

	/////////////////////// METODOS PUBLICOS ///////////////////////

	public boolean crearSesionEntrenamiento(Usuario usuario, SesionEntrenamiento sesionEntrenamiento) {
		if (!usuario.getSesionesEntrenamiento().contains(sesionEntrenamiento)) {
			usuario.getSesionesEntrenamiento().add(sesionEntrenamiento);
			return true;
		}
		return false;
	}

	public List<SesionEntrenamiento> getSesionesEntrenamiento() {
		return sesionesEntrenamiento;
	}
	
	public List<SesionEntrenamiento> getSesionesEntrenamiento(Usuario usuario) {
		return usuario.getSesionesEntrenamiento();
	}

}