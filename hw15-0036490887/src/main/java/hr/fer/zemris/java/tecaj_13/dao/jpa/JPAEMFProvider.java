package hr.fer.zemris.java.tecaj_13.dao.jpa;

import javax.persistence.EntityManagerFactory;

/**
 * Class {@link JPAEMFProvider} is responsible for access to the
 * {@link EntityManagerFactory} which offers it in method get and set.
 * 
 * @author dario
 *
 */
public class JPAEMFProvider {

    /**
     * {@link EntityManagerFactory}
     */
    public static EntityManagerFactory emf;

    /**
     * Returns the current {@link EntityManagerFactory}.
     * 
     * @return {@link EntityManagerFactory}
     */
    public static EntityManagerFactory getEmf() {
        return emf;
    }

    /**
     * Sets the {@link EntityManagerFactory}.
     * 
     * @param emf
     *            new {@link EntityManagerFactory}
     */
    public static void setEmf(EntityManagerFactory emf) {
        JPAEMFProvider.emf = emf;
    }
}