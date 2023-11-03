package es.deusto.ingenieria.sd.auctions.server.test;

import java.util.Calendar;
import java.util.List;

import es.deusto.ingenieria.sd.auctions.server.MainProgram;
import es.deusto.ingenieria.sd.auctions.server.data.dao.ArticleDAO;
import es.deusto.ingenieria.sd.auctions.server.data.dao.BidDAO;
import es.deusto.ingenieria.sd.auctions.server.data.dao.CategoryDAO;
import es.deusto.ingenieria.sd.auctions.server.data.dao.UserDAO;
import es.deusto.ingenieria.sd.auctions.server.data.domain.Article;
import es.deusto.ingenieria.sd.auctions.server.data.domain.Bid;
import es.deusto.ingenieria.sd.auctions.server.data.domain.Category;
import es.deusto.ingenieria.sd.auctions.server.data.domain.User;

public class LocalDataBaseTest {

	public static void main(String[] args) {		
		//Initialize DB
		MainProgram.initDB();
		
		List<Category> categories = null;
		List<Article> articles = null;
		List<User> users = null;
		List<Bid> bids = null;
		
		//Retreiving objects from DB
		try {
			System.out.println(" - Retreiving all categories ...");
			categories = CategoryDAO.getInstance().findAll();			
			System.out.println("\t- " + categories.size() + " categories retreived!");
			categories.forEach(cat -> System.out.println("\t\t- " + cat));
		} catch (Exception ex) {
			System.out.println("\t$ Retreiving all categories: " + ex.getMessage());
		}

		try {
			System.out.println(" - Retreiving all articles ...");
			articles = ArticleDAO.getInstance().findAll();						
			System.out.println("\t- " + articles.size() + " articles retreived!");
			articles.forEach(art -> System.out.println("\t\t- " + art));	
		} catch (Exception ex) {
			System.out.println("\t$ Retreiving all articles: " + ex.getMessage());
		}
			
		try {
			System.out.println(" - Retreiving all users ...");
			users = UserDAO.getInstance().findAll();
			System.out.println("\t- " + users.size() + " users retreived!");
			users.forEach(usr -> System.out.println("\t\t- " + usr));
		} catch (Exception ex) {
			System.out.println("\t$ Retreiving all users: " + ex.getMessage());
		}

		try {
			System.out.println(" - Retreiving all bids ...");
			bids = BidDAO.getInstance().findAll();		
			System.out.println("\t- " + bids.size() + " bids retreived!");
			bids.forEach(bid -> System.out.println("\t\t- " + bid));
		} catch (Exception ex) {
			System.out.println("\t$ Retreiving all bids: " + ex.getMessage());
		}

		try {
			if (categories != null && !categories.isEmpty()) {
				String name = categories.get(0).getName();
				System.out.println(" - Retreiving a category by named '" + name + "' ...");
				Category category = CategoryDAO.getInstance().find(name);
				
				if (category != null) {
					System.out.println("\t- Category '" + name + "' has " + category.getArticles().size() + " articles");
					category.getArticles().forEach(art -> System.out.println("\t\t- " + art));				
				}
			}
		} catch (Exception ex) {
			System.out.println("\t$ Retreiving a category by name: " + ex.getMessage());
		}
			
		
		Article article = null;
		User user = null;
				
		if (articles != null && !articles.isEmpty()) {			
			System.out.println(" - Retreiving an article by number '" + articles.get(0).getNumber() + "' ...");
			article = ArticleDAO.getInstance().find(String.valueOf(articles.get(0).getNumber()));				
			
			if (article != null) {
				System.out.println("\t- " + article);
			}
		}
			
		if (users != null && !users.isEmpty()) {
			System.out.println(" - Retreiving an user by email '" + users.get(0).getEmail() + "' ...");
			user = UserDAO.getInstance().find(String.valueOf(users.get(0).getEmail()));
			
			if (user != null) {
				System.out.println("\t- " + user);
			}				
		}
		
		if (article != null && user != null) {
			System.out.println(" - Making a bid ...");
			Bid newBid = new Bid();		
			newBid.setDate(Calendar.getInstance().getTime());			
			newBid.setAmount(article.getActualPrice()+1);
			newBid.setUser(user);
			newBid.setArticle(article);
		
			article.addBid(newBid);
			user.addBid(newBid);
			
			System.out.println("\t- " + newBid);
			
			System.out.println(" - Storing the bid...");
			BidDAO.getInstance().store(newBid);
			
			System.out.println(" - Retreiving all bids ...");
			bids = BidDAO.getInstance().findAll();		
			System.out.println("\t- " + bids.size() + " bids retreived!");
			bids.forEach(bid -> System.out.println("\t\t- " + bid));		
		}
		
		try {
			//Clean DB
			System.out.println(" - Deleting all categories ...");
			categories = CategoryDAO.getInstance().findAll();
			categories.forEach(cat -> CategoryDAO.getInstance().delete(cat));
			
			System.out.println(" - Deleting all users ...");
			users = UserDAO.getInstance().findAll();
			users.forEach(usr -> UserDAO.getInstance().delete(usr));			
			
			System.out.println(" - Retreiving all categories ...");
			categories = CategoryDAO.getInstance().findAll();			
			System.out.println("\t- " + categories.size() + " categories retreived!");
			
			System.out.println(" - Retreiving all articles ...");
			articles = ArticleDAO.getInstance().findAll();						
			System.out.println("\t- " + articles.size() + " articles retreived!");
			
			System.out.println(" - Retreiving all users ...");
			users = UserDAO.getInstance().findAll();
			System.out.println("\t- " + users.size() + " users retreived!");
			
			System.out.println(" - Retreiving all bids ...");
			bids = BidDAO.getInstance().findAll();		
			System.out.println("\t- " + bids.size() + " bids retreived!");
		} catch (Exception ex) {
			System.out.println("\t$ Testing DAO: " + ex.getMessage());
		}
	}
}