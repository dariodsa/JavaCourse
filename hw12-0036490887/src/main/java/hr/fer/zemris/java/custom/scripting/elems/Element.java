package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class Elements is the representation of the element which will be created in
 * the parsing phase. They will be atributes of the Node.
 * 
 * @see {@link hr.fer.zemris.java.custom.scripting.nodes.Node Node}
 * @author dario
 *
 */
public class Element {

	/**
	 * Returns the string representation of the given Element
	 * 
	 * @return String representation
	 */
	public String asText() {
		return "";
	}
	/**
	 * Returns the component.
	 * @return component
	 */
	public Object getComponent() {
	    if(this instanceof ElementConstantInteger) {
            return ((ElementConstantInteger)this).getValue();
        } else if(this instanceof ElementConstantDouble) {
            return ((ElementConstantDouble)this).getValue();
        } else if(this instanceof ElementString) {
            return ((ElementString)this).getValue();
        } else {
            throw new RuntimeException("Error: object doesn't have component. Parse error.");
        }
	}
}
