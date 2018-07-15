package hr.fer.zemris.java.hw16.trazilica.processor.structures;

/**
 * Class {@link Vector} encapsulate read only vector. User can get see vector's
 * distance, values and perform multiplication and cosine angle on others
 * vectors.
 * 
 * @author dario
 *
 */
public class Vector {

    /**
     * values of the vector
     */
    private double[] values;
    /**
     * distance of the vector
     */
    private double distance = 0;

    /**
     * Constructs the {@link Vector} with the given values.
     * 
     * @param values
     *            values of the vector, double values
     */
    public Vector(double... values) {
        this.values = new double[values.length];

        for (int i = 0; i < values.length; ++i) {
            this.values[i] = values[i];
        }
        for (int i = 0; i < this.values.length; ++i) {
            distance += this.values[i] * this.values[i];
        }
        this.distance = Math.sqrt(this.distance);
    }

    /**
     * Returns the values of the vector.
     * 
     * @return value of the vector, double array
     */
    public double[] getValues() {
        return this.values;
    }

    /**
     * Returns the value at the given position.
     * 
     * @param pos
     *            position
     * @return value at the given position
     */
    public double getValue(int pos) {
        if (pos < 0 && pos >= values.length) {
            throw new RuntimeException("Index was not in the given range.");
        }
        return this.values[pos];
    }

    /**
     * Returns the scalar multiplication result between those two vectors.
     * 
     * @param v
     *            other vector
     * @return result of the scalar multiplication between those two vectors
     */
    public double scalarMultiply(Vector v) {
        double answer = 0;

        for (int i = 0; i < values.length; ++i) {
            answer += values[i] * v.values[i];
        }
        return answer;
    }

    /**
     * Returns the normal value of the vector.
     * 
     * @return normalized value of the vector
     */
    public double distance() {
        return this.distance;
    }

    /**
     * Returns the cosine angle between those two vectors.
     * 
     * @param v
     *            other vector
     * @return cosine angle
     */
    public double cosine(Vector v) {
        double answer = scalarMultiply(v);

        if(distance == 0 || v.distance == 0) {
            return 0;
        }
        answer /= distance;
        answer /= v.distance;

        return answer;
    }
}
