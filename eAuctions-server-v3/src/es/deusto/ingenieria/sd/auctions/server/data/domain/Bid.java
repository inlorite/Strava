package es.deusto.ingenieria.sd.auctions.server.data.domain;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.datanucleus.api.jpa.annotations.DatastoreId;

@Entity
@DatastoreId
public class Bid implements Comparable<Bid> {
	private Date date;
	private float amount;	
	
	@ManyToOne
	private Article article;
	@ManyToOne
	private User user;

	public void setDate(Date date) {		
		this.date = date;
	}

	public Date getDate() {
		return date;
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
			return -Long.compare(date.getTime(), bid.getDate().getTime());		
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
	public int hashCode() {
		return Objects.hash(article, date, user);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Bid other = (Bid) obj;
		return Objects.equals(article, other.article) && Objects.equals(date, other.date)
				&& Objects.equals(user, other.user);
	}
}