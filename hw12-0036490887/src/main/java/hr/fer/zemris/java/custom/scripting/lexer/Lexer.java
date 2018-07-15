package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Class {@code Lexer} allows user to give char array to the class. Lexer will
 * produce tokens which will {@code TokenType} type. New token will be generated
 * by calling method nextToken(). You can also get previously generated token by
 * calling method getToken(). You can set lexer state by calling setLexerState()
 * and giving lexerState as parameter.
 * 
 * @version 1.0
 * @author dario
 * @since 1.0
 * @see {@link hr.fer.zemris.java.custom.scripting.lexer.TokenType TokenType}
 * @see {@link hr.fer.zemris.java.custom.scripting.lexer.LexerState LexerState}
 */
public class Lexer {

	/**
	 * data
	 */
    private char[] data;
    /**
     * token
     */
	private Token token;
	/**
	 * current index
	 */
	private int currentIndex;

	/***
	 * state
	 */
	private LexerState state;

	/**
	 * Constructs Lexer which will generate token from char[] data. If the char[]
	 * data is null it will throw exception. Current lexer state is
	 * {@code OUTSIDE_TAG}, and the initial position is at the beginning of the
	 * string.
	 * 
	 * @param data data
	 */
	public Lexer(char[] data) {
		if (data == null)
			throw new IllegalArgumentException("Data can't be null");
		this.data = data;
		this.state = LexerState.OUTSIDE_TAG;
		this.currentIndex = 0;
	}

	/**
	 * Sets the lexer state.
	 * 
	 * @param state
	 *            new lexer state
	 * @throws IllegalArgumentException
	 *             if the state is null
	 */
	public void setLexerState(LexerState state) {
		if (state == null)
			throw new IllegalArgumentException("State can't be null.");
		this.state = state;
	}

