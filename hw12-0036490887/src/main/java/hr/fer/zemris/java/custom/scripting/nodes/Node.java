package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
/**
 * Class Node represents the node which will be used in parsing the data. 
 * It will be part of the parsing tree that the SmartScriptParser will create
 * @see {@link hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser SmartScriptParser}
 * @author dario
 *
 */
public abstract class Node {

	/**
	 * list of elements
	 */
    private ArrayIndexedCollection list;
	/**
	 * Constructs the new Node and init it's list of children. 
	 */
	public Node() {
		list = new ArrayIndexedCollection();
	}
	/**
	 * Adds the children to the node.
	 * @param child - new children of the node
	 * @throws NullPointerException can't be null
	 */
	public void addChildNode(Node child) {
		if(child == null) throw new NullPointerException("Child can't be null");
		if(list == null)this.list = new ArrayIndexedCollection();
		
		list.add(child);
	} 
	/**
	 * Gets the number of the node's children.
	 * @return number of the children
	 */
	public int numberOfChildren() {
		if(list == null) return 0;
		return this.list.size();
	}
	/**
	 * Gets the children at the given index.
	 * @param index - position of which children we want
	 * @return children at the index position
	 * @throws IllegalArgumentException There is no children at the given position
	 */
	public Node getChild(int index) {
		if(index < 0 || index >= numberOfChildren()) {
			throw new IllegalArgumentException(String.format("There is no children at the %d position.%n",index));
		}
		return (Node)this.list.get(index);
	}
	/**
	 * Accept method specific for visitor design pattern.
	 * @param visitor visitor
	 */
	public abstract void accept(INodeVisitor visitor);
}
