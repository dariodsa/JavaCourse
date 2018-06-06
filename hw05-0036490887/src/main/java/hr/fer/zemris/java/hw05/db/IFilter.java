package hr.fer.zemris.java.hw05.db;

import hr.fer.zemris.java.hw05.db.exceptions.LikeOperatorException;

/**
 * Interface which says that class that implements must implement method
 * {@link #accepts(StudentRecord)}. It will be used in filtering {@link StudentRecord}.
 * @author dario
 *
 */
@FunctionalInterface
public interface IFilter {
	
	/**
	 * Returns true if the given {@link StudentRecord} pass all expressions, 
	 * false otherwise.
	 * @param student record which will be tested
	 * @return true if the above statament is true, false otherwise
	 * @throws LikeOperatorException 
	 */
	public boolean accepts(StudentRecord record) throws LikeOperatorException;
}
