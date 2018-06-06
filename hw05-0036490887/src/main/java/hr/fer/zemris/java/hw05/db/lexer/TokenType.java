package hr.fer.zemris.java.hw05.db.lexer;
/**
 * {@link TokenType} enumeration which is used in {@link Lexer} which {@link Lexer#nextToken() returns generated token} 
 * and those token have type of {@link TokenType}.
 *  
 * @author dario
 * @see Lexer
 * @see Token
 */
public enum TokenType {
	/**
	 * token type of variable
	 */
	VARIABLE, 
	/**
	 * token type of operator
	 */
	OPERATOR, 
	/**
	 * token type of text
	 */
	TEXT, 
	/**
	 * token type of keyword AND
	 */
	AND, 
	/**
	 * token type generated as the last token in the  text
	 */
	EOF
}
