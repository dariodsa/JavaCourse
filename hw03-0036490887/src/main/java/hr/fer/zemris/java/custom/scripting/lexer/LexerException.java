package hr.fer.zemris.java.custom.scripting.lexer;

public class LexerException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5992908046704682268L;

	public LexerException() {
	}

	public LexerException(String message) {
		super(message);
	}

	public LexerException(Throwable throwable) {
		super(throwable);
	}

	public LexerException(String message, Throwable throwable) {
		super(message, throwable);
	}

	public LexerException(String message, Throwable throwable, boolean enableSuppresion, boolean writableStackTrace) {
		super(message, throwable, enableSuppresion, writableStackTrace);
	}

}
