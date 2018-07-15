package hr.fer.zemris.java.hw07.shell.lexer;
/**
 * Enumeration of which type can {@link LexerToken} be.  
 * @author dario
 *
 */
public enum LexerType {
	/**
	 * normal string
	 */
	STRING, 
	/**
	 * open bracket
	 */
	OPEN_BRACKET, 
	/**
	 * comma
	 */
	COMMA,
	/**
	 * close bracket
	 */
	CLOSE_BRACKET,
	/**
	 * EOF
	 */
	EOF
}
