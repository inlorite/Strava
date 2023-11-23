package es.deusto.ingenieria.sd.auctions.server.data.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import es.deusto.ingenieria.sd.auctions.server.data.domain.Category;

//This class implements Singleton and DAO patterns
public class CategoryDAO extends DataAccessObjectBase implements IDataAccessObject<Category> {

	private static CategoryDAO instance;	
	
	private CategoryDAO() { }
	
	public static CategoryDAO getInstance() {
		if (instance == null) {
			instance = new CategoryDAO();
		}		
		
		return instance;
	}
	
	@Override
	public void store(Category object) {
		Category storedObject = instance.find(object.getName());

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
			System.out.println("  $ Error storing Category: " + ex.getMessage());
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}

			em.close();
		}
	}

	@Override
	public void delete(Category object) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();
						
			Category storedObject = (Category) em.find(Category.class, object.getName());
			em.remove(storedObject);
			
			tx.commit();
		} catch (Exception ex) {
			System.out.println("  $ Error removing a Category: " + ex.getMessage());
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}

			em.close();
		}		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Category> findAll() {				
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		List<Category> categories = new ArrayList<>();
		
		try {
			tx.begin();

			Query q = em.createQuery("SELECT c FROM Category c");
			categories = (List<Category>) q.getResultList();

			tx.commit();
		} catch (Exception ex) {
			System.out.println("  $ Error retrieving all the Categories: " + ex.getMessage());
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}

			em.close();
		}

		return categories;		
	}

	@Override
	public Category find(String param) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		Category result = null; 

		try {
			tx.begin();
						
			result = (Category) em.find(Category.class, param);
			
			tx.commit();
		} catch (Exception ex) {
			System.out.println("  $ Error querying a Category by name: " + ex.getMessage());
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}

			em.close();
		}

		return result;
	}
}