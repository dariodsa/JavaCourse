package hr.fer.zemris.java.custom.scripting.elems;

/**
 * {@link ElementConstantDouble} inherits {@link Element} and has single read-only
 * double property. It overrides {@link #asText() asText()} to return string 
 * representation of the double.
 * @author dario
 *
 */

public class ElementConstantDouble extends Element {
    
    /**
     * double value
     */
	private double value;

	/**
	 * Construct the {@link ElementConstantDouble} with the double as the parametar.
	 * 
	 * @param value
	 *            double as the value of the Element
	 */
	public ElementConstantDouble(double value) {
		this.value = value;
	}

	/**
	 * Returns the {@code Double} value of given element.
	 * 
	 * @return double value
	 */
	public double getValue() {
		return this.value;
	}

	/**
	 * Returns the string representation of the given Element
	 * 
	 * @return String representation
	 */
	@Override
	public String asText() {
		return Double.valueOf(value).toString();
	}
}
