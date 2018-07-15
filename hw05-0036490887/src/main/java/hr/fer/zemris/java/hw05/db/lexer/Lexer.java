package hr.fer.zemris.java.hw05.db.lexer;
/**
 * Class {@link Lexer} offers you to split given text into token which 
 * will be used in parsing process. Method {@link #nextToken()} generates next {@link Token} ,
 * with the {@link TokenType}  as type, while as {@link #getToken()} returns the same previously 
 * generated token.
 * 
 * @see Token
 * @see TokenType
 * @version 1.0
 * @since 1.0
 * @author dario
 *
 */
public class Lexer {

	/**
	 * currently generated token
	 */
	private Token token;
	/**
	 * text which will be splited to tokens
	 */
	private String text;
	/**
	 * current position in the text
	 */
	private int current;
	/**
	 * and string, as const string
	 */
	private static final String AND = "and";
	/**
	 * Init text which will be set into {@link Token}.
	 * @param text text which will be parsed
	 */
	public Lexer(String text) {
		this.text = text;
		this.current = 0;
	}
	/**
	 * Generates next  {@link Token} and returns it.
	 * @return newly generated {@link Token}
	 */
	public Token nextToken() {

		skipBlanks();
		if(current == text.length()) {
			this.token = new Token(TokenType.EOF, null);
		} else if (isAnd()) {
			this.token = new Token(TokenType.AND, null);
			current += AND.length();
		} else if (text.charAt(current) == '\"') {

			++current;
			boolean found = false;
			String value = "";
			for (; current < text.length(); ++current) {
				if (text.charAt(current) == '\"') {
					++current;
					found = true;
					this.token = new Token(TokenType.TEXT, value);
					break;
				} else {
					value += text.charAt(current);
				}
			}
			if(!found) throw new LexerException("You didn't close string.");
		} else if (isOperator()) {
			String operator = getOperator();
			current += operator.length();
			this.token = new Token(TokenType.OPERATOR, operator);
		} else {
			StringBuilder value = new StringBuilder();
			for(;current < text.length(); ++current) {
				if(isBlank(text.charAt(current)) || isOperator()) {
					break;
				}
				value.append(text.charAt(current));
			}
			this.token = new Token(TokenType.VARIABLE, value.toString());
		}
		return this.token;
	}
	/**
	 * Returns true if the next string is operator, false otherwise
	 * @return true if the next string is operator, false otherwise
	 */
	private boolean isOperator() {

		if (getChar(current) == '<' && getChar(current + 1) == '=')
			return true;
		if (getChar(current) == '<' && getChar(current + 1) != '=')
			return true;
		if (getChar(current) == '>' && getChar(current + 1) == '=')
			return true;
		if (getChar(current) == '>' && getChar(current + 1) != '=')
			return true;
		if (getChar(current) == '=')
			return true;
		if (getChar(current) == '!' && getChar(current + 1) == '=')
			return true;
		if (getChar(current) == 'L' && getChar(current + 1) == 'I' && getChar(current + 2) == 'K'
				&& getChar(current + 3) == 'E')
			return true;
		return false;
	}
	/**
	 * Returns the String equal operator if the string is found, otherwise throws {@link LexerException}.
	 * @return {@link String} represenentation of the found operator
	 */
	private String getOperator() {
		if (getChar(current) == '<' && getChar(current + 1) == '=')
			return "<=";
		if (getChar(current) == '<' && getChar(current + 1) != '=')
			return "<";
		if (getChar(current) == '>' && getChar(current + 1) == '=')
			return ">=";
		if (getChar(current) == '>' && getChar(current + 1) != '=')
			return ">";
		if (getChar(current) == '=')
			return "=";
		if (getChar(current) == '!' && getChar(current + 1) == '=')
			return "!=";
		if (getChar(current) == 'L' && getChar(current + 1) == 'I' && getChar(current + 2) == 'K'
				&& getChar(current + 3) == 'E')
			return "LIKE";
		throw new LexerException("I didn't find operator.");
	}
	/**
	 * Returns generated token, which was generated previosly.
	 * @return previosly generated token
	 */
	public Token getToken() {
		return this.token;
	}
	/**
	 * Skips all blanks in text. Blanks are spaces and tabs.
	 */
	private void skipBlanks() {
		while (current < text.length() && isBlank(text.charAt(current))) {
			++current;
		}
	}
	/**
	 * Returns true if the next string is "AND", no matter cases.
	 * @return true if the next string is and, otherwise false
	 */
	private boolean isAnd() {
		if (current + 3 > text.length())
			return false;
		String res =""+ text.charAt(current) + (char)text.charAt(current + 1) + (char)text.charAt(current + 2) + "";
	
		return res.toLowerCase().compareTo(AND) == 0;
	}
	/**
	 * Returns true if the give character is blank character ( space or tab).
	 * @param ch which we will interspect
	 * @return true if character is blank char
	 */
	private boolean isBlank(char ch) {
		return ch == ' ' || ch == '\t';
	}
	/**
	 * Returns char at the given position. If the position is outside of the string it will return null character.
	 * @param pos position in the text
	 * @return char at the given position
	 */
	private char getChar(int pos) {
		if (pos >= text.length() || pos < 0) {
			return 0;
		}
		return text.charAt(pos);
	}
}
