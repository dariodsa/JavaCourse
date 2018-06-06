package hr.fer.zemris.java.hw07.shell.exceptions;

/**
 * {@link ShellIOException} is generic exception that 
 * could appear in shell commands. 
 *   
 * @author dario
 *
 */
public class ShellIOException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6182616799394195468L;
	/**
	 * Constructor for {@link ShellIOException}
	 * @param message exception's message
	 */
	public ShellIOException(String message) {
		super(message);
	}
	/**
	 *  Constructor for {@link ShellIOException}
	 * @param cause cause of exception
	 */
	public ShellIOException(Throwable cause) {
		super(cause);
	}
}
