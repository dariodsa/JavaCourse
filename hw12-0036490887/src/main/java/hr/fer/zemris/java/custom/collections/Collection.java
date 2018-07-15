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
    /**
     * Default constructor
     */
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
	 * @return size of a collection
	 */
	public int size() {
		return 0;
	}

	/**
	 * Adds the given object into this collection.
	 * 
	 * @param value object which will be added in collection
	 */
	public void add(Object value) {
		return;
	}

	/**
	 * Returns true only if the collection contains given value, as determined by
	 * {@link #equals(Object) equals} method.
	 * 
	 * @param value object which will be search in a collection
	 * @return true if collection contains object, false otherwise
	 */
	public boolean contains(Object value) {
		return false;
	}

	/**
	 * Returns true only if the collection contains given value as determined by
	 * {@link #equals(Object) equals} method and removes one occurrence of it (in
	 * this class it is not specified which one).
	 * 
	 * @param value object which will be removed from collection
	 * @return true if object was removed, false otherwise
	 */
	public boolean remove(Object value) {
		return false;
	}

	/**
	 * Allocates new array with site equals to the size of this collections, fills
	 * it with collection content and returns the array. This method never returns
	 * null.
	 * 
	 * @return object array from collection
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
	 * @param processor processor
	 */
	public void forEach(Processor processor) {
		return;
	}

	/**
	 * Method adds into the current collection all elements from the given collection.
	 * @param other collection from which all elements will be added to current collection
	 */
	public void addAll(Collection other) {
	    class MyProcessor extends Processor {
            @Override
            public void process(Object value) {
                Collection.this.add(value);
            }
        } 
        
        other.forEach(new MyProcessor());
	}

	/**
	 * Removes all elements from this collection.
	 */
	public void clear() {
		return;
	}
}
