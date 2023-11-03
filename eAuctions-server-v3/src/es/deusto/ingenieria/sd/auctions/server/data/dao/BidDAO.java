package es.deusto.ingenieria.sd.auctions.server.data.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import es.deusto.ingenieria.sd.auctions.server.data.domain.Article;
import es.deusto.ingenieria.sd.auctions.server.data.domain.Bid;

//This class implements Singleton and DAO patterns
public class BidDAO extends DataAccessObjectBase implements IDataAccessObject<Bid> {

	private static BidDAO instance;	
	
	private BidDAO() { }
	
	public static BidDAO getInstance() {
		if (instance == null) {
			instance = new BidDAO();
		}		
		
		return instance;
	}
	
	@Override
	public void store(Bid object) {
		super.saveObject(object);
	}

	@Override
	public void delete(Bid object) {
		super.deleteObject(object);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Bid> findAll() {			
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		List<Bid> bids = new ArrayList<>();

		try {
			tx.begin();
			
			Query q = em.createQuery("SELECT b FROM Bid b");
			bids = (List<Bid>) q.getResultList();

			tx.commit();
		} catch (Exception ex) {
			System.out.println("  $ Error retrieving all the Bids: " + ex.getMessage());
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}

			em.close();
		}

		return bids;
	}

	@Override
	public Bid find(String param) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		Article article = null;
		Bid result = null;

		try {
			tx.begin();
						
			article = (Article) em.find(Article.class, param);
			
			if (article != null) {
				result = article.getHighestBid();
			}
			
			tx.commit();
		} catch (Exception ex) {
			System.out.println("  $ Error querying Highest Bid of an Article: " + ex.getMessage());
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}

			em.close();
		}		

		return result;
	}
}