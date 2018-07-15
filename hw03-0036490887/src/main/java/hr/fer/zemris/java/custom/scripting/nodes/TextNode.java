package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * TextNode is a node which represents a piece of textual data. It inherits Node
 * class.
 * 
 * @see {@link Node Node}
 * @author dario
 *
 */
public class TextNode extends Node {

	private String text;

	/**
	 * Constructs the TextNode with a String text as a parameter.
	 * 
	 * @param text
	 */
	public TextNode(String text) {
		this.text = text;
	}

	/**
	 * Returns the text of the TextNode.
	 * 
	 * @return String text of the TextNode
	 */
	public String getText() {
		return this.text;
	}

}
