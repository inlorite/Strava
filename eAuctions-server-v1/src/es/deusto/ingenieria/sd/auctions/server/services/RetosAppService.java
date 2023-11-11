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
	
	public List<Reto> getRetos(String usuario) {
		//TODO: Get all the categories using DAO Pattern
		List<Reto> retosUsuario = new ArrayList<Reto>();
		
		for (Reto reto : retos) {
			if (reto.getCreador().equals(usuario))
				retosUsuario.add(reto);
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
			if (!r.getParticipantes().contains(usuario)) {
				r.getParticipantes().add(usuario);
				return true;
			}
		}
		return false;
	}
	
	public boolean desapuntarseReto(String usuario, String reto) {
		Reto r = this.getRetoPorNombre(reto);		
		
		if (r != null) {
			if (r.getParticipantes().contains(usuario)) {
				r.getParticipantes().remove(usuario);
				return true;
			}
		}
		return false;
		
	}
	
	public boolean eliminarReto(String usuario, String reto) {
		Reto r = this.getRetoPorNombre(reto);
		
		if (r != null) {
			if (r.getCreador().equals(usuario)) {
				this.retos.remove(r);
				return true;
			}
		}
		return false;
	}
	
}