package hr.fer.zemris.java.tecaj_13.dao.jpa;

import javax.persistence.EntityManager;

import hr.fer.zemris.java.tecaj_13.dao.DAOException;

/**
 * Class {@link JPAEMProvider} returns the {@link EntityManager} and this class is responsible for
 * accessing and closing that object.
 *  
 * @author dario
 *
 */
public class JPAEMProvider {

    /**
     * {@link ThreadLocal} {@link EntityManager}
     */
	private static ThreadLocal<EntityManager> locals = new ThreadLocal<>();

	/**
	 * Returns the {@link EntityManager}.
	 * @return {@link EntityManager}
	 */
	public static EntityManager getEntityManager() {
		EntityManager em = locals.get();
		if(em==null) {
			em = JPAEMFProvider.getEmf().createEntityManager();
			em.getTransaction().begin();
			locals.set(em);
		}
		return em;
	}
	/**
	 * Close the {@link JPAEMProvider}.
	 * @throws DAOException exception
	 */
	public static void close() throws DAOException {
		EntityManager em = locals.get();
		if(em==null) {
			return;
		}
		DAOException dex = null;
		try {
		    em.getTransaction().commit();
		} catch(Exception ex) {
			dex = new DAOException("Unable to commit transaction.", ex);
		}
		try {
			em.close();
		} catch(Exception ex) {
			if(dex!=null) {
				dex = new DAOException("Unable to close entity manager.", ex);
			}
		}
		locals.remove();
		if(dex!=null) throw dex;
	}
	
}