package hr.fer.zemris.java.p12.dao;

import hr.fer.zemris.java.p12.dao.sql.SQLDAO;

/**
 * {@link DAOProvider} is a singleton object which knows how to return {@link DAO}
 * which have access to data persistent layer and returns it in the {@link #getDao()} method.
 * 
 * @author dario
 *
 */
public class DAOProvider {

	/**
	 * {@link SQLDAO} dao 
	 */
    private static DAO dao = new SQLDAO();
	
	/**
	 * Returns the DAO, private static variable.
	 * @return object that encapsulate access to the data persistent layer
	 */
	public static DAO getDao() {
		return dao;
	}
	
}