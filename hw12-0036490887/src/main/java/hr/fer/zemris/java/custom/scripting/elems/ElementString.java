package hr.fer.zemris.java.custom.scripting.elems;

/**
 * {@link ElementString} inherits {@link Element} and has single read-only
 * {@link String} property value. It overrides a {@link #asText() asText()} to
 * return string represenatation of the value property.
 * 
 * @author dario
 *
 */

public class ElementString extends Element {

	/**
	 * value string
	 */
    private String value;

	/**
	 * Constructs the {@link ElementString} with the String value as the parameter.
	 * 
	 * @param value
	 *            String value
	 */
	public ElementString(String value) {
		this.value = value;
	}

	/**
	 * Returns the String value of the {@link ElementVariable} which was given in
	 * the construction of the object
	 * 
	 * @return string value
	 */
	public String getValue() {
		return this.value;
	}

	/**
	 * Returns the string representation of the given Element
	 * 
	 * @return String representation
	 */
	@Override
	public String asText() {
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
