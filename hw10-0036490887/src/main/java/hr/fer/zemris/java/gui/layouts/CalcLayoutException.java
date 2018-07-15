package hr.fer.zemris.java.gui.layouts;
/**
 * Exceptions which could be thrown in calc environment.
 * @author dario
 *
 */
public class CalcLayoutException extends RuntimeException {
    /**
     * 
     */
    private static final long serialVersionUID = 4669058213799689981L;
    /**
     * Constructs {@link CalcLayoutException} with message as their
     * parameter.
     * @param message message of exception
     */
    public CalcLayoutException(String message) {
	super(message);
    }
}
