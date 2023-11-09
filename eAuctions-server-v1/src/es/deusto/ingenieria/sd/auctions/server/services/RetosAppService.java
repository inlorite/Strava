package es.deusto.ingenieria.sd.auctions.server.services;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import es.deusto.ingenieria.sd.auctions.server.data.domain.Reto;
import es.deusto.ingenieria.sd.auctions.server.data.domain.Usuario;

//TODO: Implement Singleton Pattern
public class RetosAppService {
	
	//TODO: remove when DAO Pattern is implemented
	private List<Reto> retos = new ArrayList<>();
	
	public RetosAppService() {
		//TODO: remove when DAO Pattern is implemented
		this.initilizeData();
	}
	
	//TODO: remove when DAO Pattern is implemented
	private void initilizeData() {
		//Create Users
		
	}
	
	public List<Reto> getRetos() {
		//TODO: Get all the categories using DAO Pattern		
		return this.retos;
	}

	public void crearRetos(String nombre, Date fechaInicio, Date fechaFin, float distancia, float tiempo, Usuario usuario) {
		Reto reto = new Reto();
		reto.setNombre(nombre);
		reto.setFechaInicio(fechaInicio);
		reto.setFechaFin(fechaFin);
		reto.setDistancia(distancia);
		reto.setTiempo(tiempo);
		reto.setCreador(usuario);
	}
	
	public boolean apuntarseReto(Reto reto, Usuario usuario) {
		if (reto.getParticipantes().contains(usuario)) {
			return false;
		} else {
			reto.getParticipantes().add(usuario);
			return true;
		}
	}
	
}