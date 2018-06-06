package hr.fer.zemris.java.custom.collections;

/**
 * Creates ObjectStack class, which allows you to add elements and return it following the LIFO structure.
 * You can ask the structure if it is empty, and its size. Also, like a stack  is allows you to 
 * pop , get the peek of the stack and push the {@code Object} value at the top of the collection.
 * @author dario
 *
 */
public class ObjectStack {
    
    /**
     * stack
     */
	private ArrayIndexedCollection stack;
	/**
	 * Construct the ObjectStack by creating ArrayIndexed collection.
	 */
	public ObjectStack() {
		this.stack = new ArrayIndexedCollection();
	}

	/**
	 * Returns true if there is no elements in the stack, false otherwise.
	 * 
	 * @return true if stack is empty, false otherwise
	 */
	public boolean isEmpty() {
		return stack.isEmpty();
	}

	/**
	 * Returns the number of elements in the stack.
	 * 
	 * @return {@code int} - number of elements in the stack
	 */
	public int size() {
		return stack.size();
	}

	/**
	 * Adds the object at the top of the stack.
	 * 
	 * @param value
	 *            which will be added to the top of the stack
	 */
	public void push(Object value) {
		stack.add(value);
	}
	/**
	 * Returns element at the top of the stack.
	 * @return element at the top of the stack
	 * @throws EmptyStackException stack is empty
	 */
	public Object pop() {
		if (size() == 0)
			throw new hr.fer.zemris.java.custom.collections.EmptyStackException();
		Object peek = stack.get(size() - 1);
		stack.remove(size() - 1);
		return peek;
	}

	/**
	 * Returns the last element placed in the stack, but doesn't delete it from the
	 * stack.
	 * 
	 * @return peek of the stack
	 */
	public Object peek() {
	    return stack.get(size() - 1);
	}

	/**
	 * Removes all elements from the stack.
	 */
	public void clear() {
		stack.clear();
	}
}
