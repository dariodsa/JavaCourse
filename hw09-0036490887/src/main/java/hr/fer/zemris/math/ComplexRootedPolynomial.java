package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;

/**
 * Class {@link ComplexRootedPolynomial} encapsulate polynom of n-th order, with
 * complex numbers as factors. In constructor we will give, roots of the
 * polynom, not its factors. You can converto {@link ComplexRootedPolynomial}
 * polynom in {@link ComplexPolynomial} by calling {@link #toComplexPolynom()}.
 * You can also ask for which root is the closest to some point by calling
 * {@ink #indexOfClosestRootFor(Complex, double) this method.}
 * 
 * @author dario
 *
 */
public class ComplexRootedPolynomial {

    /**
     * roots of the polynomial
     */
    private List<Complex> roots;

    /**
     * Creates {@link ComplexRootedPolynomial} with given roots from which it can be
     * constructed polynomial.
     * 
     * @param roots
     *            polynomial's roots
     */
    public ComplexRootedPolynomial(Complex... roots) {
        this.roots = new ArrayList<>();
        for (Complex c : roots) {
            this.roots.add(c);
        }
    }

    /**
     * Returns {@link Complex} value of the function for the given parameter.
     * 
     * @param z
     *            value for which is we want value
     * @return value of the function with z as parameter
     */
    public Complex apply(Complex z) {

        Complex result = new Complex(z.getReal(), z.getImaginary());

        for (Complex root : roots) {
            result = result.multiply(z.sub(root));
        }
        return result;
    }

    /**
     * Returns {@link ComplexPolynomial} from {@link ComplexRootedPolynomial}, or (x
     * - x0) * (x - x1) .. to a*x^n + b*x^(n-1) + ...
     * 
     * @return {@link ComplexPolynomial} from current one
     */
    public ComplexPolynomial toComplexPolynom() {
        ComplexPolynomial pol = new ComplexPolynomial(Complex.ONE, roots.get(0).negate());

        for (int i = 1; i < roots.size(); ++i) {
            pol = pol.multiply(new ComplexPolynomial(Complex.ONE, roots.get(i).negate()));
        }

        return pol;
    }

    @Override
    public String toString() {
        return this.toComplexPolynom().toString();
    }

    /**
     * Finds index of root which distance to given point is the smallest and it
     * smaller then the given treshold.
     * 
     * @param z
     *            {@link Complex} number from which we measure distance
     * @param treshold
     *            maximal distance, distance from root must be smaller than it
     * @return index of closest root , -1 if is not found
     */
    public int indexOfClosestRootFor(Complex z, double treshold) {
        double distance = treshold;
        int index = -1;
        for (int i = 0; i < roots.size(); ++i) {
            if (z.sub(roots.get(i)).module() < distance) {
                distance = z.sub(roots.get(i)).module();
                index = i;
            }

        }
        return index;
    }
}
