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

	public List<Category> getCategories() {
		// TODO: Get all the categories using DAO Pattern
		return this.categories;
	}

	public List<Article> getArticles(String category) {
		// TODO: Get articles of a category using DAO Pattern
		for (Category cat : this.categories) {
			if (cat.getName().equalsIgnoreCase(category)) {
				return cat.getArticles();
			}
		}

		return null;
	}

	public boolean makeBid(Usuario user, int number, float amount) {
		// TODO: Find the artile using DAO Pattern
		Article article = null;

		for (Article art : this.articles) {
			if (art.getNumber() == number) {
				article = art;
				break;
			}
		}

		if (article != null) {
			Bid newBid = new Bid();
			newBid.setDate(Calendar.getInstance().getTime());
			newBid.setAmount(amount);
			newBid.setArticle(article);
			newBid.setUser(user);
			article.addBid(newBid);
			user.addBid(newBid);

			// TODO: Save the new bin in the DB using DAO Pattern

			return true;
		} else {
			return false;
		}
	}

	public float getUSDRate() {
		// TODO: Get conversion rate using Service Gateway
		return 0.85f;
	}

	public float getGBPRate() throws RemoteException {
		// TODO: Get conversion rate using Service Gateway
		return 1.17f;
	}

	private int getUsuarioPorIndex(String usuario) {

		for (Usuario u : AutenticacionAppService.getInstance().getUsuarios()) {
			if (u.getNombre().equals(usuario)) {
				return AutenticacionAppService.getInstance().getUsuarios().indexOf(u);
			}
		}
		return -1;

	}
}