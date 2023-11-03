package es.deusto.ingenieria.sd.auctions.server.data.domain;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Bid implements Comparable<Bid> {
	private long date;
	private float amount;	
	private Article article;
	private User user;

	public void setDate(Date date) {		
		this.date = date.getTime();
	}

	public Date getDate() {
		return new Date(this.date);
	}
	
	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public int compareTo(Bid bid) {
		if (bid != null) {
			return -Long.compare(this.date, bid.date);
		} else {		
			return 0;
		}
	}
	
	@Override
	public String toString() {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MMM-YY - hh:mm");
		NumberFormat numberFormatter = NumberFormat.getCurrencyInstance(Locale.getDefault()); 

		StringBuffer result = new StringBuffer("User:");
		
		result.append(this.getUser().getNickname());
		result.append(" - Article:");
		result.append(this.getArticle().getTitle());
		result.append(" - Date:");
		result.append(dateFormatter.format(this.date));
		result.append(" - Amount:");
		result.append(numberFormatter.format(this.amount));
		
		return result.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this.getClass().getName().equals(obj.getClass().getName())) {
			Bid bid = (Bid)obj;
			
			if (this.user != null && this.article != null &&
					bid.user != null && bid.article != null) {			
					return this.date == bid.date &&
						   this.amount == bid.amount &&
						   this.article.equals(bid.article) &&
						   this.user.equals(bid.user);
				}
		}
		
		return false;
	}
}