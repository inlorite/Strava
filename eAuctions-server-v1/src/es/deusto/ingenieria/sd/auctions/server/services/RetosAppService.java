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

	public boolean crearRetos(Reto reto) {
		
		if(!this.retos.contains(reto)) {
			this.retos.add(reto);
			return true;
		} else {
			return false;
		}
		
	}
	
	private Usuario getUsuarioPorNombre(String usuario) {
		
		for (Usuario u : AutenticacionAppService.getInstance().getUsuarios()) {
			if (u.getNombre().equals(usuario)) {
				return u;
			}
		}
		return null;
		
	}
	
	private Reto getRetoPorNombre(String reto) {
		
		for (Reto r : this.retos) {
			if (r.getNombre().equals(reto)) {
				return r;
			}
		}
		return null;
		
	}
	
	public boolean apuntarseReto(String usuario, String reto) {
		
		Usuario u = this.getUsuarioPorNombre(usuario);
		Reto r = this.getRetoPorNombre(reto);
		
		if (u != null && r != null) {
			if (r.getParticipantes().contains(u)) {
				return false;
			} else {
				r.getParticipantes().add(u);
				return true;
			}
		} else {
			return false;
		}
		
	}
	
	public boolean desapuntarseReto(String usuario, String reto) {
		
		Usuario u = this.getUsuarioPorNombre(usuario);
		Reto r = this.getRetoPorNombre(reto);		
		
		if (u != null && r != null) {
			if (r.getParticipantes().contains(u)) {
				r.getParticipantes().remove(u);
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
		
	}
	
	public boolean eliminarReto(String reto) {
		
		Reto r = this.getRetoPorNombre(reto);
		
		if (this.retos.contains(r)) {
			this.retos.remove(r);
			return true;
		} else {
			return false;
		}
		
	}
	
}