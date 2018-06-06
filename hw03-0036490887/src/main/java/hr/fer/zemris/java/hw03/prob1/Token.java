package hr.fer.zemris.java.hw03.prob1;

/**
 * Represents the Token which will be produce by the {@link Lexer}. Token is
 * constructed with {@link TokenType} as the type and {@link Object} as the
 * value. Type can't be null, otherwise it will produce
 * {@link NullPointerException}. On the another hand, value can be null.
 * 
 * @author dario
 *
 */
public class Token {

	private TokenType type;
	private Object value;

	/**
	 * Constructs the Token with the given {@code TokenType} tokenType and
	 * {@code Object} value.
	 * 
	 * @param type
	 *            - {@code TokenType} type of the token
	 * @param value
	 *            - {@code Object} value of the token
	 * @throws NullPointerException
	 */
	public Token(TokenType type, Object value) {
		if (type == null)
			throw new NullPointerException("Type can't be null.");

		this.type = type;
		this.value = value;
	}

	/**
	 * Returns the value of the {@code Token}.
	 * 
	 * @return - value of the token
	 */
	public Object getValue() {
		return this.value;
	}

	/**
	 * Returns the token type.
	 * 
	 * @return {@code TokenType} - enum - type of the token
	 */
	public TokenType getType() {
		return this.type;
	}

	/*
	 * Represents the Token in the string format. Format is given: (tokenType, token
	 * value). If the token value is null, it will print null.
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		if (value == null)
			return "(" + type + ", null)";
		return "(" + type + ", " + value.toString() + ")";
	}
}
