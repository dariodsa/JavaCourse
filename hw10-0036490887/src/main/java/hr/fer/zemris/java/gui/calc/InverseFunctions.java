package hr.fer.zemris.java.gui.calc;

/**
 * {@link InverseFunctions} implements {@link Functions} which implements all
 * functions in a way that changes x and y component of a function so it will
 * call inverse function.
 * 
 * @author dario
 *
 */
public class InverseFunctions implements Functions {

    @Override
    public double log(double input) {
	return Math.pow(10, input);
    }

    @Override
    public double sin(double input) {
	return Math.asin(input);
    }

    @Override
    public double cos(double input) {
	return Math.acos(input);
    }

    @Override
    public double tan(double input) {
	return Math.atan(input);
    }

    @Override
    public double ctg(double input) {
	return 1.0 / Math.atan(input);
    }

    @Override
    public double ln(double input) {
	return Math.exp(input);
    }

    @Override
    public double power(double input, double n) {
	return Math.pow(input, 1.0 / n);
    }

    @Override
    public double reciprocalValue(double value) {
	return 1.0 / value;
    }

}
