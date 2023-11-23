package es.deusto.ingenieria.sd.auctions.server.data.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import es.deusto.ingenieria.sd.auctions.server.data.domain.User;

//This class implements Singleton and DAO patterns
public class UserDAO extends DataAccessObjectBase implements IDataAccessObject<User> {

	private static UserDAO instance;	
	
	private UserDAO() { }
	
	public static UserDAO getInstance() {
		if (instance == null) {
			instance = new UserDAO();
		}		
		
		return instance;
	}	
	
	@Override
	public void store(User object) {
		User storedObject = instance.find(object.getEmail());

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
			System.out.println("  $ Error storing User: " + ex.getMessage());
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}

			em.close();
		}
	}

	@Override
	public void delete(User object) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();
			
			User storedObject = (User) em.find(User.class, object.getEmail());
			em.remove(storedObject);
			
			tx.commit();
		} catch (Exception ex) {
			System.out.println("  $ Error removing an User: " + ex.getMessage());
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}

			em.close();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> findAll() {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		List<User> users = new ArrayList<>();

		try {
			tx.begin();

			Query q = em.createQuery("SELECT u FROM User u");
			users = (List<User>) q.getResultList();
						
			tx.commit();
		} catch (Exception ex) {
			System.out.println("  $ Error querying all users: " + ex.getMessage());
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}

			em.close();
		}

		return users;
	}

	@Override
	public User find(String param) {		
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		User result = null; 

		try {
			tx.begin();

			result = (User) em.find(User.class, param);
			
			tx.commit();
		} catch (Exception ex) {
			System.out.println("  $ Error querying a User by email: " + ex.getMessage());
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}

			em.close();
		}

		return result;
	}
}