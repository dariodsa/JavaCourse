package hr.fer.zemris.java.custom.collections;

/**
 * {@link EmptyStackException} extends {@link RuntimeException} and 
 * it will be called when the stack is empty.
 * @author dario
 *
 */
public class EmptyStackException extends RuntimeException {
	
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /**
     * Default constructor.
     */
	public EmptyStackException() {
		super();
	}
	/**
	 * Constructs {@link EmptyStackException} with a given message.
	 * @param message exception message
	 */
	public EmptyStackException(String message) {
		super(message);
	}
	
}
