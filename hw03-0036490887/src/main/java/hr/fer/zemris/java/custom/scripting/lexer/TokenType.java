package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * TokenType is type of token that will be returned by the Lexer.
 * 
 * @see {@link hr.fer.zemris.java.custom.scripting.lexer.Lexer Lexer}
 * @author dario
 * 
 */
public enum TokenType {
	TAG_SYMBOL_OPEN, // {$
	TAG_SYMBOL_CLOSE, // $}
	TEXT, // ElementString
	OPERATOR, // ElementOperator --done
	FUNCTION, // ElementFunction --done
	VARIABLE, // ElementVariable --done
	INTEGER, // ElementConstantInteger
	DOUBLE, // ElementConstantDouble
	EOF
}
