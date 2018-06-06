package hr.fer.zemris.java.custom.scripting.elems;

/**
 * {@link ElementOperator} iherits {@link Element} and has single read-only {@link String} 
 * property {@code symbol}. It overrides a {@link #asText() asText()} to return
 *  {@code symbol} property.
 * @author dario
 *
 */

public class ElementOperator extends Element{

	/**
	 * operator symbol
	 */
    private String symbol;
	/**
	 * Constructs the {@link ElementOperator} with the String symbol as 
	 * a parameter. 
	 * @param symbol - string symbol
	 */
	public ElementOperator(String symbol) {
		this.symbol = symbol;
	}
	/**
	 * Returns the String representation of the symbol which was
	 * given in the construction of the {@link ElementOperator}
	 * @return symbol String
	 */
	public String getSymbol() {
		return this.symbol;
	}
	/**
	 * Returns the string representation of the given Element
	 * @return String representation
	 */
	@Override
	public String asText() {
		return this.symbol;
	}
}
