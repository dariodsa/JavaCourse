package hr.fer.zemris.java.hw05.db.exceptions;

public class ParserException extends RuntimeException {

	public ParserException(String message) {
		super(message);
	}

	public ParserException(Throwable throwable) {
		super(throwable);
	}

}
