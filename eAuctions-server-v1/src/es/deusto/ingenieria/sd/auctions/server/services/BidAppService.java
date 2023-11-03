package es.deusto.ingenieria.sd.auctions.server.services;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import es.deusto.ingenieria.sd.auctions.server.data.domain.Article;
import es.deusto.ingenieria.sd.auctions.server.data.domain.Bid;
import es.deusto.ingenieria.sd.auctions.server.data.domain.Category;
import es.deusto.ingenieria.sd.auctions.server.data.domain.User;

//TODO: Implement Singleton Pattern
public class BidAppService {
	
	//TODO: remove when DAO Pattern is implemented
	private List<Category> categories = new ArrayList<>();
	private List<Article> articles = new ArrayList<>();
	
	public BidAppService() {
		//TODO: remove when DAO Pattern is implemented
		this.initilizeData();
	}
	
	//TODO: remove when DAO Pattern is implemented
	private void initilizeData() {
		//Create Users
		User user0 = new User();
		user0.setEmail("thomas.e2001@gmail.com");
		user0.setNickname("Thomas");
		user0.setPassword("$!9PhNz,");
		
		User user1 = new User();
		user1.setEmail("sample@gmail.com");
		user1.setNickname("buyer33");		
		user1.setPassword("hqc`}3Hb");
								
		//Create Categories
		Category catPhone = new Category();
		catPhone.setName("Cell Phones");		

		//Create Articles				
		Article galaxy = new Article();
		galaxy.setNumber(9);
		galaxy.setTitle("Samsung Galaxy S20 128GB");
		galaxy.setInitialPrice(149.99f);
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, 60);
		galaxy.setAuctionEnd(calendar.getTime());
		
		catPhone.addArticle(galaxy);
		galaxy.setCategory(catPhone);
		user1.addArticle(galaxy);
		galaxy.setOwner(user1);
		
		Article iphone = new Article();
		iphone.setNumber(10);
		iphone.setTitle("Apple iPhone 12 64GB");
		iphone.setInitialPrice(216.00f);
		calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, 30);
		iphone.setAuctionEnd(calendar.getTime());			
		
		catPhone.addArticle(iphone);
		iphone.setCategory(catPhone);
		user1.addArticle(iphone);
		iphone.setOwner(user1);
						
		Article xiaomi = new Article();
		xiaomi.setNumber(11);	
		xiaomi.setTitle("Xiaomi Mi 10");
		xiaomi.setInitialPrice(99.40f);
		calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, 11);
		xiaomi.setAuctionEnd(calendar.getTime());			
				
		catPhone.addArticle(xiaomi);		
		xiaomi.setCategory(catPhone);
		user1.addArticle(xiaomi);
		xiaomi.setOwner(user1);
		
		//Add the Category to the local cache.
		this.categories.add(catPhone);
		//Add articles to local cahce
		this.articles.add(galaxy);
		this.articles.add(iphone);
		this.articles.add(xiaomi);
	}
	
	
	public List<Category> getCategories() {
		//TODO: Get all the categories using DAO Pattern		
		return this.categories;
	}

	public List<Article> getArticles(String category) {
		//TODO: Get articles of a category using DAO Pattern
		for (Category cat : this.categories) {
			if (cat.getName().equalsIgnoreCase(category)) {
				return cat.getArticles();
			}
		}
		
		return null;
	}

	public boolean makeBid(User user, int number, float amount) {
		//TODO: Find the artile using DAO Pattern
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

			//TODO: Save the new bin in the DB using DAO Pattern
			
			return true;
		} else {
			return false;
		}
	}
	
	public float getUSDRate() {
		//TODO: Get conversion rate using Service Gateway
		return 0.85f;
	}

	public float getGBPRate() throws RemoteException {
		//TODO: Get conversion rate using Service Gateway
		return 1.17f;
	}
}