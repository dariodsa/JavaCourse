package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.lexer.Lexer;
import hr.fer.zemris.java.custom.scripting.lexer.LexerException;
import hr.fer.zemris.java.custom.scripting.lexer.LexerState;
import hr.fer.zemris.java.custom.scripting.lexer.Token;
import hr.fer.zemris.java.custom.scripting.lexer.TokenType;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;

/**
 * {@link SmartScriptParser} accepts {@link String} data in the constructor.
 * 
 * @author dario
 *
 */
public class SmartScriptParser {

	private char[] data;
	private Lexer lexer;

	private DocumentNode documentNode;
	private ObjectStack stack;

	/**
	 * Constructs the parser, which will parse given data. Parsing is call right
	 * after the construction.
	 * 
	 * @param data
	 *            - data which we will parse
	 */
	public SmartScriptParser(String data) {
		if (data == null)
			throw new IllegalArgumentException("String data can't be null");
		this.data = data.toCharArray();
		this.lexer = new Lexer(this.data);
		this.documentNode = new DocumentNode();
		this.stack = new ObjectStack();
		// System.out.println(data);
		parseMe();
	}

	/**
	 * Private method which will parse given data. It will construct tree, with the
	 * document node as the root. That root you can access by calling the method
	 * {@link #getDocumentNode() getDocumentNode()}.
	 * 
	 * @see {@link hr.fer.zemris.java.custom.scripting.nodes.Node Node}
	 */
	private void parseMe() {

		try {
			stack.push(documentNode);
			Token token = lexer.nextToken();

			while (token.getType() != TokenType.EOF) {

				Node parent = (Node) stack.peek();
				if (token.getType() == TokenType.TAG_SYMBOL_OPEN) {
					lexer.setLexerState(LexerState.INSIDE_TAG);

					token = lexer.nextToken();
					if (token.getType() != TokenType.VARIABLE) {
						throw new SmartScriptParserException(
								String.format("Your tag name is invalid.%nTag name: %s", token.getValue().toString()));
					} else if (token.getValue().toString().toLowerCase().compareTo("for") == 0) {

						ForLoopNode forNode = parseFor();
						parent.addChildNode(forNode);
						stack.push(forNode);
					} else if (token.getValue().toString().compareTo("=") == 0) {
						Node echoNode = parseEcho();
						parent.addChildNode(echoNode);
					} else if (token.getValue().toString().toLowerCase().compareTo("end") == 0) {

						if (!(stack.peek() instanceof ForLoopNode)) {
							throw new SmartScriptParserException(
									"You have to have for node to be able to close for tag.");
						}
						
						stack.pop();
						token = lexer.nextToken();
						if (token.getType() == TokenType.TAG_SYMBOL_CLOSE) {
						} else {
							throw new SmartScriptParserException("After END has to come end tag.");
						}
						lexer.setLexerState(LexerState.OUTSIDE_TAG);

					} else {
						throw new SmartScriptParserException(String
								.format("Unknown tag name.You used this tag name: %s", token.getValue().toString()));
					}
				} else if (token.getType() == TokenType.TEXT) {
					TextNode node = new TextNode((String) token.getValue());
					parent.addChildNode(node);
				} else {
					throw new SmartScriptParserException(
							String.format("TokenType: %s should not appear in this part", token.getValue().toString()));
				}

				token = lexer.nextToken();
			}
			if (stack.isEmpty() == true) {
				throw new SmartScriptParserException("You should close some tag, probably for.");
			}
			if (token.getType() == TokenType.EOF) {
				stack.pop();
				if (!stack.isEmpty()) {
					throw new SmartScriptParserException("Stack is not empty at the end.");
				}
			} else {
				throw new SmartScriptParserException(String.format("Unknown token type(%s).", token.getType()));
			}

			System.out.println("Parsing is done.");
			return;

		} catch (LexerException e) {
			throw new SmartScriptParserException(e);
		} catch (SmartScriptParserException ex) {
			throw new SmartScriptParserException(ex);
		} catch (Exception e) {
			throw new SmartScriptParserException(e);
		}
	}