	/**
	 * Returns next token which will be generated in the process of parsing the
	 * tokens. Token will be type of {@link TokenType}.
	 * 
	 * @return new generated token {@link TokenType}
	 */
	public Token nextToken() {
		if (state == LexerState.OUTSIDE_TAG) {
			if (isOpenTag(currentIndex)) {
				this.token = new Token(TokenType.TAG_SYMBOL_OPEN, "{$");
				currentIndex += 2;
			} else if (currentIndex == data.length) {
				this.token = new Token(TokenType.EOF, null);
			} else {
				// ovdje ide text
				StringBuilder builder = new StringBuilder();
				for (; currentIndex < data.length; ++currentIndex) {
					if (isOpenTag(currentIndex))
						break;
					else if (currentIndex + 1 < data.length && data[currentIndex] == '\\'
							&& data[currentIndex + 1] == '\\') {
						builder.append(data[currentIndex]);
						++currentIndex;
					} else if (currentIndex + 1 < data.length && data[currentIndex] == '\\'
							&& data[currentIndex + 1] == '{') {
						builder.append(data[currentIndex + 1]);
						++currentIndex;
					} else if (data[currentIndex] == '\\') {
						throw new LexerException(
								"You use \\ and behind it there wasn't anything that I could inteprete.");
					} else
						builder.append(data[currentIndex]);
				}
				this.token = new Token(TokenType.TEXT, builder.toString());
			}
		} else if (state == LexerState.INSIDE_TAG) {
			// System.out.println(currentIndex);
			skipBlanks();
			// System.out.println(currentIndex+" nakon ");
			if (data[currentIndex] == '"') {
				boolean done = false;
				StringBuilder builder = new StringBuilder();
				builder.append('"');
				++currentIndex;
				for (; currentIndex < data.length; ++currentIndex) {
					if (currentIndex + 1 < data.length && data[currentIndex] == '\\' && data[currentIndex + 1] == 34) {
						builder.append('"');
						++currentIndex;
					} else if (data[currentIndex] == 34) {
						done = true;
						builder.append('"');
						++currentIndex;
						break;
					} else if (currentIndex + 1 < data.length && data[currentIndex] == '\\'
							&& data[currentIndex + 1] == '\\') {
						builder.append('\\');
						++currentIndex;
					} else if (currentIndex + 1 < data.length && data[currentIndex] == '\\'
							&& data[currentIndex + 1] == 'n') {
						builder.append('\n');
						++currentIndex;
					} else if (currentIndex + 1 < data.length && data[currentIndex] == '\\'
							&& data[currentIndex + 1] == 't') {
						builder.append('\t');
						++currentIndex;
					} else if (currentIndex + 1 < data.length && data[currentIndex] == '\\'
							&& data[currentIndex + 1] == 'r') {
						builder.append('\r');
						++currentIndex;
					} else if (data[currentIndex] == '\\') {
						throw new LexerException(
								"Inside tag you used \\ and it is on allowed to use it in this format.");
					} else
						builder.append(data[currentIndex]);
				}
				if (done) {
					this.token = new Token(TokenType.TEXT, builder.toString());
				} else {
					throw new LexerException("String doesn't end with \"");
				}
			} else if (data[currentIndex] == '@' && currentIndex + 1 < data.length) {
				// TODO function

				if (isLetter(data[currentIndex + 1]) == false)
					throw new LexerException("Wrong function name.");
				StringBuilder builder = new StringBuilder();
				builder.append(data[currentIndex]);
				++currentIndex;
				for (; currentIndex < data.length; ++currentIndex) {
					if (isNumber(data[currentIndex]) || isLetter(data[currentIndex]) || data[currentIndex] == '_') {
						builder.append(data[currentIndex]);
					} else if (data[currentIndex] == '$' && currentIndex + 1 < data.length
							&& data[currentIndex] == '}') {
						break;
					} else if (isBlank(data[currentIndex]))
						break;
					else if (isCloseTag(currentIndex))
						break;
					else
						throw new LexerException("Wrong name structure of the variable.");
				}
				this.token = new Token(TokenType.FUNCTION, builder.toString());
			} else if (isNumber(data[currentIndex]) || (data[currentIndex] == '-' && currentIndex + 1 < data.length
					&& isNumber(data[currentIndex + 1]))) {
				StringBuilder builder = new StringBuilder();
				boolean dot = false;
				builder.append(data[currentIndex++]);
				for (; currentIndex < data.length; ++currentIndex) {
					if (isNumber(data[currentIndex])) {
						builder.append(data[currentIndex]);
					} else if (data[currentIndex] == '.') {
						builder.append('.');
						dot = true;
					} else if (isBlank(data[currentIndex]))
						break;
					else if (isCloseTag(currentIndex))
						break;
					else {
						throw new LexerException(String.format("I don't know how to parse this char in number(%c).",
								data[currentIndex]));
					}
				}
				if (dot) {
					try {
						Double data = Double.parseDouble(builder.toString());
						this.token = new Token(TokenType.DOUBLE, data);
					} catch (NumberFormatException ex) {
						throw new LexerException(String.format("I can't parse this double(%s).%n", builder.toString()));
					}
				} else {
					try {
						Integer data = Integer.parseInt(builder.toString());
						this.token = new Token(TokenType.INTEGER, data);
					} catch (NumberFormatException ex) {
						throw new LexerException(
								String.format("I can't parse this integer(%s).%n", builder.toString()));
					}
				}
			} else if (isLetter(data[currentIndex])) {

				StringBuilder builder = new StringBuilder();
				builder.append(data[currentIndex]);
				++currentIndex;
				for (; currentIndex < data.length; ++currentIndex) {
					if (isNumber(data[currentIndex]) || isLetter(data[currentIndex]) || data[currentIndex] == '_') {
						builder.append(data[currentIndex]);
					} else if (data[currentIndex] == '$' && currentIndex + 1 < data.length
							&& data[currentIndex + 1] == '}') {
						break;
					} else if (isBlank(data[currentIndex]))
						break;
					else
						throw new LexerException("Wrong name structure of the variable.");
				}
				this.token = new Token(TokenType.VARIABLE, builder.toString());
			} else if (isCloseTag(currentIndex)) {
				this.token = new Token(TokenType.TAG_SYMBOL_CLOSE, "$}");
				currentIndex += 2;
			} else if (data[currentIndex] == '=') {
				this.token = new Token(TokenType.VARIABLE, "=");
				++currentIndex;
			} else if (isOperator(currentIndex)) {
				this.token = new Token(TokenType.OPERATOR, data[currentIndex] + "");
				++currentIndex;
			} else
				throw new LexerException("I don't know what are you ? " + data[currentIndex]);
		}
		//System.out.println(state+ " " +token.getType() + " " + token.getValue());
		return this.token;
	}

