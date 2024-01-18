package es.deusto.ingenieria.sd.strava.server.services;

import java.rmi.RemoteException;
import java.security.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import es.deusto.ingenieria.sd.strava.server.data.dao.RetoDAO;
import es.deusto.ingenieria.sd.strava.server.data.dao.UserDAO;
import es.deusto.ingenieria.sd.strava.server.data.domain.Reto;
import es.deusto.ingenieria.sd.strava.server.data.domain.Usuario;

//TODO: Implement Singleton Pattern
public class RetosAppService {

	private static RetosAppService instance;

	private RetosAppService() {

	}

	public List<Reto> getRetos(String usuario) {
		// TODO: Get all the categories using DAO Pattern
		List<Reto> retosUsuario = new ArrayList<Reto>();

		for (Reto reto : RetoDAO.getInstance().findAll()) {
			if (reto.getCreador().getNombre().equals(usuario)) {
				retosUsuario.add(reto);
			}
		}

		return retosUsuario;
	}

	public List<Reto> getRetosApuntados(Usuario usuario) {
		List<Reto> retosUsuarioApuntados = new ArrayList<Reto>();

		for (Reto reto : RetoDAO.getInstance().findAll()) {
			if (reto.getParticipantes().contains(usuario)) {
				retosUsuarioApuntados.add(reto);
			}
		}

		return retosUsuarioApuntados;
	}

	public List<Reto> getRetosDesapuntados(Usuario usuario) {
		List<Reto> retosUsuarioDesapuntados = new ArrayList<Reto>();

		for (Reto reto : RetoDAO.getInstance().findAll()) {
			if (!reto.getParticipantes().contains(usuario)) {
				retosUsuarioDesapuntados.add(reto);
			}
		}

		return retosUsuarioDesapuntados;
	}

	public List<Reto> getRetos() {
		return RetoDAO.getInstance().findAll();
	}

	private Reto getRetoPorNombre(String reto) {
		for (Reto r : RetoDAO.getInstance().findAll()) {
			if (r.getNombre().equals(reto)) {
				return r;
			}
		}
		return null;
	}

	public boolean crearReto(Reto reto, Usuario creador) {
		if (RetoDAO.getInstance().find(reto.getNombre()) == null) {
			RetoDAO.getInstance().store(reto);
			AutenticacionAppService.getInstance().getUsuario(creador.getNombre()).getRetosCreados().add(reto);
			AutenticacionAppService.getInstance().getUsuario(creador.getNombre()).getRetosApuntados().add(reto);
			return true;
		}
		return false;
	}

	public boolean apuntarseReto(Usuario usuario, String reto) {
		Reto r = RetoDAO.getInstance().find(reto);
		if (!r.getParticipantes().contains(usuario)) {
			r.getParticipantes().add(usuario);
			AutenticacionAppService.getInstance().getUsuario(usuario.getNombre()).getRetosApuntados().add(r);
			RetoDAO.getInstance().store(r);
			//UserDAO.getInstance().store(AutenticacionAppService.getInstance().getUsuario(usuario.getNombre()));
			
			return true;

		}
		return false;
	}

	public boolean desapuntarseReto(Usuario usuario, String reto) {
		Reto r = RetoDAO.getInstance().find(reto);
		if (r.getParticipantes().contains(usuario)) {
			r.getParticipantes().remove(usuario);
			AutenticacionAppService.getInstance().getUsuario(usuario.getNombre()).getRetosApuntados().remove(r);
			RetoDAO.getInstance().store(r);
			UserDAO.getInstance().store(AutenticacionAppService.getInstance().getUsuario(usuario.getNombre()));
			
			return true;

		}
		return false;

	}

	@SuppressWarnings("unlikely-arg-type")
	public boolean eliminarReto(Usuario usuario, String reto) {
		Reto r = this.getRetoPorNombre(reto);

		if (r != null) {
			if (r.getCreador().equals(usuario.getNombre())) {
				RetoDAO.getInstance().delete(r);
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