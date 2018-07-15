package hr.fer.zemris.java.hw05.db;

import hr.fer.zemris.java.hw05.db.exceptions.LikeOperatorException;

/**
 * {@link IComparisonOperator} here is a functional interface, which will
 * be used as a strategy (see strategy design pattern). You need to implement {@link #satisfied(String, String)}. 
 * @author dario
 * @version 1.0
 * @since 1.0
 *
 */
@FunctionalInterface
public interface IComparisonOperator {
	/**
	 * Returns true if the some expression accounting the value1 and value2 in the 
	 * expression is satisfied, false otherwise.
	 *  
	 * @param value1 {@link String} first parameter to the expression
	 * @param value2 {@link String} second parameter to the expression
	 * @return if the expression with value1 and value2 as parameters satisfied returns true, false otherwise
	 * @throws LikeOperatorException 
	 */
	public boolean satisfied(String value1, String value2);
}
