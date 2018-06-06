package hr.fer.zemris.java.hw03.prob1;

import java.text.ParseException;
import java.util.Arrays;

import javax.xml.parsers.ParserConfigurationException;

/**
 * {@link Lexer} is a class which returns {@link Token} in the process of
 * parsing the data which will Lexer recieve in the constructor
 * {@link #Lexer(String)}.
 * 
 * @author dario
 *
 */
public class Lexer {
	/**
	 * data which will be parsed
	 */
	private char[] data;
	/**
	 * last generated token
	 */
	private Token token;
	/**
	 * pointer to the next character in {@code data} which wasn't processed
	 */
	private int currentIndex;

	private LexerState state;

	/**
	 * Construct the {@code Lexer} in which we will give text which will be parsed.
	 * 
	 * @param text
	 *            - text which we want to parse
	 * @throws NullPointerException
	 */
	public Lexer(String text) {
		if (text == null)
			throw new IllegalArgumentException("String can't be null.");
		data = text.toCharArray();

		currentIndex = 0;
		state = LexerState.BASIC;
	}

	/**
	 * Generates next {@codeToken} token starting from {@code currentIndex} which
	 * points to the char[] data. It looks in which {@link LexerState} is lexer.
	 * 
	 * @return next Token in parsing
	 * @throws LexerException
	 */
	public Token nextToken() {
		switch (state) {
		case BASIC:
			return nextTokenBasic();
		default:
			return nextTokenExtended();
		}
	}

	/**
	 * Returns the next token in the extended state of the lexer.
	 * 
	 * @return new tokne in th extended state of the lexer
	 */
	private Token nextTokenExtended() {

		if (token != null && token.getType() == TokenType.EOF)
			throw new LexerException("I can't generate next token.");
		skipBlanks();
		if (currentIndex >= data.length) {
			this.token = new Token(TokenType.EOF, null);
			return this.token;
		}
		if (data[currentIndex] != ' ' && data[currentIndex] != '#') // TEXT
		{
			String text = "";
			for (; currentIndex < data.length; ++currentIndex) {

				if (data[currentIndex] != ' ' && data[currentIndex] != '#') {
					text += data[currentIndex];
				} else
					break;
			}
			token = new Token(TokenType.WORD, text);
		} else { // SIMBOL
			token = new Token(TokenType.SYMBOL, Character.valueOf(data[currentIndex]));
			++currentIndex;
		}
		return this.token;
	}

	/**
	 * Returns the new token in the basic state of the lexer.
	 * 
	 * @return new token in the basic state of the lexer
	 */
	public Token nextTokenBasic() {
		if (token != null && token.getType() == TokenType.EOF)
			throw new LexerException("I can't generate next token.");
		skipBlanks();
		if (currentIndex >= data.length) {
			this.token = new Token(TokenType.EOF, null);
			return this.token;
		}
		if (Character.isLetter(data[currentIndex]) || data[currentIndex] == '\\') // TEXT
		{
			String text = "";
			for (; currentIndex < data.length; ++currentIndex) {

				if (Character.isLetter(data[currentIndex])) {
					text += data[currentIndex];
				} else if (data[currentIndex] == '\\') {
					int tempCurrentIndex = currentIndex;
					skipBlanks();
					if (tempCurrentIndex != currentIndex)
						break;
					else {
						currentIndex++;
						text += data[currentIndex];
					}
				} else
					break;
			}
			token = new Token(TokenType.WORD, text);
		} else if (isNumber(data[currentIndex])) { // BROJ
			String number = "";
			for (; currentIndex < data.length; ++currentIndex) {
				if (isNumber(data[currentIndex])) {
					number += data[currentIndex];
				} else
					break;
			}
			try {
				token = new Token(TokenType.NUMBER, Long.parseLong(number));
			} catch (NumberFormatException ex) {
				throw new LexerException("Number is not in the range for long value.");
			}
		} else { // SIMBOL
			token = new Token(TokenType.SYMBOL, Character.valueOf(data[currentIndex]));
			++currentIndex;
		}
		return this.token;
	}

	/**
	 * Sets the {@link Lexer} state by giving the {@link LexerState}.
	 * 
	 * @param state
	 *            - new state of the lexer
	 */
	public void setState(LexerState state) {
		if (state == null)
			throw new IllegalArgumentException("State can't be null");
		this.state = state;
	}

	/**
	 * Returns true if the {@code char} c is number
	 * 
	 * @param c
	 *            char
	 * @return true if the c is number, false otherwise
	 */
	private boolean isNumber(char c) {
		return '0' <= c && c <= '9';
	}

	/**
	 * Returns the last generated token. It doesn't produce new token.
	 * 
	 * @return Token - last generated Token
	 */
	public Token getToken() {
		return this.token;
	}

	/**
	 * Private method which skips blanks, including spaces, tabs, newlines and \r.
	 * It sets {@code currentIndex} at the place for next symbol which is not blank.
	 */
	private void skipBlanks() {
		switch (state) {
		case BASIC:
			skipBlanksBasic();
			break;
		default:
			skipBlanksExtended();
			break;
		}
	}

	/**
	 * Skips blanks in the extended state of the lexer.
	 */
	private void skipBlanksExtended() {
		while (currentIndex < data.length) {

			if (data[currentIndex] == ' ' || data[currentIndex] == '\r' || data[currentIndex] == '\t'
					|| data[currentIndex] == '\n') {
				currentIndex++;
			} else
				break;
		}
	}

	/**
	 * Skips blanks in the basic state of the Lexer.
	 */
	private void skipBlanksBasic() {
		while (currentIndex < data.length) {

			if (currentIndex + 1 < data.length) {
				if (data[currentIndex] == '\\' && data[currentIndex + 1] == '\\') {
					break;
				} else if (data[currentIndex] == '\\' && isNumber(data[currentIndex + 1]))
					break;
				else if (data[currentIndex] == '\\') {
					throw new LexerException("Illegal expression! I can't parse it.");
				}
			}

			if ((data[currentIndex] == '\r') || (data[currentIndex] == '\t') || (data[currentIndex] == '\n')) {
				currentIndex++;

			} else if (data[currentIndex] == ' ')
				++currentIndex;
			else if (data[currentIndex] == '\\')
				throw new LexerException("Illegal expression! I can't parse it.");
			else
				break;
		}
	}
}
