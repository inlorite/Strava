package es.deusto.ingenieria.sd.auctions.client.gui;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import es.deusto.ingenieria.sd.auctions.client.controller.BidController;
import es.deusto.ingenieria.sd.auctions.server.data.dto.ArticleDTO;
import es.deusto.ingenieria.sd.auctions.server.data.dto.CategoryDTO;

//This clase simulates the GUI of the Bid use case
public class BidWindow {
	
	private BidController controller;
	
	//This attributes are user for formatting currencies and dates
	private static NumberFormat CURRENCY_FORMATTER = NumberFormat.getCurrencyInstance(Locale.getDefault());
	private static DateFormat DATE_FORMATTER = DateFormat.getDateTimeInstance();
	
	public BidWindow(BidController bid) {
		this.controller = bid;
	}

	public List<CategoryDTO> getCategories() {
		System.out.println(" - Getting all the categories ...");
		
		List<CategoryDTO> categories = this.controller.getCategories();
		
		for (CategoryDTO category : categories) {
			System.out.println("\t* " + category.getName());
		}
		
		return categories;
	}

	public List<ArticleDTO> getArticles(String category) { 		
		System.out.println(" - Getting articles of the category '" + category + "' ...");
		
		List<ArticleDTO> articles = this.controller.getArticles(category);
		
		for (ArticleDTO article : articles) {
			System.out.println("\t* " + article.getNumber() + " - " + 
		                                 article.getTitle() + " -  Initial/actual price: " + 
		                                 CURRENCY_FORMATTER.format(article.getInicialPrice()) + "/" +
		                                 CURRENCY_FORMATTER.format(article.getActualPrice()) + " - (" + 
		                                 article.getTotalBids() + " bids) - End date: " +
		                                 DATE_FORMATTER.format(article.getAuctionEnd()));
		}
			
		return articles;		
	}
	
	public void currencyToGBP(List<ArticleDTO> articles) {
		System.out.println(" - Converting currency from EUR to GBP...");

		float rateGBP = this.controller.getGBPRate();
		
		CURRENCY_FORMATTER = NumberFormat.getCurrencyInstance(Locale.UK);
		
		for (ArticleDTO article : articles) {
			System.out.println("\t* " + article.getNumber() + " - " + 
		                                 article.getTitle() + " -  Initial/actual price: " + 
		                                 CURRENCY_FORMATTER.format(article.getInicialPrice() * rateGBP) + "/" +
		                                 CURRENCY_FORMATTER.format(article.getActualPrice() * rateGBP) + " - (" + 
		                                 article.getTotalBids() + " bids) - End date: " +
		                                 DATE_FORMATTER.format(article.getAuctionEnd()));
		}	
	}

	public void currencyToUSD(List<ArticleDTO> articles) {
		System.out.println(" - Converting currency from EUR to USD...");

		float rateUSD = this.controller.getUSDRate();

		CURRENCY_FORMATTER = NumberFormat.getCurrencyInstance(Locale.US);
		
		for (ArticleDTO article : articles) {
			System.out.println("\t* " + article.getNumber() + " - " + 
		                                 article.getTitle() + " -  Initial/actual price: " + 
		                                 CURRENCY_FORMATTER.format(article.getInicialPrice() * rateUSD) + "/" +
		                                 CURRENCY_FORMATTER.format(article.getActualPrice() * rateUSD) + " - (" + 
		                                 article.getTotalBids() + " bids) - End date: " +
		                                 DATE_FORMATTER.format(article.getAuctionEnd()));
		}	
	}
	
	public void makeBid(long token, ArticleDTO article) {		
		CURRENCY_FORMATTER = NumberFormat.getCurrencyInstance(Locale.getDefault());
		
		//Bid amount is 1 euro greather than the actual price of the article
		float bid = article.getActualPrice()+1; 
		
		System.out.println(" - Making a bid of " + CURRENCY_FORMATTER.format(bid) + " for the article '" + article.getTitle() + "'");
		
		boolean bidResult = this.controller.makeBid(token, article.getNumber(), bid);
		
		System.out.println("\t* Make bid result: " + bidResult);		
	}
}