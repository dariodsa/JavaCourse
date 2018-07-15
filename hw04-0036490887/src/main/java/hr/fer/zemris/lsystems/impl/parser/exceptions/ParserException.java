package hr.fer.zemris.lsystems.impl.parser.exceptions;

public class ParserException extends RuntimeException{
	public ParserException() {
		super();
	}
	public ParserException(String message) {
		super(message);
	}
	public ParserException(Throwable throwable) {
		super(throwable);
	}
}
