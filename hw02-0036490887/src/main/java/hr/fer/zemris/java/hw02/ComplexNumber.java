package hr.fer.zemris.java.hw02;

import static java.lang.Math.PI;
import static java.lang.Math.atan;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;


/**
 * Class, names {@code ComplexNumber} contains real and imaginary part of
 * complex number, eq. two doubles. It supports basic operation on complex
 * numbers, allows you to get attributes, change them and also creates one with
 * angle and magnitude.
 * 
 * @author dario
 *
 */
public class ComplexNumber {

	/**
	 * real part of {@link ComplexNumber}
	 */
    private double real;
    /**
     * imaginary part of {@link ComplexNumber}
     */
	private double imaginary;

	/**
	 * Creates {@code ComplexNumber} with real and imaginary part, getting them from
	 * the parameters.
	 * 
	 * @param real
	 *            {@code double} real part of the complex number
	 * @param imaginary {@code double} imaginary part of the complex number
	 */
	public ComplexNumber(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}

	/**
	 * Returns the {@code ComplexNumber} with parameter real as real parameter.
	 * 
	 * @param real real component of a complex number
	 * @return {@code ComplexNUmber} with real as real part and 0 as imaginary part.
	 */
	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real, 0);
	}

	/**
	 * Returns the {@code ComplexNumber} with parameter imaginary as real parameter.
	 * 
	 * @param imaginary imaginary part of a complex number
	 * @return {@code ComplexNUmber} with imaginary as imaginary part and 0 as real
	 *         part.
	 */
	public static ComplexNumber fromImaginary(double imaginary) {
		return new ComplexNumber(0, imaginary);
	}

	/**
	 * Returns the {@code ComplexNumber} with real and imaginary part which equals
	 * to the given distance and angle.
	 * 
	 * @param magnitude
	 *            distance from 0,0
	 * @param angle angle of a complex number, any double value
	 * @return complex number with real and imaginary part which equals to that
	 *         distance and angle
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
		while (angle > 2 * PI)
			angle -= 2 * PI;
		while (angle < 0)
			angle += 2 * PI;
		double real = 0;
		double imaginary = 0;

		real = magnitude * Math.cos(angle);
		imaginary = magnitude * Math.sin(angle);

		return new ComplexNumber(real, imaginary);
	}

	/**
	 * Parse string to the {@code ComplexNumber}. The String can have either real
	 * part, either imaginary or both.
	 * <p>
	 * Double must be expresed with decimal dot, not comma.
	 * </p>
	 * 
	 * @param input {@link String} from which will parsing be able
	 * @return parsed complex number
	 * @throws IllegalArgumentException String can't be parsed.
	 * @throws NumberFormatException String can't be parsed.
	 * @throws NullPointerException String can't be null
	 */
	public static ComplexNumber parse(String input) {
		if (input == null)
			throw new NullPointerException("String can't be null");
		ComplexNumber result = null;

        input = input.replace(" ", "");
        int index = 0;
        for (int i = 1; i < input.length(); ++i) {
            if (input.charAt(i) == '+' || input.charAt(i) == '-') {
                index = i;
                break;
            }
        }
        double real = 0;
        double imaginary = 0;
        if (index == 0) {
            if (input.contains("i")) {
                if (input.equals("i")) {
                    result = new ComplexNumber(0, 1);
                    return result;
                } else if (input.equals("-i")) {
                    result = new ComplexNumber(0, -1);
                    return result;
                } else {
                    input = input.replace("i", "");
                    result = new ComplexNumber(0, Double.parseDouble(input));
                    return result;
                }
            } else {
                real += Double.parseDouble(input);
            }
        } else {
            String first = input.substring(0, index);
            String second = input.substring(index);
            if (first.contains("i")) {
                first = first.replace("i", "");
                imaginary += Double.parseDouble(first);
            } else if (!first.contains("i")) {
                real += Double.parseDouble(first);
            }

            if (second.contains("i")) {
                second = second.replace("i", "");
                imaginary += Double.parseDouble(second);
            } else if (!second.contains("i")) {
                real += Double.parseDouble(second);
            }
        }
       
        result = new ComplexNumber(real, imaginary);
        return result;
	}

	/**
	 * Returns the real part of the complex number.
	 * 
	 * @return {@code double} real part
	 */
	public double getReal() {
		return this.real;
	}

	/**
	 * Returns the imaginary part of the complex number.
	 * 
	 * @return {@code double} imaginary part
	 */
	public double getImaginary() {
		return this.imaginary;
	}

	/**
	 * Returns the magnitude of complex number. Formula which is used is : sqrt(
	 * real^2 + imaginary^2)
	 * 
	 * @return {@code double} distance from 0,0
	 */
	public double getMagnitude() {
		return sqrt(real * real + imaginary * imaginary);
	}

	/**
	 * Angle in comparison with positive part of x axis. It returns the value from 0
	 * to 2pi.
	 * 
	 * @return {@code double} angle in comparison with positive part of x axis
	 */
	public double getAngle() {
		double angle;
		if (real == 0 && imaginary < 0)
			angle = -PI / 2;
		if (real == 0 && imaginary >= 0)
			angle = PI / 2;
		if ((real > 0 && imaginary >= 0) || (real > 0 && imaginary < 0))
			angle = atan(imaginary / real);
		else if (real < 0 && imaginary >= 0) {
			angle = atan(imaginary / real) + PI;
		} else {
			angle = atan(imaginary / real) - PI;
		}
		
		while (angle > 2 * PI)
			angle -= 2 * PI;
		while (angle < 0)
			angle += 2 * PI;
		
		return angle;
	}

	/**
	 * Returns {@code String} representation of the complex number. Format being
	 * used is : (real, imaginary)
	 */
	public String toString() {
		return String.format("(%f, %f)", real, imaginary);
	}

	/**
	 * Adds two {@code ComplexNumber}, this and {@code ComplexNumber} c.
	 * 
	 * @param c second parameter in operation of summation
	 * @return {@code ComplexNumber} result of summation
	 * @throws NullPointerException Complex number c can't be null
	 */
	public ComplexNumber add(ComplexNumber c) {
		if (c == null)
			throw new NullPointerException("Complex number c can't be null");

		return new ComplexNumber(this.real + c.getReal(), this.imaginary + c.getImaginary());
	}

	/**
	 * Subs two {@code ComplexNumber}, this and {@code ComplexNumber} c.
	 * 
	 * @param c second parameter in operation of subtraction
	 * @return {@code ComplexNumber} result of subtraction
	 * @throws NullPointerException complex number can't be null
	 */
	public ComplexNumber sub(ComplexNumber c) {
		if (c == null)
			throw new NullPointerException("Complex number c can't be null");

		return new ComplexNumber(this.real - c.getReal(), this.imaginary - c.getImaginary());
	}

	/**
	 * Multiply two {@code ComplexNumber}, this and {@code ComplexNumber} c.
	 * 
	 * @param c second parameter in operation of multiplication
	 * @return {@code ComplexNumber} result of multiplication
	 * @throws NullPointerException complex number can't be null
	 */
	public ComplexNumber mul(ComplexNumber c) {
		if (c == null)
			throw new NullPointerException("Complex number c can't be null");

		return new ComplexNumber(this.real * c.getReal() - this.imaginary * c.getImaginary(),
				this.imaginary * c.getReal() + this.real * c.getImaginary());
	}

	/**
	 * Divide two {@code ComplexNumber}, this and c.
	 * 
	 * @param c second parameter in operation of division
	 * @return {@code ComplexNumber} result of division
	 * @throws NullPointerException complex number can't be null
	 */
	public ComplexNumber div(ComplexNumber c) {
		if (c == null)
			throw new NullPointerException("Complex number c can't be null");

		double divider = c.getReal() * c.getReal() + c.getImaginary() * c.getImaginary();
		return new ComplexNumber((this.real * c.getReal() + this.imaginary * c.getImaginary()) / divider,
				(this.imaginary * c.getReal() - this.real * c.getImaginary()) / divider);
	}

	/**
	 * Returns power of the complex number.
	 * 
	 * @param n parameter to which power
	 * @return complex number to the power of n
	 * @throws IllegalArgumentException N can't be less then zero.
	 */
	public ComplexNumber power(int n) {
		if (n < 0)
			throw new IllegalArgumentException("N can't be less then zero.");

		double angle = getAngle() * n;
		double magnitude = pow(getMagnitude(), n);

		return fromMagnitudeAndAngle(magnitude, angle);
	}

	/**
	 * Returns the complex number n-th root.
	 * 
	 * @param n parameter to which root
	 * @return complex number n-th root
	 * @throws IllegalArgumentException Root number is less or equal to zero.
	 */
	public ComplexNumber[] root(int n) {
		if (n <= 0)
			throw new IllegalArgumentException("Root number is less or equal to zero.");
		ComplexNumber[] numbers = new ComplexNumber[n];
		for (int i = 0; i < n; ++i) {
			double angle = (getAngle() + 2 * i * PI) / n;
			double magnitude = pow(getMagnitude(), 1f / n);
			numbers[i] = fromMagnitudeAndAngle(magnitude, angle);
		}
		return numbers;
	}

}
