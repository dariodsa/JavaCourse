package hr.fer.zemris.java.gui.calc;

/**
 * Interface {@link Functions} specifies mathematical function which should be
 * implemented. They can be implemented in regular, normal way when values x and
 * y is not replaced, or when they are, or so called inverse functions.
 * 
 * @author dario
 *
 */
public interface Functions {
    
    /**
     * Returns log of a given value.
     * 
     * @param input
     *            value on which operation will be performed
     * @return double, result of a operation
     */
    public double log(double input);

    /**
     * Returns sin of a given value.
     * 
     * @param input
     *            value on which operation will be performed
     * @return double, result of a operation
     */
    public double sin(double input);

    /**
     * Returns cosine of a given value.
     * 
     * @param input
     *            value on which operation will be performed
     * @return double, result of a operation
     */
    public double cos(double input);

    /**
     * Returns tan of a given value.
     * 
     * @param input
     *            value on which operation will be performed
     * @return double, result of a operation
     */
    public double tan(double input);

    /**
     * Returns 1.0 / tan of a given value.
     * 
     * @param input
     *            value on which operation will be performed
     * @return double, result of a operation
     */
    public double ctg(double input);

    /**
     * Returns natural logarithm of a given value.
     * 
     * @param input
     *            value on which operation will be performed
     * @return double, result of a operation
     */
    public double ln(double input);

    /**
     * Returns power on input on n.
     * 
     * @param input
     *            value on which operation will be performed
     * @param n
     *            value on which operation will be performed
     * @return double, result of a operation
     */
    public double power(double input, double n);

    /**
     * Return 1 divided by a given value.
     * 
     * @param value
     *            value on which operation will be performed
     * @return double, result of a operation
     */
    public double reciprocalValue(double value);
}
