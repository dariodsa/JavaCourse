package hr.fer.zemris.java.gui.calc;

/**
 * Class {@link NormalFunctions} implements {@link Functions} and 
 * returns values that specific functions should return.
 * @author dario
 *
 */
public class NormalFunctions implements Functions {

    @Override
    public double log(double input) {
	return Math.log10(input);
    }

    @Override
    public double sin(double input) {
	return Math.sin(input);
    }

    @Override
    public double cos(double input) {
	return Math.cos(input);
    }

    @Override
    public double tan(double input) {
	return Math.tan(input);
    }

    @Override
    public double ctg(double input) {
	return 1.0 / Math.tan(input);
    }

    @Override
    public double ln(double input) {
	return Math.log(input);
    }

    @Override
    public double power(double input, double n) {
	return Math.pow(input, n);
    }

    @Override
    public double reciprocalValue(double value) {
	return 1.0 / value;
    }

}
