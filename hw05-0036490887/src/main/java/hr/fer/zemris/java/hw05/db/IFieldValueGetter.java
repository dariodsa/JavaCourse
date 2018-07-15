package hr.fer.zemris.java.hw05.db;

/**
 * Interface {@link IFieldValueGetter} is responsible for obtaining a requested field 
 * value from given  {@link StudentRecord}. 
 * @author dario
 *
 */
@FunctionalInterface
public interface IFieldValueGetter {
	/**
	 * Returns specific String from {@link StudentRecord}
	 * @param record from where we get String
	 * @return specific string from {@link StudentRecord} 
	 */
	public String get(StudentRecord record);
}
