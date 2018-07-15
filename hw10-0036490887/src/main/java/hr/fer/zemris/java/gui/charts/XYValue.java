package hr.fer.zemris.java.gui.charts;

/**
 * Class {@link XYValue} encapsulate class with two integers, similar as so
 * called class Pair. It's attributes are read-only so there exist only getters
 * for them.
 * 
 * @author dario
 *
 */
public class XYValue {
    /**
     * x value
     */
    private int x;
    /**
     * y value
     */
    private int y;

    /**
     * Constructs {@link XYValue} with x and y values as thier parameters.
     * 
     * @param x
     *            x value
     * @param y
     *            y value
     */
    public XYValue(int x, int y) {
	this.x = x;
	this.y = y;
    }

    /**
     * Returns x value of the object
     * 
     * @return object's x value
     */
    public int getX() {
	return x;
    }

    /**
     * Returns y value of the object
     * 
     * @return object's y value
     */
    public int getY() {
	return y;
    }

}
