package es.deusto.ingenieria.sd.strava.server.data.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import es.deusto.ingenieria.sd.strava.server.data.domain.SesionEntrenamiento;



//This class implements Singleton and DAO patterns
public class SesionEntrenamientoDAO extends DataAccessObjectBase implements IDataAccessObject<SesionEntrenamiento> {

	private static SesionEntrenamientoDAO instance;	
	
	private SesionEntrenamientoDAO() { }
	
	public static SesionEntrenamientoDAO getInstance() {
		if (instance == null) {
			instance = new SesionEntrenamientoDAO();
		}		
		
		return instance;
	}	
	
	@Override
	public void store(SesionEntrenamiento object) {
		SesionEntrenamiento storedObject = instance.find(object.getTitulo());

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
	public void delete(SesionEntrenamiento object) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();
			
			SesionEntrenamiento storedObject = (SesionEntrenamiento) em.find(SesionEntrenamiento.class, object.getTitulo());
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
	public List<SesionEntrenamiento> findAll() {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		List<SesionEntrenamiento> users = new ArrayList<>();

		try {
			tx.begin();

			Query q = em.createQuery("SELECT u FROM User u");
			users = (List<SesionEntrenamiento>) q.getResultList();
						
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
	public SesionEntrenamiento find(String param) {		
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		SesionEntrenamiento result = null; 

		try {
			tx.begin();

			result = (SesionEntrenamiento) em.find(SesionEntrenamiento.class, param);
			
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