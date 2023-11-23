package es.deusto.ingenieria.sd.auctions.server.data.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import es.deusto.ingenieria.sd.auctions.server.data.domain.Article;

//This class implements Singleton and DAO patterns
public class ArticleDAO extends DataAccessObjectBase implements IDataAccessObject<Article> {

	private static ArticleDAO instance;	
	
	private ArticleDAO() { }
	
	public static ArticleDAO getInstance() {
		if (instance == null) {
			instance = new ArticleDAO();
		}		
		
		return instance;
	}
	
	@Override
	public void store(Article object) {
		Article storedObject = instance.find(String.valueOf(object.getNumber()));

		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();
			
			if (storedObject != null) {
				em.merge(object);
			} else {
				em.persist(object);			
			}
			
			tx.commit();
		} catch (Exception ex) {
			System.out.println("  $ Error storing Article: " + ex.getMessage());
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}

			em.close();
		}
	}

	@Override
	public void delete(Article object) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();
			
			Article storedObject = (Article) em.find(Article.class, 
													 String.valueOf(object.getNumber()));
			em.remove(storedObject);
			
			tx.commit();
		} catch (Exception ex) {
			System.out.println("  $ Error removing an Article: " + ex.getMessage());
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}

			em.close();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Article> findAll() {				
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		List<Article> articles = new ArrayList<>();
		
		try {
			tx.begin();
			
			Query q = em.createQuery("SELECT a FROM Article a");
			articles = (List<Article>) q.getResultList();
			
			tx.commit();
		} catch (Exception ex) {
			System.out.println("  $ Error retrieving all the Articles: " + ex.getMessage());
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}

			em.close();
		}

		return articles;
	}

	@Override
	public Article find(String param) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		Article result = null; 

		try {
			tx.begin();
						
			result = (Article) em.find(Article.class, param);
			
			tx.commit();
		} catch (Exception ex) {
			System.out.println("  $ Error querying an Article by Id: " + ex.getMessage());
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}

			em.close();
		}

		return result;
	}
}