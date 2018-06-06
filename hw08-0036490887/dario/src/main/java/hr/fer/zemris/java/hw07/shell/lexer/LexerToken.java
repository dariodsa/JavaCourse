package hr.fer.zemris.java.hw07.shell.lexer;

/**
 * {@link LexerToken} is class which encapsulate token which is created
 * in lexer process. 
 * @author dario
 *
 */
public class LexerToken {
	/**
	 * type of lexer token
	 */
	private LexerType type;
	/**
	 * value of lexer
	 */
	private String value;
	
	/**
	 * Constructs {@link LexerToken} with specific type and it's value.
	 * @param type type of token
	 * @param value value of token
	 */
	public LexerToken(LexerType type, String value) {
		this.type = type;
		this.value = value;
	}
	/**
	 * Returns type of the lexer token.
	 * @return {@link LexerType} type
	 */
	public LexerType getType() {
		return type;
	}
	/**
	 * Returns token's value.
	 * @return {@link String} value of the token
	 */
	public String getValue() {
		return this.value;
	}
}
