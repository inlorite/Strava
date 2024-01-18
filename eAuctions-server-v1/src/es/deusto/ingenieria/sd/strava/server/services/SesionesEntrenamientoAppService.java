package es.deusto.ingenieria.sd.strava.server.services;

import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.deusto.ingenieria.sd.strava.server.data.dao.SesionEntrenamientoDAO;
import es.deusto.ingenieria.sd.strava.server.data.dao.UserDAO;
import es.deusto.ingenieria.sd.strava.server.data.domain.SesionEntrenamiento;
import es.deusto.ingenieria.sd.strava.server.data.domain.Usuario;

//TODO: Implement Singleton Pattern
public class SesionesEntrenamientoAppService {

	private static SesionesEntrenamientoAppService instance;

	private SesionesEntrenamientoAppService() {

	}

	/////////////////////// METODOS PUBLICOS ///////////////////////

	public boolean crearSesionEntrenamiento(Usuario usuario, SesionEntrenamiento sesionEntrenamiento) {
		if (!usuario.getSesionesEntrenamiento().contains(sesionEntrenamiento)) {
			usuario.getSesionesEntrenamiento().add(sesionEntrenamiento);
			SesionEntrenamientoDAO.getInstance().store(sesionEntrenamiento);
			return true;
		}
		return false;
	}

	public List<SesionEntrenamiento> getSesionesEntrenamiento() {
		return SesionEntrenamientoDAO.getInstance().findAll();
	}

	public List<SesionEntrenamiento> getSesionesEntrenamiento(Usuario usuario) {
		return usuario.getSesionesEntrenamiento();
	}
	
	public static SesionesEntrenamientoAppService getInstance() {
		if (instance == null) {
			instance = new SesionesEntrenamientoAppService();
		}

		return instance;
	}

}