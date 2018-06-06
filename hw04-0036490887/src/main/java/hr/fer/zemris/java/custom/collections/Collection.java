package hr.fer.zemris.java.custom.collections;

/**
 * Collection is an "abstract" class for the moment. It contains a lot of method
 * which specification will provide other classes which will inherit this class. 
 * You can only see the list of the methods, even though they don't provider any
 * implementation. 
 * @author dario
 *
 */ 
public class Collection {

	protected Collection() {

	}

	/**
	 * Returns {@code true} if collection contains no objects and {@code false}
	 * otherwise.
	 * 
	 * @return true if the collection is empty, false otherwise
	 */
	public boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * Returns the number of currently stored objects in this collections.
	 * 
	 * @return
	 */
	public int size() {
		return 0;
	}

	/**
	 * Adds the given object into this collection.
	 * 
	 * @param value
	 */
	public void add(Object value) {
		return;
	}

	/**
	 * Returns true only if the collection contains given value, as determined by
	 * {@link #equals(Object) equals} method.
	 * 
	 * @param value
	 * @return
	 */
	public boolean contains(Object value) {
		return false;
	}

	/**
	 * Returns true only if the collection contains given value as determined by
	 * {@link #equals(Object) equals} method and removes one occurrence of it (in
	 * this class it is not specified which one).
	 * 
	 * @param value
	 * @return
	 */
	public boolean remove(Object value) {
		return false;
	}

	/**
	 * Allocates new array with site equals to the size of this collections, fills
	 * it with collection content and returns the array. This method never returns
	 * null.
	 * 
	 * @return
	 */
	public Object[] toArray() {
		throw new UnsupportedOperationException("I need to implement you ...");
	}

	/**
	 * Method calls
	 * {@link #hr.fer.zemris.java.custom.collections.Processor.process()
	 * processor.process(.)} for each element of this collection, The order in which
	 * elements will be sent is undefined in this class.
	 * 
	 * @param processor
	 */
	public void forEach(Processor processor) {
		return;
	}

	/**
	 * Method adds into the current collection all elements from the given collection.
	 * @param other
	 */
	public void addAll(Collection other) {
	}

	/**
	 * Removes all elements from this collection.
	 */
	public void clear() {
		return;
	}
}
