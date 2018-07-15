package hr.fer.zemris.java.custom.scripting.elems;

/**
 * {@link ElementConstantInteger} inherits {@link Element} and has single read only 
 * int property value. It overrides a {@link #asText() asText()} to return string 
 * representation of the value property. 
 * 
 * @author dario
 *
 */

public class ElementConstantInteger extends Element {

	private int value;

	/**
	 * Constructs the {@link ElementConstantInteger} with the int value as the parameter.
	 * @param value
	 */
	public ElementConstantInteger(int value) {
		this.value = value;
	}

	/**
	 * Returns the int value of the given {@link ElementConstantInteger}
	 * 
	 * @return int value of the {@link ElementConstantInteger}
	 */
	public int getValue() {
		return this.value;
	}

	/**
	 * Returns the string representation of the given Element
	 * 
	 * @return String representation
	 */
	@Override
	public String asText() {
		return Integer.valueOf(value).toString();
	}

}
