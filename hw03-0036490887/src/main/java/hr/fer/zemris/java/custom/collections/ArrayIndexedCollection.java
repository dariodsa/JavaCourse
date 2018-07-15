package hr.fer.zemris.java.custom.collections;

/**
 * ArrayIndexedCollection stores {@code Object} objects in array. It is allowed
 * you to add elements, get them from structure, ask collection if there is some
 * and remove them out. Complexity of the given operations are: Insert O(N)
 * Delete O(N) Get O(1) Find O(N)
 * 
 * @author dario
 *
 */
public class ArrayIndexedCollection extends Collection {

	private static final int INIT_SIZE = 16;

	private int size;
	private int capacity;
	private Object[] elements;

	/**
	 * Constructs the array of initial size. Initial size is 16.
	 */
	public ArrayIndexedCollection() {

		this(INIT_SIZE);
	}

	/**
	 * Constructs the array of the given capacity.
	 * 
	 * @param initialCapacity
	 *            initial capacity
	 * @throws IllegalArgumentException
	 *             if capacity is less than one
	 */
	public ArrayIndexedCollection(int initialCapacity) {

		if (initialCapacity < 1)
			throw new IllegalArgumentException("Initial capacity must be bigger than 0.");
		this.capacity = initialCapacity;
		this.elements = new Object[capacity];
	}

	/**
	 * Constructs the collection from the given collection. It doesn't copy the
	 * objects to the array.
	 * 
	 * @param collection
	 * @throws NullPointerException
	 *             if the collection is null
	 */
	public ArrayIndexedCollection(Collection collection) {
		this(collection, 0);
	}

	/**
	 * Constructs the collection from the given collection and with initial capacity
	 * as parameters. If the initial capacity is less than collection size, the
	 * result will be array with the size of the collection. Otherwise initial
	 * capacity is set as the size of the result array.
	 * 
	 * @param collection
	 * @param initialCapacity
	 * @throws NullPointerException
	 */
	public ArrayIndexedCollection(Collection collection, int initialCapacity) {
		if (collection == null)
			throw new NullPointerException("Given collection is null.");
		if (collection.size() == 0)
			throw new IllegalArgumentException(
					"You can't generate new collection from exsiting collection with size zero.");
		int arraySize = Math.max(collection.size(), initialCapacity);
		this.elements = new Object[arraySize];

		addAll(collection);
		/*
		 * for(int i = 0, len = collection.size(); i < len; ++i) {
		 * add(collection.toArray()[i]); }
		 */
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public void add(Object value) {

		if (value == null) {
			throw new NullPointerException("Value that we want to add in list can't be null");
		}

		if (size + 1 >= capacity) {
			increaseCapacity(capacity * 2);
		}

		elements[size++] = value;
		return;
	}

	/**
	 * Returns the n-th {@code Object} in the array.
	 * 
	 * @param n-th
	 *            element in the array
	 * @return
	 * @throws IllegalArgumentException
	 */
	public Object get(int index) {

		if (index < 0 || index >= size)
			throw new IllegalArgumentException(
					String.format("Index is not in the supported range (%d - %d).%nIndex was %d.", 0, size - 1, index));

		return this.elements[index];
	}

	/**
	 * Private method which increase capacity to new capacity and relocates the
	 * existing objects.
	 * 
	 * @param capacity
	 *            new capacity of the array
	 */
	private void increaseCapacity(int capacity) {

		this.capacity = capacity;
		Object[] values = new Object[capacity];
		for (int i = 0; i < size; ++i) {
			values[i] = elements[i];
		}
		this.elements = values;

	}

	/**
	 * Inserts the {@code Object} value in the wanted position.
	 * 
	 * @param value
	 *            value which will be added in array
	 * @param position
	 *            position on which will be added
	 * @throws IllegalArgumentException
	 * @throws NullPointerException
	 */
	public void insert(Object value, int position) {

		if (value == null)
			throw new NullPointerException("Value can't be null");
		if (position < 0 || position > size)
			throw new IllegalArgumentException(String
					.format("Position is not in the supported range (%d - %d).%nIndex was %d.", 0, size - 1, position));

		if (size + 1 >= capacity) {
			increaseCapacity(capacity * 2);
		}

		for (int i = size - 1; i >= position; --i) {
			elements[i + 1] = elements[i];
		}
		elements[position] = value;
		++size;

	}

	public int indexOf(Object value) {
		if (value == null)
			return -1;
		for (int i = 0; i < size; ++i) {
			if (elements[i].equals(value))
				return i;
		}
		return -1;
	}

	/**
	 * Removes the object at the given index.
	 * 
	 * @param index
	 * @throws IndexOutOfBoundsException
	 *             if the index is not in the array bounds.
	 */
	public void remove(int index) {
		if (index < 0 || index >= size)
			throw new IndexOutOfBoundsException(
					String.format("Index is not in the supported range (%d - %d).%nIndex was %d.", 0, size - 1, index));
		for (int i = index; i < size - 1; ++i) {
			elements[i] = elements[i + 1];
		}
		size = size - 1;
	}

	@Override
	public boolean contains(Object value) {
		if (value == null)
			throw new NullPointerException("Value can't be null");

		for (int i = 0; i < size; ++i) {
			if (elements[i].equals(value))
				return true;
		}
		return false;
	}

	/**
	 * Returns true only if the collection contains given value as determined by
	 * {@link #equals(Object) equals} method and removes one occurrence of it (in
	 * this class it is not specified which one).
	 * 
	 * @param value
	 * @return boolean
	 */
	@Override
	public boolean remove(Object value) {
		if (value == null)
			throw new NullPointerException("Value can't be null.");
		for (int i = 0; i < size; ++i) {
			if (get(i).equals(value)) {
				for (int j = i; j < size - 1; ++j) {
					elements[j] = elements[j + 1];
				}
				return true;
			}

		}
		return false;
	}

	/**
	 * Allocates new array with site equals to the size of this collections, fills
	 * it with collection content and returns the array. This method never returns
	 * null.
	 * 
	 * @return Object[] array of objects
	 */
	@Override
	public Object[] toArray() {
		if(size == 0) 
			throw new NullPointerException("Array is empty, null");
		Object[] array = new Object[this.size];
		for(int i=0;i<size;++i) {
			array[i] = this.elements[i];
		}
		return array;
	}

	@Override
	public void forEach(Processor processor) {
		for (int i = 0; i < size(); ++i) {
			processor.process(get(i));
		}
		return;
	}

	@Override
	public void addAll(Collection other) {
		other.forEach(new Processor() {
			@Override
			public void process(Object value) {
				add(value);
			}
		});
		return;
	}

	/**
	 * Removes all elements from this collection.
	 */
	@Override
	public void clear() {
		for (int i = 0; i < size; ++i) {
			this.elements[i] = null;
		}
		this.size = 0;
		return;
	}

}