	/**
	 * Returns {@code Token} which was generated previously.
	 * 
	 * @return {@code Token} last generated Token
	 * @throws NullPointerException
	 *             if the token is null
	 */
	public Token getToken() {
		if (token == null)
			throw new NullPointerException("Token is null");
		return this.token;
	}

	/**
	 * Sets private attribute {@code currentIndex} at the new position by moving it
	 * on right, adding up, in order to escape spaces, tabs and newlines. After that
	 * currentIndex will point to the something that isn't newline, space or tab.
	 */
	private void skipBlanks() {
		for (; currentIndex < data.length;) {
			if (data[currentIndex] == ' ')
				++currentIndex;
			else if (data[currentIndex] == 10)
				++currentIndex;
			else if (data[currentIndex] == 13)
				++currentIndex;
			else if (data[currentIndex] == 9)
				++currentIndex;
			else
				break;
		}
	}

	/**
	 * Returns true if the char at the {@code position} is in the set {+,-,*,/,^}.
	 * 
	 * @param position
	 *            position on which we are checking char
	 * @return true if the char at the given position is in the above set
	 */
	private boolean isOperator(int position) {
		if (position >= data.length)
			return false;
		return data[position] == '+' || data[position] == '-' || data[position] == '*' || data[position] == '/'
				|| data[position] == '^';
	}

	/**
	 * Returns true if the given char is in a number digit, from 0 to 9.
	 * 
	 * @param c
	 *            - char that we are checking
	 * @return true if the char is representing a number digit, false otherwise
	 */
	private boolean isNumber(char c) {
		return '0' <= c && c <= '9';
	}

	/**
	 * Returns true if the given char is space or tab or newline or \r.
	 * 
	 * @param c
	 *            char which we are checking
	 * @return true if the char is in the above set, false otherwise
	 */
	private boolean isBlank(char c) {
		return c == ' ' || c == '\t' || c == '\r' || c == '\n';
	}

	/**
	 * Checks if the c is upper-case or lower-case letter of english alphabet.
	 * 
	 * @param c
	 *            - char which we are checking
	 * @return true if the char is a letter, false otherwise
	 */
	private boolean isLetter(char c) {
		return ('a' <= c && c <= 'z') || ('A' <= c && c <= 'Z');

	}

	/**
	 * Returns true if the substring from the given position is open tag <b>{$</b>.
	 * 
	 * @param position
	 *            in the char array from which we are trying to find open tag
	 * @return true if the open tag is on the that position, false otherwise
	 */
	private boolean isOpenTag(int position) {
		if (position >= data.length)
			return false;
		if (position == 0 && data[position] == '{' && data[position + 1] == '$') {
			return true;
		}
		if (position > 0 && data[position] == '{' && data[position + 1] == '$' && data[position - 1] != '\\') {
			return true;
		}
		return false;
	}

	/**
	 * Returns true if the substring from the given position is closed tag <b>$}</b>
	 * 
	 * @param position
	 *            position in the char array from which we are trying to find closed
	 *            tag
	 * @return true if the close tag is on the that position, false otherwise
	 */
	private boolean isCloseTag(int position) {
		if (position >= data.length)
			return false;
		if (position == 0 && data[position] == '$' && data[position + 1] == '}') {
			return true;
		}
		if (position > 0 && data[position] == '$' && data[position + 1] == '}' && data[position - 1] != '\\') {
			return true;
		}
		return false;
	}
}
