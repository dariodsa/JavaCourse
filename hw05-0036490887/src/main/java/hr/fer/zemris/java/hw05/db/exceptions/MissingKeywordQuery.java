package hr.fer.zemris.java.hw05.db.exceptions;

public class MissingKeywordQuery extends ParserException {
	public static final String MESSAGE = "Keyword \"query\" was not found in the given string.";
	public MissingKeywordQuery() {
		super(MESSAGE);
	}
}
