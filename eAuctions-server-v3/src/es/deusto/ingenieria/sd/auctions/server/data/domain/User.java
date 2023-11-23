package es.deusto.ingenieria.sd.auctions.server.data.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class User {

	@Id
	private String email;
	private String nickname;
	private String password;	
	
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
	private Set<Bid> bids = new HashSet<>();
	
	@OneToMany(mappedBy = "owner", fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
	private Set<Article> articles = new HashSet<>();
		
	public String getNickname() {
		return nickname;
	}
	
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public boolean checkPassword(String password) {
		return this.password.equals(password);
	}
	
	public void setPassword(String password) {
		//In this version, the password is hashed when it is assigned
		this.password = org.apache.commons.codec.digest.DigestUtils.sha1Hex(password);
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
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
	
	public Set<Article> getArticles() {
		return articles;
	}
	
	public void setArticles(Set<Article> articles) {
		this.articles = articles;
	}
	
	public void addArticle(Article article) {
		if (article != null) {
			this.articles.add(article);
		}
	}
		
	@Override
	public String toString() {
		StringBuffer result = new StringBuffer();
		
		result.append(this.nickname);
		result.append(" - ");
		result.append(this.email);
		result.append(" - (");
		result.append(this.articles.size());
		result.append(" articles) - (");
		result.append(this.bids.size());
		result.append(" bids)");
		
		return result.toString();
	}

	@Override
	public int hashCode() {
		return Objects.hash(email);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(email, other.email);
	}
}