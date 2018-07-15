package hr.fer.zemris.java.hw07.shell.exceptions;

/**
 * Regular shell exception
 * @author dario
 *
 */
public class ShellException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3263214049553144847L;
	/**
	 * Creates exception with the specific message 
	 * @param message message of the exception
	 */
	public ShellException(String message) {
		super(message);
	}
	/**
	 * Creates exception with the specific throw cause
	 * @param throwable throw cause
	 */
	public ShellException(Throwable throwable) {
		super(throwable);
	}
}
