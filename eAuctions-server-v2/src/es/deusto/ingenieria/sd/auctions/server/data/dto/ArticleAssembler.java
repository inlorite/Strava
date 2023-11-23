package es.deusto.ingenieria.sd.auctions.server.data.dto;

import java.util.ArrayList;
import java.util.List;

import es.deusto.ingenieria.sd.auctions.server.data.domain.Article;

//This class is part of the DTO pattern. It also implements Singleton Pattern.
public class ArticleAssembler {	
	private static ArticleAssembler instance;

	private ArticleAssembler() { }
	
	public static ArticleAssembler getInstance() {
		if (instance == null) {
			instance = new ArticleAssembler();
		}

		return instance;
	}

	public ArticleDTO articleToDTO(Article article) {
		ArticleDTO dto = new ArticleDTO();
		
		dto.setNumber(article.getNumber());
		dto.setTitle(article.getTitle());
		dto.setAuctionEnd(article.getAuctionEnd());
		dto.setInicialPrice(article.getInitialPrice());
		dto.setActualPrice(article.getActualPrice());
		dto.setTotalBids(article.getBids().size());
				
		return dto;
	}
	
	public List<ArticleDTO> articleToDTO(List<Article> articles) {
		List<ArticleDTO> dtos = new ArrayList<>();
		
		for (Article article : articles) {
			dtos.add(this.articleToDTO(article));
		}
		
		return dtos;		
	}
}