package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;

/**
 * EchoNode is a node which represents a command which generates some textual
 * output dynamically. It inherits from class Node.
 * 
 * @see {@link Node Node}
 * @author dario
 *
 */
public class EchoNode extends Node {

	/**
	 * elements
	 */
    private Element[] elements;

	/**
	 * Constructs the {@link EchoNode} with the {@link Element} array as the
	 * parameter.
	 * 
	 * @param elements
	 *            parameter of the EchoNode
	 */
	public EchoNode(Element[] elements) {
		super();
		this.elements = elements;
	}

	/**
	 * Gets the all elements of the EchoNode.
	 * 
	 * @return elements of the EchoNode
	 */
	public Element[] getElements() {
		return this.elements;
	}
	
	public void accept(INodeVisitor visitor) {
        visitor.visitEchoNode(this);
    }
}
