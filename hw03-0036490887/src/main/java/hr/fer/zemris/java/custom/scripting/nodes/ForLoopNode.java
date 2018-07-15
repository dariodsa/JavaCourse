package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;

/**
 * ForLoopNode is a node which represents a single for-loop construct. It
 * inherits from Node class.
 * 
 * @see {@link Node Node}
 * @author dario
 *
 */
public class ForLoopNode extends Node {

	private ElementVariable variable;
	private Element startExpression;
	private Element endExpression;
	private Element stepExpression; // which can be null

	/**
	 * Constructs the ForLoop with the given parameters, start expression, end
	 * expression and step exepreesion. <b>Only Step Expression can be null</b>. If
	 * others are null, exception will be thrown.
	 * 
	 * @param variable
	 *            - elementVariable of the ForLoopNode
	 * @param startExpression
	 *            startExpression of the ForLoopNode
	 * @param endExpression
	 *            end expression of the ForLoopNode
	 * @param stepExpression
	 *            step expression of the ForLoopNode
	 * @throws NullPointerException
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression,
			Element stepExpression) {
		super();
		if (variable == null)
			throw new NullPointerException("Variable can't be null");
		if (startExpression == null)
			throw new NullPointerException("StartExpression can't be null");
		if (endExpression == null)
			throw new NullPointerException("Endexpression can't be null");

		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}

	/**
	 * Returns the ElementVariable of the ForLoopNode.
	 * 
	 * @return {@link ElementVariable} variable
	 */
	public ElementVariable getVariable() {
		return variable;
	}

	/**
	 * Returns the start Expression of the {@link ForLoopNode}
	 * 
	 * @return {@link Element} startExpression
	 */
	public Element getStartExpression() {
		return startExpression;
	}

	/**
	 * Returns the end expression of the {@link ForLoopNode}
	 * 
	 * @return {@link Element} endExpression
	 */
	public Element getEndExpression() {
		return endExpression;
	}

	/**
	 * Returns the step expression of the {@link ForLoopNode}. BE CAREFULL it WILL
	 * return null.
	 * 
	 * @return {@link Element} stepExpression
	 */
	public Element getStepExpression() {
		return stepExpression;
	}

}
