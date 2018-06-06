package hr.fer.zemris.java.custom.scripting.lexer;
/**
 * LexerStates are avaliable states of the lexer. Each state 
 * represents the different way of parsing. There are 
 * INSIDE_TAG and OUTSIDE_TAG.
 * 
 * @author dario
 * @see {@link Lexer Lexer}
 */
public enum LexerState {
	INSIDE_TAG, OUTSIDE_TAG
}