	/**
	 * Gets document node, which is a root of the tree structure, which will be
	 * created by the parser.
	 * 
	 * @return DocumentNode
	 */
	public DocumentNode getDocumentNode() {

		return this.documentNode;
	}

	/**
	 * Parse tag which name is =. It will construct EchoNode and all tokens which
	 * will generate {@link Lexer} will be transfor to {@link Element} array and it
	 * will be set to the EchoNode as the atribute.
	 * 
	 * @return EchoNode new leaf in the parsing tree
	 */
	private EchoNode parseEcho() {
		Token token = lexer.getToken();
		lexer.setLexerState(LexerState.INSIDE_TAG);
		ArrayIndexedCollection collection = new ArrayIndexedCollection();

		while (token.getType() != TokenType.TAG_SYMBOL_CLOSE) {

			token = lexer.nextToken();
			Element element = null;
			switch (token.getType()) {
			case DOUBLE:
				element = new ElementConstantDouble((double) token.getValue());
				break;
			case INTEGER:
				element = new ElementConstantInteger((int) token.getValue());
				break;
			case FUNCTION:
				element = new ElementFunction((String) token.getValue());
				break;
			case VARIABLE:
				element = new ElementVariable((String) token.getValue());
				break;
			case TEXT:
				element = new ElementString(token.getValue().toString());
				break;
			case OPERATOR:
				element = new ElementFunction(token.getValue().toString());
				break;
			default:

				break;
			}
			if (element != null)
				collection.add(element);
		}
		// ako dode tag close gotovi smo
		lexer.setLexerState(LexerState.OUTSIDE_TAG);
		Element[] elements = new Element[collection.size()];
		for (int i = 0; i < elements.length; ++i) {
			elements[i] = (Element) collection.get(i);
		}

		EchoNode echoNode = new EchoNode(elements);

		return echoNode;
	}

	/**
	 * Constructs the {@link ForLoopNode} and it returns it. It looks the tokens
	 * from the {@link Lexer} and if they are not by the rule it will produce an
	 * exception.
	 * 
	 * @return ForLoopNode new leaf in the parsing tree
	 * @throws SmartScriptParserException
	 */
	private ForLoopNode parseFor() {
		// parsiraj sve do END
		Token token = lexer.getToken();
		boolean insideFor = true;

		ElementVariable variable;
		Element[] others = new Element[3];

		token = lexer.nextToken();
		if (token.getType() == TokenType.VARIABLE)
			variable = new ElementVariable((String) token.getValue());
		else
			throw new SmartScriptParserException("First parameter in for loop must be a variable.");

		int pos = 0;
		while (true) {
			token = lexer.nextToken();
			if (pos == 3 && token.getType() == TokenType.TAG_SYMBOL_CLOSE) {
				lexer.setLexerState(LexerState.OUTSIDE_TAG);
				break;
			} else if (pos == 3 && token.getType() != TokenType.TAG_SYMBOL_CLOSE) {
				throw new LexerException("There were too many arguments in the for loop.");
			} else if (pos > 3) {
				// System.out.println(pos + " " +token.getType());
				throw new SmartScriptParserException(String.format("You used too many arguments."));
			} else if (token.getType() == TokenType.VARIABLE) {
				others[pos] = new ElementVariable((String) token.getValue());
			} else if (token.getType() == TokenType.INTEGER) {
				others[pos] = new ElementConstantInteger((int) token.getValue());
			} else if (token.getType() == TokenType.DOUBLE) {
				others[pos] = new ElementConstantDouble((double) token.getValue());
			} else if (token.getType() == TokenType.TEXT) {
				others[pos] = new ElementString((String) token.getValue());
			} else if (pos == 2 && token.getType() == TokenType.TAG_SYMBOL_CLOSE) {
				lexer.setLexerState(LexerState.OUTSIDE_TAG);
				break;
			} else {
				throw new SmartScriptParserException(
						String.format("You used TokenType %s," + "at it is not allowed at that place in For init.",
								token.getType().toString()));
			}
			++pos;
		}
		ForLoopNode forNode = new ForLoopNode(variable, others[0], others[1], others[2]);

		return forNode;
	}
}
