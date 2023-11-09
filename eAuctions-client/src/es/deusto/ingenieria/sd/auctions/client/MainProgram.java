package es.deusto.ingenieria.sd.auctions.client;

import java.util.List;

import es.deusto.ingenieria.sd.auctions.client.controller.StravaController;
import es.deusto.ingenieria.sd.auctions.client.controller.AutenticacionController;
import es.deusto.ingenieria.sd.auctions.client.gui.StravaWindow;
import es.deusto.ingenieria.sd.auctions.client.gui.AutenticacionWindow;
import es.deusto.ingenieria.sd.auctions.client.remote.ServiceLocator;
import es.deusto.ingenieria.sd.auctions.server.data.dto.ArticleDTO;
import es.deusto.ingenieria.sd.auctions.server.data.dto.CategoryDTO;

public class MainProgram {

	public static void main(String[] args) {
		ServiceLocator serviceLocator = new ServiceLocator();

		System.out.println("test");
		System.out.println("test");
		// args[0] = RMIRegistry IP
		// args[1] = RMIRegistry Port
		// args[2] = Service Name
		serviceLocator.setService(args[0], args[1], args[2]);

		AutenticacionController autenticacionController = new AutenticacionController(serviceLocator);
		AutenticacionWindow autenticacionWindow = new AutenticacionWindow(autenticacionController);
		StravaController StravaController = new StravaController(serviceLocator);
		StravaWindow stravaWindow = new StravaWindow(StravaController);

		// Login
		autenticacionWindow.login();
		
		// Get Categories
		List<CategoryDTO> categories = stravaWindow.getCategories();
		// Get Articles of a category (first category is selected)
		List<ArticleDTO> articles = stravaWindow.getArticles(categories.get(0).getName());
		// Convert currency to GBP
		stravaWindow.currencyToGBP(articles);
		// Convert currency to USD
		stravaWindow.currencyToUSD(articles);
		// Place a bid (first article of the category is selected; the token is stored
		// in the BidController)
		stravaWindow.makeBid(autenticacionController.getToken(), articles.get(0));
		// Get Articles to check if the bid has been done
		articles = stravaWindow.getArticles(categories.get(0).getName());
		
		// Logout
		autenticacionWindow.logout();
	}
}