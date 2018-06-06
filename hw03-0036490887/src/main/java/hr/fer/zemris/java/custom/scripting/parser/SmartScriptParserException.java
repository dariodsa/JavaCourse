package hr.fer.zemris.java.custom.scripting.parser;

public class SmartScriptParserException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7143512009280949584L;

	public SmartScriptParserException() {
	}

	public SmartScriptParserException(String message) {
		super(message);
	}

	public SmartScriptParserException(Throwable throwable) {
		super(throwable);
	}

	public SmartScriptParserException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
