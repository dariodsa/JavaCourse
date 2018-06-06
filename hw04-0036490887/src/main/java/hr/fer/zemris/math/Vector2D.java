package hr.fer.zemris.math;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 * 
 * {@link Vector2D} offers you to create two dimensional vectors, by method
 * {@link #Vector2D(double, double)}. It offers you to rotate, scale and
 * translated them. It is offered that method can return new vector, or perfrom
 * action on the exsiting one.
 * 
 * @author dario
 * @since 1.0
 * @version 1.0
 */
public class Vector2D {

	/**
	 * Real part of the vector, section on the x coordinate
	 */
	private double x;
	/**
	 * Real part of the vector, section on the y coordinate
	 */
	private double y;

	/**
	 * It constructs {@link Vector2D} with given parameters, x and y directions,
	 * which are double by their type.
	 * 
	 * @param x
	 *            - x part of vector
	 * @param y
	 *            - y part of vector
	 */
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Returns the first parameter of the {@link Vector2D}, eq. x part.
	 * 
	 * @return x part of the vector2D
	 */
	public double getX() {
		return this.x;
	}

	/**
	 * Returns the second parameter of the {@link Vector2D}, eq. y part.
	 * 
	 * @return y part of the vector2D
	 */
	public double getY() {
		return this.y;
	}

	/**
	 * It translates the {@link Vector2D} this, by the {@link Vector2D} offset.
	 * 
	 * @param offset
	 *            - offset by which will vector be moved
	 * @throws IllegalArgumentException
	 *             if offset is null
	 */
	public void translate(Vector2D offset) {
		if (offset == null)
			throw new IllegalArgumentException("Vector offset in translate is null.");
		this.x += offset.getX();
		this.y += offset.getY();
	}

	/**
	 * It calls {@link #copy()} and then {@link #translate(Vector2D)} to with
	 * {@link Vector2D} offset. It will return the new {@link Vector2D} as the
	 * result.
	 * 
	 * @param offset
	 *            - offset by which will vector be moved
	 * @return new translated {@link Vector2D}
	 * @see {@link #copy()}
	 * @see {@link #translate(Vector2D)}
	 */
	public Vector2D translated(Vector2D offset) {
		if (offset == null)
			throw new IllegalArgumentException("Vector offset in translated can't be null.");
		Vector2D vectorCopy = this.copy();
		vectorCopy.translate(offset);
		return vectorCopy;
	}

	/**
	 * Rotates the {@link Vector2D} by the degree as the parameter.
	 * 
	 * @param angle
	 *            - degree by which will vector be moved
	 */
	public void rotate(double angle) {
		double angleRad = angle * PI / 180;

		double oldX = this.x;

		this.x = this.x * cos(angleRad) - this.y * sin(angleRad);
		this.y = oldX * sin(angleRad) + this.y * cos(angleRad);
	}

	/**
	 * It copy current vector and then it rotates by the given degree.
	 * 
	 * @param angle
	 *            - degree by which will vector be moved
	 * @return new rotated Vector
	 * @see {@link #copy()}
	 * @see {@link #rotate(double)}
	 */
	public Vector2D rotated(double angle) {
		Vector2D vectorCopy = this.copy();
		vectorCopy.rotate(angle);
		return vectorCopy;
	}

	/**
	 * It scales the {@link Vector2D} by the given scale factor.
	 * 
	 * @param scaler
	 *            - double value, by which value vector will be scaled
	 */
	public void scale(double scaler) {
		this.x = this.x * scaler;
		this.y = this.y * scaler;
	}

	/**
	 * It copies current vector and then it scales it by the given scale factor.
	 * 
	 * @param scaler
	 *            - scale factor of the new vector
	 * @return - new vector, scaled by the given factor
	 * @see {@link #copy()}
	 * @see {@link #scale(double)}
	 */
	public Vector2D scaled(double scaler) {
		Vector2D vectorCopy = this.copy();
		vectorCopy.scale(scaler);
		return vectorCopy;
	}

	/**
	 * It constructs new Vector which will have same arguments as the current.
	 * 
	 * @return - copy of the current vector
	 */
	public Vector2D copy() {
		return new Vector2D(this.x, this.y);
	}

	/**
	 * Sets the x and y coordinate at the same time.
	 * 
	 * @param x
	 *            new x coordinate
	 * @param y
	 *            new y coordinate
	 */
	public void setXandY(double x, double y) {
		this.x = x;
		this.y = y;
	}
}
