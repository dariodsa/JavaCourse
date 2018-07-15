package hr.fer.zemris.java.tecaj_13.dao;

/**
 * Class {@link DAOException} extends {@link RuntimeException} and encapsulate
 * exception that will be thrown in case of DAO error.
 * 
 * @author dario
 *
 */
public class DAOException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor of {@link DAOException}.
     */
    public DAOException() {
    }

    /**
     * Constructs {@link DAOException} with {@link Throwable} cause so that every
     * exception thrown in dealing with DAO can be decorated in
     * {@link DAOException}.
     * 
     * @param message
     *            message
     * @param cause
     *            cause
     */
    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }
}