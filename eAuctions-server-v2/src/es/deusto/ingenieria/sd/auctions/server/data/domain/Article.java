package es.deusto.ingenieria.sd.auctions.server.data.domain;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Article {
	private int number;
	private String title;	
	private float initialPrice;
	private Date auctionEnd;	
	private Category category;
	private User owner;
	private List<Bid> bids = new ArrayList<>();
		
	public int getNumber() {
		return number;
	}
	
	public void setNumber(int number) {
		this.number = number;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public float getInitialPrice() {
		return initialPrice;
	}

	public void setInitialPrice(float price) {
		this.initialPrice = price;
	}
	
	public float getActualPrice() {
		if (this.bids.isEmpty()) {
			return this.initialPrice;
		} else {
			return this.getHighestBid().getAmount();
		}
	}

	public Date getAuctionEnd() {
		return auctionEnd;
	}

	public void setAuctionEnd(Date auctionEnd) {
		this.auctionEnd = auctionEnd;
	}

	public Category getCategory() {
		return category;
	}
	
	public void setCategory(Category category) {
		this.category = category;
	}
	
	public User getOwner() {
		return owner;
	}
	
	public void setOwner(User owner) {
		this.owner = owner;
	}
	
	public List<Bid> getBids() {
		return bids;
	}
	
	public void setBids(List<Bid> bids) {
		this.bids = bids;
	}
	
	public void addBid(Bid bid) {
		if (bid != null && !this.bids.contains(bid)) {
			this.bids.add(bid);
		}
	}
	
	public Bid getHighestBid() {
		if (!this.bids.isEmpty()) {
			ArrayList<Bid> bidsArray = new ArrayList<Bid>(this.bids);			
			Collections.sort(bidsArray);		
			return bidsArray.get(0);
		} else {
			return null;
		}
	}
	
	@Override
	public String toString() {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MMM-YY - hh:mm");
		NumberFormat numberFormatter = NumberFormat.getCurrencyInstance(Locale.getDefault()); 

		StringBuffer result = new StringBuffer();
		
		result.append(this.number);
		result.append(" # '");
		result.append(this.title);
		result.append("' # Initial/actual price: ");
		result.append(numberFormatter.format(this.initialPrice));
		result.append("/");
		result.append(numberFormatter.format(this.getHighestBid().getAmount()));
		result.append(" # Auction end: ");
		result.append(dateFormatter.format(this.auctionEnd));
		result.append(" (");
		result.append(this.bids.size());
		result.append(" bids)");
		
		return result.toString();		
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this.getClass().getName().equals(obj.getClass().getName())) {
			return this.number == ((Article)obj).number;
		}
		
		return false;
	}
}