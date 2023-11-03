package es.deusto.ingenieria.sd.auctions.server.data.domain;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Article {
	@Id
	private int number;
	private String title;	
	private float initialPrice;
	private Date auctionEnd;
	
	@ManyToOne
	private Category category;
	@ManyToOne
	private User owner;
	
	@OneToMany(mappedBy = "article", fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
	private Set<Bid> bids = new HashSet<>();
		
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
	
	public Set<Bid> getBids() {
		return bids;
	}
	
	public void setBids(Set<Bid> bids) {
		this.bids = bids;
	}
	
	public void addBid(Bid bid) {
		if (bid != null) {
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
	
	public String toString() {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MMM-YY");
		NumberFormat numberFormatter = NumberFormat.getCurrencyInstance(Locale.getDefault()); 

		StringBuffer result = new StringBuffer();
		
		result.append(this.number);
		result.append(" / ");
		result.append(this.title);
		result.append(" / Initial/actual price: ");
		result.append(numberFormatter.format(this.initialPrice));
		result.append("/");
		result.append(numberFormatter.format(this.getActualPrice()));
		result.append(" / Auction end: ");
		result.append(dateFormatter.format(this.auctionEnd));
		result.append(" (");
		result.append(this.bids.size());
		result.append(" bids)");
		
		return result.toString();		
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(number);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Article other = (Article) obj;
		return number == other.number;
	}
}