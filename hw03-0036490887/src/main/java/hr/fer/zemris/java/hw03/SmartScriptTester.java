package hr.fer.zemris.java.hw03;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.print.DocFlavor.STRING;

import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

/**
 * Program accepts one arguments as a file path. That file will be sent to
 * parser, from which he will generated parsing tree, with {@link DocumentNode}
 * as the root. After that you can call
 * {@link #createOriginalDocumentBody(DocumentNode)
 * CreateOriginalDocumentBody()} to get text that you enetered at the begining.
 * Be carefull that text will not be the same, because some blanks or chars will
 * be deleted in the parsing process.
 * 
 * @author dario
 *
 */
public class SmartScriptTester {

	/**
	 * Prints the interpreted String from parser at the Standard Output. Data will
	 * get from file path which will be given as the arguments. You should provide
	 * only one argument.
	 * 
	 * @param args
	 *            - String data path to the file
	 */
	public static void main(String[] args) {

		if (args.length != 1) {
			System.out.println("You should provide only one argument.");
			System.exit(1);
		}
		if (args[0] == null) {
			System.out.println("You provided null.");
			System.exit(1);
		}

		Path filePath = Paths.get(args[0]);

		String docBody = "";
		try {
			docBody = new String(Files.readAllBytes(filePath), StandardCharsets.UTF_8);
		} catch (IOException e1) {
			e1.printStackTrace();
			System.exit(-1);
		}
		SmartScriptParser parser = null;
		try {
			parser = new SmartScriptParser(docBody);
		} catch (SmartScriptParserException e) {
			//System.out.println(e.getMessage());
			System.out.println("Unable to parse document!");
			System.exit(-1);
		} catch (Exception e) {
			System.out.println("If this line ever executes, you have failed this class!");
			System.exit(-1);
		}
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = createOriginalDocumentBody(document);
		System.out.println(originalDocumentBody);
		/*
		 * String copy = createOriginalDocumentBody((new
		 * SmartScriptParser(originalDocumentBody).getDocumentNode()));
		 * System.out.println(copy); // should write something like original
		 * System.out.println(copy.compareTo(originalDocumentBody)==0?"SAME":"NOT SAME"
		 * );
		 */
		// content of docBody

	}

	/**
	 * Generates text document from the tree structure that parser give us. The root
	 * of that tree is always {@code DocumentNode}.
	 * 
	 * @param document
	 *            - root of the tree
	 * @return {@code String} representation of the parser-tree structure
	 */
	public static String createOriginalDocumentBody(DocumentNode document) {
		StringBuilder result = new StringBuilder();

		ObjectStack stack = new ObjectStack();

		stack.push(document);

		while (!stack.isEmpty()) {

			if (stack.peek() instanceof Integer) {
				result.append("{$END$}");
				stack.pop();
				continue;
			}
			Node node = (Node) stack.peek();
			stack.pop();
			
			if (node instanceof ForLoopNode) {
				result.append("{$ FOR ");
				result.append(((ForLoopNode) node).getVariable().asText() + " ");
				result.append(((ForLoopNode) node).getStartExpression().asText() + " ");
				result.append(((ForLoopNode) node).getEndExpression().asText() + " ");
				if (((ForLoopNode) node).getStepExpression() != null)
					result.append(((ForLoopNode) node).getStepExpression().asText() + " ");
				result.append(" $}");
			} else if (node instanceof TextNode) {
				result.append(remove(((TextNode) node).getText()));
			} else if (node instanceof EchoNode) {
				result.append("{$= ");
				for (Element E : ((EchoNode) node).getElements()) {
					result.append(E.asText() + " ");
				}
				result.append("$}");
			}

			for (int i = node.numberOfChildren() - 1; i >= 0; --i) {

				
				if (node.getChild(i) instanceof ForLoopNode) {
					stack.push(Integer.valueOf(0));
				}
				stack.push(node.getChild(i));
			}

		}
		return result.toString();
	}

	/**
	 * Removes special chars and transform it to escaped chars. It will be used in
	 * reconstruction of the document.
	 * 
	 * @param value
	 *            {@link String} value which will be transformed
	 * @return {@link String} new transformed string
	 */
	private static String remove(String value) {

		StringBuilder builder = new StringBuilder();
		int len = value.length();
		for (int i = 0; i < len; ++i) {
			if (value.charAt(i) == '\"' && i != 0 && i != len - 1) {
				builder.append('\\');
				builder.append('\"');
			} else if (value.charAt(i) == '\\') {
				builder.append('\\');
				builder.append('\\');
			} else if (value.charAt(i) == '{') {
				builder.append('\\');
				builder.append('{');
			} else
				builder.append(value.charAt(i));
		}
		return builder.toString();

	}

}
