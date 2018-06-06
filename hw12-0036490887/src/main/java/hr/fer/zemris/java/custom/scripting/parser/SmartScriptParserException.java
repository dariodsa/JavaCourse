package hr.fer.zemris.java.custom.scripting.parser;

/**
 * {@link SmartScriptParserException} extends {@link RuntimeException} and 
 * specifies exceptions caused in the parsing.
 * @author dario
 *
 */
public class SmartScriptParserException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7143512009280949584L;

	/**
	 * Constructs {@link SmartScriptParserException} with messsage.
	 * @param message message of the exception
	 */
	public SmartScriptParserException(String message) {
		super(message);
	}
	/**
	 * 
	 * @param throwable cause of the exception
	 */
	public SmartScriptParserException(Throwable throwable) {
		super(throwable);
	}
}
