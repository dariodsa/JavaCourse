package hr.fer.zemris.java.custom.scripting.elems;

/**
 * {@link ElementVariable} inherits {@link Element} and has single 
 * read-only {@link String} property {@code name}. It overrides 
 * {@link #asText() asText()} to return the value of {@code name} property.
 * @author dario
 *
 */

public class ElementVariable extends Element{

	private String name;
	/**
	 * Constructs the {@link ElementVariable} with String name as a parameter.
	 * @param name
	 */
	public ElementVariable(String name) {
		this.name = name;
	}
	/**
	 * Returns the name of the {@link ElementVariable}, which
	 * was given in the construction of the object.
	 * @return name
	 */
	public String getName() {
		return this.name;
	}
	/**
	 * Returns the string representation of the given Element
	 * @return String representation
	 */
	@Override 
	public String asText() {
		return this.name;
	}

}
