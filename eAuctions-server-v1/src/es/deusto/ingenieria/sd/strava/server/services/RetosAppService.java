package es.deusto.ingenieria.sd.strava.server.services;

import java.rmi.RemoteException;
import java.security.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import es.deusto.ingenieria.sd.strava.server.data.domain.Reto;
import es.deusto.ingenieria.sd.strava.server.data.domain.Usuario;

//TODO: Implement Singleton Pattern
public class RetosAppService {
	
	private static RetosAppService instance;
	//TODO: remove when DAO Pattern is implemented
	private List<Reto> retos = new ArrayList<>();
	
	private RetosAppService() {
		//TODO: remove when DAO Pattern is implemented
		//this.initializeData();
	}
	
	//TODO: remove when DAO Pattern is implemented
	private void initializeData() {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		
		// Inicializar reto 1
		Reto r1 = new Reto();
		//r1.setCreador("Inigo");
		r1.setDistancia(19.2f);
		r1.setFechaInicio(new Date(Calendar.getInstance().getTimeInMillis()));
		
		try {
			String myDate = "2023/12/29 18:10:45";
			Date date;
			date = sdf.parse(myDate);
			r1.setFechaFin(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		r1.setNombre("Reto 1");
		
		List<String> participantes = new ArrayList<>();
		participantes.add("Inigo");
		//r1.setParticipantes(participantes);
		
		r1.setTiempo(120);
		this.crearReto(r1);
		
		// Inicializar reto 2
		Reto r2 = new Reto();
		//r2.setCreador("Adrian");
		r2.setDistancia(9.4f);
		r2.setFechaInicio(new Date(Calendar.getInstance().getTimeInMillis()));
		
		try {
			String myDate = "2024/01/15 23:59:59";
			Date date;
			date = sdf.parse(myDate);
			r2.setFechaFin(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		r2.setNombre("Reto 2");
		
		List<String> participantes2 = new ArrayList<>();
		participantes2.add("Adrian");
		//r2.setParticipantes(participantes2);
		
		r2.setTiempo(420);
		this.crearReto(r2);
		
		// Inicializar reto 3
		Reto r3 = new Reto();
		//r3.setCreador("Yeray");
		r3.setDistancia(20.2f);
		r3.setFechaInicio(new Date(Calendar.getInstance().getTimeInMillis()));
		
		try {
			String myDate = "2024/03/20 23:59:59";
			Date date;
			date = sdf.parse(myDate);
			r3.setFechaFin(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		r3.setNombre("Reto 3");
		
		List<String> participantes3 = new ArrayList<>();
		participantes3.add("Yeray");
		//r3.setParticipantes(participantes3);
		
		r3.setTiempo(600);
		this.crearReto(r3);
	}
	
	public List<Reto> getRetos(String usuario) {
		//TODO: Get all the categories using DAO Pattern
		List<Reto> retosUsuario = new ArrayList<Reto>();
		
		for (Reto reto : retos) {
			if (reto.getCreador().getNombre().equals(usuario)) {
				retosUsuario.add(reto);
			}
		}
		
		return retosUsuario;
	}
	
	public List<Reto> getRetos() {
		//TODO: Get all the categories using DAO Pattern		
		return this.retos;
	}

	private Reto getRetoPorNombre(String reto) {
		for (Reto r : this.retos) {
			if (r.getNombre().equals(reto)) {
				return r;
			}
		}
		return null;
	}
	
	public boolean crearReto(Reto reto) {
		if(!this.retos.contains(reto)) {
			this.retos.add(reto);
			return true;
		}
		return false;
	}
	
	public boolean apuntarseReto(String usuario, String reto) {
		Reto r = this.getRetoPorNombre(reto);
		
		if (r != null) {
			if (!r.getParticipantes().contains(AutenticacionAppService.getInstance().getUsuario(usuario))) {
				r.getParticipantes().add(AutenticacionAppService.getInstance().getUsuario(usuario));
				return true;
			}
		}
		return false;
	}
	
	public boolean desapuntarseReto(String usuario, String reto) {
		Reto r = this.getRetoPorNombre(reto);		
		
		if (r != null) {
			if (r.getParticipantes().contains(AutenticacionAppService.getInstance().getUsuario(usuario))) {
				r.getParticipantes().remove(AutenticacionAppService.getInstance().getUsuario(usuario));
				return true;
			}
		}
		return false;
		
	}
	
	public boolean eliminarReto(String usuario, String reto) {
		Reto r = this.getRetoPorNombre(reto);
		
		if (r != null) {
			if (r.getCreador().equals(AutenticacionAppService.getInstance().getUsuario(usuario))) {
				this.retos.remove(r);
				return true;
			}
		}
		return false;
	}
	
	public static RetosAppService getInstance() {
		if (instance == null) {
			instance = new RetosAppService();
		}

		return instance;
	}
	
}