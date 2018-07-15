package hr.fer.zemris.java.hw16.trazilica.processor.structures;

/**
 * Interface {@link Structure} specifies how should structure behave, for
 * example duplicates in the structure are forbidden.
 * 
 * @author dario
 *
 */
public interface Structure extends Iterable<StringPair> {
    /**
     * Adds new {@link StringPair} to the structure. If structure already consist of
     * that element method will return false, otherwise true.
     * 
     * @param pair
     *            new element
     * @return result of the adding operation
     */
    public boolean add(StringPair pair);

    /**
     * Returns the {@link StringPair} with the same text as the one given as the
     * argument.
     * 
     * @param pair
     *            its text will be searched in the structure
     * @return {@link StringPair} found or null if it doesn't exsist
     */
    public StringPair get(StringPair pair);

    /**
     * Returns the size of a structure
     * 
     * @return number of elements in the structure
     */
    public int size();
}
