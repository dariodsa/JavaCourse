package hr.fer.zemris.math;

/**
 * Class {@link Vector3} encapsulate vector, 3 dimensional in mathematical term.
 * In {link {@link #Vector3(double, double, double) constructor} you need to
 * provide three values. They are read only property, so you will be able so get
 * them but not to change afterwards.
 * 
 * @author dario
 *
 */
public class Vector3 {

    /**
     * compare difference
     */
    private final double DELTA = 1E-3;
    /**
     * x component
     */
    private double x;
    /**
     * y component
     */
    private double y;
    /**
     * z component
     */
    private double z;

    /**
     * Constructs {@link Vector3} with three parameter as it's main attributes.
     * 
     * @param x
     *            x parameter of vector
     * @param y
     *            y parameter of vector
     * @param z
     *            z parameter of vector
     */
    public Vector3(double x, double y, double z) {

        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Returns x value of the vector.
     * 
     * @return vector's x value
     */
    public double getX() {
        return x;
    }

    /**
     * Returns y value of the vector.
     * 
     * @return vector's y value
     */
    public double getY() {
        return y;
    }

    /**
     * Returns z value of the vector.
     * 
     * @return vector's z value
     */
    public double getZ() {
        return z;
    }

    /**
     * Returns vector's norm, or distance from center.
     * 
     * @return vector's norm
     */
    public double norm() {
        return Math.sqrt(x * x + y * y + z * z);
    }

    /**
     * Normalize this vector by dividing all coefficients by the norm of the vector.
     * 
     * @return normalized this vector
     */
    public Vector3 normalized() {
        double len = Math.sqrt(x * x + y * y + z * z);
        return new Vector3(x / len, y / len, z / len);
    }

    /**
     * Adds this vector with given one and returns new as the result.
     * 
     * @param other
     *            second parameter of addition
     * @return result of operation
     */
    public Vector3 add(Vector3 other) {
        return new Vector3(x + other.getX(), y + other.getY(), z + other.getZ());
    }

    /**
     * Subs this vector with given one and returns new as the result.
     * 
     * @param other
     *            second parameter of subtraction
     * @return result of operation
     */
    public Vector3 sub(Vector3 other) {
        return new Vector3(x - other.getX(), y - other.getY(), z - other.getZ());
    }

    /**
     * Returns dot product of this vector and given one.
     * 
     * @param other
     *            second parameter in dot product
     * @return result of dot product, as double
     */
    public double dot(Vector3 other) {
        return x * other.getX() + y * other.getY() + z * other.getZ();
    }

    /**
     * Returns new vector which coefficients are multiplied by given scale.
     * 
     * @param s
     *            multiplication scale
     * @return new vector as the result
     */
    public Vector3 scale(double s) {
        return new Vector3(x * s, y * s, z * s);
    }

    /**
     * Returns cosine value of angle between this vector and given one.
     * 
     * @param other
     *            other vector for comparison
     * @return cosine value of amgle between two vectors
     */
    public double cosAngle(Vector3 other) {
        return this.dot(other) / (this.norm() * other.norm());
    }

    /**
     * Returns new vector as the result of vector cross product of this and given
     * one.
     * 
     * @param other
     *            second parameter in product function
     * @return result of vector product
     */
    public Vector3 cross(Vector3 other) {
        return new Vector3(y * other.getZ() - z * other.getY(), -(x * other.getZ() - z * other.getX()),
                (x * other.getY() - other.getX() * y));
    }

    /**
     * Returns array of vector's coefficients with the length 3-
     * 
     * @return array of vector's coefficients
     */
    public double[] toArray() {
        return new double[] { x, y, z };
    }

    @Override
    public String toString() {
        return String.format("(%.6f, %.6f, %.6f)", x, y, z);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Vector3 other = (Vector3) obj;

        return Math.abs(other.x - x) < DELTA && Math.abs(other.y - y) < DELTA && Math.abs(other.z - z) < DELTA;
    }

}
