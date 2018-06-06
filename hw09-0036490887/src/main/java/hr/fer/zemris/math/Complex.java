package hr.fer.zemris.math;

import static java.lang.Math.PI;
import static java.lang.Math.atan;
import static java.lang.Math.pow;

import java.util.ArrayList;
import java.util.List;

/**
 * Class {@link Complex} creates complex number with its real and complex part.
 * It offers you to {@link #add(Complex) add} them and {@link #sub(Complex)
 * subtract} and {@link #multiply(Complex) multiply}. It is possible to get
 * {@link #module() module}, {@link #root(int)} root(int)} and
 * {@link #power(int)}. It also reimplements equal method so it is OK to call
 * that method on this object.
 * 
 * @author dario
 *
 */
public class Complex {

    /**
     * compare difference
     */
    private final double DELTA = 1E-3;
    /**
     * zero value
     */
    public static final Complex ZERO = new Complex(0, 0);
    /**
     * value one
     */
    public static final Complex ONE = new Complex(1, 0);
    /**
     * value one negative
     */
    public static final Complex ONE_NEG = new Complex(-1, 0);
    /**
     * value one complex
     */
    public static final Complex IM = new Complex(0, 1);
    /**
     * value one negative complex
     */
    public static final Complex IM_NEG = new Complex(0, -1);
    /**
     * real part
     */
    private double re;
    /**
     * imaginary part
     */
    private double im;

    /**
     * Construct {@link Complex} number with given real and imaginary part.
     * 
     * @param re
     *            real part of complex number
     * @param im
     *            imaginary part of complex number
     */
    public Complex(double re, double im) {
        this.re = re;
        this.im = im;
    }

    /**
     * Returns the module of complex number. Formula which is used is : sqrt( real^2
     * + imaginary^2)
     * 
     * @return {@code double} distance from 0,0
     */
    public double module() {
        return Math.sqrt(re * re + im * im);
    }

    /**
     * Multiply two {@link Complex}, this and given complex number c.
     * 
     * @param c
     *            second parameter
     * @return result of multiplication
     */
    public Complex multiply(Complex c) {
        if (c == null)
            throw new NullPointerException("Complex number c can't be null");

        return new Complex(this.re * c.getReal() - this.im * c.getImaginary(),
                this.im * c.getReal() + this.re * c.getImaginary());
    }

    /**
     * Divs two {@link Complex}, this and given complex number c.
     * 
     * @param c
     *            second parameter
     * @return result of division
     */
    public Complex divide(Complex c) {
        if (c == null)
            throw new NullPointerException("Complex number c can't be null");

        double divider = c.getReal() * c.getReal() + c.getImaginary() * c.getImaginary();
        return new Complex((this.re * c.getReal() + this.im * c.getImaginary()) / divider,
                (this.im * c.getReal() - this.re * c.getImaginary()) / divider);
    }

    /**
     * Adds two {@link Complex}, this and given complex number c.
     * 
     * @param c
     *            second parameter
     * @return result of addition
     */
    public Complex add(Complex c) {
        return new Complex(re + c.getReal(), im + c.getImaginary());
    }

    /**
     * Subs two {@link Complex}, this and given complex number c.
     * 
     * @param c
     *            second parameter
     * @return result of subtraction
     */
    public Complex sub(Complex c) {
        return new Complex(re - c.getReal(), im - c.getImaginary());
    }

    /**
     * Returns power of the complex number.
     *
     * @param n
     *            parameter to which power
     * @return complex number to the power of n
     * @throws IllegalArgumentException
     *             N can't be less then zero
     */
    public Complex power(int n) {
        if (n < 0)
            throw new IllegalArgumentException("N can't be less then zero.");

        double angle = getAngle() * n;
        double magnitude = pow(module(), n);
        return fromMagnitudeAndAngle(magnitude, angle);
    }

    /**
     * Angle in comparison with positive part of x axis. It returns the value from 0
     * to 2pi.
     * 
     * @return {@code double} angle in comparison with positive part of x axis
     */
    private double getAngle() {
        double angle;
        if (re == 0 && im < 0)
            angle = -PI / 2;
        if (re == 0 && im >= 0)
            angle = PI / 2;
        if ((re > 0 && im >= 0) || (re > 0 && im < 0))
            angle = atan(im / re);
        else if (re < 0 && im >= 0) {
            angle = atan(im / re) + PI;
        } else {
            angle = atan(im / re) - PI;
        }

        while (angle > 2 * PI)
            angle -= 2 * PI;
        while (angle < 0)
            angle += 2 * PI;

        return angle;
    }

    /**
     * Returns the {@code Complex} with real and imaginary part which equals to the
     * given distance and angle.
     * 
     * @param magnitude
     *            distance from 0,0
     * @param angle
     *            angle of the complex number, any real number
     * @return complex number with real and imaginary part which equals to that
     *         distance and angle
     **/
    private static Complex fromMagnitudeAndAngle(double magnitude, double angle) {
        while (angle > 2 * PI)
            angle -= 2 * PI;
        while (angle < 0)
            angle += 2 * PI;
        double real = 0;
        double imaginary = 0;

        real = magnitude * Math.cos(angle);
        imaginary = magnitude * Math.sin(angle);

        return new Complex(real, imaginary);
    }

    /**
     * Returns the complex number n-th root.
     * 
     * @param n
     *            parameter to which root
     * @return complex number n-th root
     * @throws IllegalArgumentException
     *             root number is less or equal to zero
     */
    public List<Complex> root(int n) {
        if (n <= 0)
            throw new IllegalArgumentException("Root number is less or equal to zero.");
        List<Complex> numbers = new ArrayList<>(n);
        for (int i = 0; i < n; ++i) {
            double angle = (getAngle() + 2 * i * Math.PI) / n;
            double magnitude = pow(module(), 1f / n);
            numbers.add(fromMagnitudeAndAngle(magnitude, angle));
        }
        return numbers;
    }

    /**
     * Negate current complex number and returns the new one.
     * 
     * @return negation of complex number
     */
    public Complex negate() {
        return new Complex(-re, -im);
    }

    /**
     * Returns string representation of complex number.
     */
    public String toString() {
        return String.format("%.5f %.5fi", re, im);
    }

    /**
     * Returns real part of complex number.
     * 
     * @return real part of complex number
     */
    public double getReal() {
        return this.re;
    }

    /**
     * Returns imaginary part of complex number.
     * 
     * @return imaginary part of complex number
     */
    public double getImaginary() {
        return this.im;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Complex other = (Complex) obj;

        return Math.abs(other.re - re) < DELTA && Math.abs(other.im - im) < DELTA;
    }
}
