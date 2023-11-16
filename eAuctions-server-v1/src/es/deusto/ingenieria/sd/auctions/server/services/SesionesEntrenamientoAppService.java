package es.deusto.ingenieria.sd.auctions.server.services;

import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.deusto.ingenieria.sd.auctions.server.data.domain.SesionEntrenamiento;
import es.deusto.ingenieria.sd.auctions.server.data.domain.Usuario;

//TODO: Implement Singleton Pattern
public class SesionesEntrenamientoAppService {

	private static SesionesEntrenamientoAppService instance;
	// TODO: remove when DAO Pattern is implemented
	private List<SesionEntrenamiento> sesionesEntrenamiento = new ArrayList<>();

	private SesionesEntrenamientoAppService() {
		// TODO: remove when DAO Pattern is implemented
		//this.initializeData();
	}

	// TODO: remove when DAO Pattern is implemented
	private void initializeData() {
		// Create Sesiones Entrenamiento

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

		// Inicializar sesion 1
		SesionEntrenamiento s1 = new SesionEntrenamiento();
		s1.setTitulo("Sesion 1");
		s1.setDistancia(100);
		s1.setDuracion(100);

		try {
			String myDate = "2023/12/29 18:10:45";
			Date date;
			date = sdf.parse(myDate);
			s1.setFechaInicio(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		s1.setHoraInicio(Calendar.getInstance().getTimeInMillis());

		AutenticacionAppService.getInstance().getUsuario("Adrian").getSesionesEntrenamiento().add(s1);

		// Inicializar sesion 2
		SesionEntrenamiento s2 = new SesionEntrenamiento();
		s2.setTitulo("Sesion 2");
		s2.setDistancia(200);
		s2.setDuracion(200);

		try {
			String myDate = "2024/12/21 18:34:45";
			Date date;
			date = sdf.parse(myDate);
			s2.setFechaInicio(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		s2.setHoraInicio(Calendar.getInstance().getTimeInMillis());

		AutenticacionAppService.getInstance().getUsuario("Iñigo").getSesionesEntrenamiento().add(s2);

		// Inicializar sesion 3
		SesionEntrenamiento s3 = new SesionEntrenamiento();
		s3.setTitulo("Sesion 3");
		s3.setDistancia(150);
		s3.setDuracion(150);

		try {
			String myDate = "2023/10/11 12:12:34";
			Date date;
			date = sdf.parse(myDate);
			s3.setFechaInicio(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		s3.setHoraInicio(Calendar.getInstance().getTimeInMillis());

		AutenticacionAppService.getInstance().getUsuario("Yeray").getSesionesEntrenamiento().add(s3);
	}

	/////////////////////// METODOS PUBLICOS ///////////////////////

	public boolean crearSesionEntrenamiento(Usuario usuario, SesionEntrenamiento sesionEntrenamiento) {
		if (!usuario.getSesionesEntrenamiento().contains(sesionEntrenamiento)) {
			sesionesEntrenamiento.add(sesionEntrenamiento);
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
	
	public static SesionesEntrenamientoAppService getInstance() {
		if (instance == null) {
			instance = new SesionesEntrenamientoAppService();
		}

		return instance;
	}

}