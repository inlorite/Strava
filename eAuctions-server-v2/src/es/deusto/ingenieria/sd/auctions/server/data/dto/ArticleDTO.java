package es.deusto.ingenieria.sd.auctions.server.data.dto;

import java.io.Serializable;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

//This class implements DTO pattern
public class ArticleDTO implements Serializable {
	//This attribute is needed to implement the "Serializable" interface.
	private static final long serialVersionUID = 1L;
	private int number;
	private String title;
	private Date auctionEnd;
	private float inicialPrice;
	private float actualPrice;
	private int totalBids;
	
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
	
	public Date getAuctionEnd() {
		return auctionEnd;
	}

	public void setAuctionEnd(Date auctionEnd) {
		this.auctionEnd = auctionEnd;
	}

	public float getInicialPrice() {
		return inicialPrice;
	}

	public void setInicialPrice(float inicialPrice) {
		this.inicialPrice = inicialPrice;
	}

	public float getActualPrice() {
		return actualPrice;
	}

	public void setActualPrice(float actualPrice) {
		this.actualPrice = actualPrice;
	}

	public int getTotalBids() {
		return totalBids;
	}

	public void setTotalBids(int totalBids) {
		this.totalBids = totalBids;
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
		result.append(numberFormatter.format(this.inicialPrice));
		result.append("/");
		result.append(numberFormatter.format(this.actualPrice));
		result.append(" # Auction end: ");
		result.append(dateFormatter.format(this.auctionEnd));
		result.append(" (");
		result.append(this.totalBids);
		result.append(" bids)");
		
		return result.toString();
	}
}