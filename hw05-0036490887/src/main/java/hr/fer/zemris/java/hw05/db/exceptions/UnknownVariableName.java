package hr.fer.zemris.java.hw05.db.exceptions;

public class UnknownVariableName extends ParserException {

	/**
	 * general message for this exception
	 */
	private static final String MESSAGE = "You used unknown variable name.";

	public UnknownVariableName(String message) {
		super(MESSAGE + "\n" + message);
		
	}

}
