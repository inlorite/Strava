package es.deusto.ingenieria.sd.strava.server.data.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import es.deusto.ingenieria.sd.strava.server.data.domain.Usuario;



//This class implements Singleton and DAO patterns
public class UserDAO extends DataAccessObjectBase implements IDataAccessObject<Usuario> {

	private static UserDAO instance;	
	
	private UserDAO() { }
	
	public static UserDAO getInstance() {
		if (instance == null) {
			instance = new UserDAO();
		}		
		
		return instance;
	}	
	
	@Override
	public void store(Usuario object) {
		Usuario storedObject = instance.find(object.getEmail());

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
	public void delete(Usuario object) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();
			
			Usuario storedObject = (Usuario) em.find(Usuario.class, object.getEmail());
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
	public List<Usuario> findAll() {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		List<Usuario> users = new ArrayList<>();
		try {
			tx.begin();
			Query q = em.createQuery("SELECT u FROM Usuario u");
			users = (List<Usuario>) q.getResultList();	
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
	public Usuario find(String param) {		
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		Usuario result = null; 

		try {
			tx.begin();

			result = (Usuario) em.find(Usuario.class, param);
			
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