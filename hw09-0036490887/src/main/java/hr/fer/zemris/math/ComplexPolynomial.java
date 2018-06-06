package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;

/**
 * Class {@link ComplexPolynomial} encapsulate polynomial of n degree which
 * factors can be complex numbers. You will need them in
 * {@link #ComplexPolynomial(Complex...) in constructor method} and you can
 * {@link #getFactors() get them}. You can also
 * {@link #multiply(ComplexPolynomial) multiply} {@link ComplexPolynomial} with
 * each other, {@link #derive() deriver it} and also {@link #apply(Complex) get
 * value for specific input}.
 * 
 * @author dario
 *
 */
public class ComplexPolynomial {

    /**
     * polynomial factors
     */
    List<Complex> factors;

    /**
     * Constructs {@link ComplexPolynomial} with given factors, from bigger factors
     * to the smaller one. First one is the biggest and last one is so called free
     * coefficient.
     * 
     * @param factors
     *            factors of polynomial
     */
    public ComplexPolynomial(Complex... factors) {
        this.factors = new ArrayList<>();
        for (Complex c : factors) {
            this.factors.add(c);
        }
    }

    private List<Complex> getFactors() {
        return this.factors;
    }

    /**
     * Returns polynomial order. It will be from 0 to N.
     * 
     * @return polynomial order
     */
    public short order() {
        return (short) (factors.size() - 1);
    }

    /**
     * Multiply this {@link ComplexPolynomial} with given one.
     * 
     * @param p
     *            second parameter in multiplication
     * @return new {@link ComplexPolynomial}, result of multiplication
     */
    public ComplexPolynomial multiply(ComplexPolynomial p) {

        List<Complex> list = new ArrayList<>();

        for (int i = 0; i < p.order() + factors.size(); ++i) {
            list.add(new Complex(0, 0));
        }

        int maxOrder = p.order() + factors.size() - 1;
        for (int i = 0; i < factors.size(); ++i) {
            for (int j = 0; j < p.getFactors().size(); ++j) {
                int order = maxOrder - (factors.size() - 1 - i + (p.getFactors().size() - 1 - j));
                list.set(order, list.get(order).add(factors.get(i).multiply(p.getFactors().get(j))));
            }
        }
        return new ComplexPolynomial(list.toArray(new Complex[0]));
    }

    /**
     * Derives this polynomial and returns new one as the result.
     * 
     * @return derived polynomial as result
     */
    public ComplexPolynomial derive() {
        List<Complex> list = new ArrayList<>();
        for (int i = 0; i < factors.size() - 1; ++i) {
            list.add(factors.get(i).multiply(new Complex(factors.size() - i - 1, 0)));
        }
        return new ComplexPolynomial(list.toArray(new Complex[0]));
    }

    /**
     * Calculates polynomial value for given value z.
     * 
     * @param z
     *            value for which want function value
     * @return {@link Complex} value of function with given value
     */
    public Complex apply(Complex z) {
        Complex result = new Complex(0, 0);

        int n = factors.size() - 1;
        for (Complex factor : factors) {
            result = result.add(z.power(n).multiply(factor));
            --n;
        }

        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < factors.size(); ++i) {
            builder.append("z ^" + (factors.size() - 1 - i) + " * " + factors.get(i).toString());
            if (i != factors.size() - 1) {
                builder.append(" + ");
            }
        }
        return builder.toString();
    }
}
