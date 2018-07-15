package hr.fer.zemris.java.hw03.prob1;
/**
 * TokenType are types of the {@link Token}. It is a enumeration and it 
 * consists of the EOF as the end of file, WORD as the text, NUMBER as the number 
 * and the SYMBOL as the operator like +, -, * etc.
 * @author dario
 * @see {@link Token Token}
 *
 */
public enum TokenType {
	EOF, WORD, NUMBER, SYMBOL
}
