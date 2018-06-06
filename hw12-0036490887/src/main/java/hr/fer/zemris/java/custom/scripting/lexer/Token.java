package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Token is a pair of the {@link TokenType} as type and the {@link Object} as
 * the value. Lexer will use Token as the representation of the char data.
 * 
 * @author dario
 * @see {@link Lexer Lexer}
 */
public class Token {

	/**
	 * token type
	 */
    private TokenType tokenType;
	/**
	 * value
	 */
	private Object value;

	/**
	 * Constructs the Token with the TokenType as type and the Object as the value
	 * 
	 * @param tokenType
	 *            {@link TokenType} as type
	 * @param value
	 *            {@link Object} as value
	 */
	public Token(TokenType tokenType, Object value) {
		if (tokenType == null)
			throw new IllegalArgumentException("Token type can't be null.");
		if (value == null && tokenType != TokenType.EOF)
			throw new IllegalArgumentException("Value of token can't be null");

		this.tokenType = tokenType;
		this.value = value;
	}

	/**
	 * Returns the Object value of the Token
	 * 
	 * @return Object value
	 */
	public Object getValue() {
		return this.value;
	}

	/**
	 * Returns the token type.
	 * 
	 * @return {@link hr.fer.zemris.java.hw03.prob1.TokenType}
	 */
	public TokenType getType() {
		return this.tokenType;
	}
}
