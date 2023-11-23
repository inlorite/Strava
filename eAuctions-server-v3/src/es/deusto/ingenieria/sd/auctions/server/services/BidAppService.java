package es.deusto.ingenieria.sd.auctions.server.services;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import es.deusto.ingenieria.sd.auctions.server.data.dao.ArticleDAO;
import es.deusto.ingenieria.sd.auctions.server.data.dao.CategoryDAO;
import es.deusto.ingenieria.sd.auctions.server.data.domain.Article;
import es.deusto.ingenieria.sd.auctions.server.data.domain.Bid;
import es.deusto.ingenieria.sd.auctions.server.data.domain.Category;
import es.deusto.ingenieria.sd.auctions.server.data.domain.User;
import es.deusto.ingenieria.sd.auctions.server.gateway.CurrencyServiceGateway;

public class BidAppService {
	
	//Instance for Singleton Pattern
	private static BidAppService instance;

	private BidAppService() { }
	
	public static BidAppService getInstance() {
		if (instance == null) {
			instance = new BidAppService();
		}
		
		return instance;
	}
	
	public List<Category> getCategories() {
		//Get all the categories using DAO Pattern
		return CategoryDAO.getInstance().findAll();
	}

	public List<Article> getArticles(String category) {
		//Get articles of a category using DAO Pattern
		return new ArrayList<>(CategoryDAO.getInstance().find(category).getArticles());
	}

	public boolean makeBid(User user, int articleNum, float amount) {
		//Find the artile using DAO Pattern
		Article article = ArticleDAO.getInstance().find(String.valueOf(articleNum));

		if (article != null) {
			//Create the Bid
			Bid newBid = new Bid();		
			newBid.setDate(Calendar.getInstance().getTime());
			newBid.setAmount(amount);
			newBid.setArticle(article);
			newBid.setUser(user);		
			article.addBid(newBid);
			user.addBid(newBid);

			//Save the article in the DB using DAO Pattern
			ArticleDAO.getInstance().store(article);
			
			return true;
		} else {
			return false;
		}
	}
	
	public float getUSDRate() {
		//Get conversion rate from Service Gateway
		return CurrencyServiceGateway.getInstance().getUSDRate();
	}

	public float getGBPRate() throws RemoteException {
		//Get conversion rate from Service Gateway
		return CurrencyServiceGateway.getInstance().getGBPRate();
	}
}