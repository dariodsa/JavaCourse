package hr.fer.zemris.java.custom.scripting.elems;

/**
 * {@link ElementFunction} inherits {@link Element} and has single read-only
 * String property name. It overrides a {@link #asText() asText()} to return a
 * string representation of name property.
 * 
 * @author dario
 *
 */

public class ElementFunction extends Element {

	private String name;

	/**
	 * Constructs the {@link ElementFunction} with the String name as the parameter.
	 * 
	 * @param name
	 */
	public ElementFunction(String name) {
		this.name = name;
	}

	/**
	 * Returns the String name of the {@link ElementFunction}, which was given in
	 * the construction
	 * 
	 * @return {@link String} name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Returns the string representation of the given Element
	 * 
	 * @return String representation
	 */
	@Override
	public String asText() {
		return this.name;
	}

}
