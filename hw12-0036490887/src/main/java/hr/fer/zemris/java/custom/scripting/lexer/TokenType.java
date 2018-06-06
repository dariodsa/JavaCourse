package hr.fer.zemris.java.custom.scripting.lexer;

import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;

/**
 * TokenType is type of token that will be returned by the Lexer.
 * 
 * @see {@link hr.fer.zemris.java.custom.scripting.lexer.Lexer Lexer}
 * @author dario
 * 
 */
public enum TokenType {
	/**
	 * tag symbol open
	 */
    TAG_SYMBOL_OPEN, 
    /**
     * tag symbol close
     */
	TAG_SYMBOL_CLOSE, 
	/**
	 * text
	 */
	TEXT, 
	/**
	 * {@link ElementOperator}
	 */
	OPERATOR, 
	/**
	 * {@link ElementFunction}
	 */
	FUNCTION, 
	/**
	 * {@link ElementVariable}
	 */
	VARIABLE,
	/**
	 * {@link ElementConstantInteger}
	 */
	INTEGER,
	/**
	 * {@link ElementConstantDouble}
	 */
	DOUBLE, 
	/**
	 * eof
	 */
	EOF
}
