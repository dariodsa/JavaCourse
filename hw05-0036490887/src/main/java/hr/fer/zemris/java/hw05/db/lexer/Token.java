package hr.fer.zemris.java.hw05.db.lexer;

/**
 * Class {@link Token} has its value and type as parameters and it offers getters to 
 * access them. 
 * @author dario
 *
 */

public class Token {
	
	/**
	 * token's type
	 */
	private TokenType type;
	/**
	 * token's value
	 */
	private String value; 
	/**
	 * Constructs {@link Token} with token type and its value as parameters.
	 * @param type {@link TokenType} of given token
	 * @param value {@link Object} of given token
	 */
	public Token(TokenType type, String value) {
		super();
		this.type = type;
		this.value = value;
	}
	/**
	 * Returns {@link TokenType} of given token.
	 * @return token type of token
	 */
	public TokenType getType() {
		return type;
	}
	/**
	 * Returns value of token
	 * @return token's value
	 */
	public String getValue() {
		return value;
	}
	
	
}
