package hr.fer.zemris.java.gui.layouts;

/**
 * Class {@link RCPosition} encapsulate two integer variables, row and width. It
 * is used in determine on which position in grid should some component go.
 * 
 * @author dario
 */
public class RCPosition {
    /**
     * row position
     */
    private int row;
    /**
     * column position
     */
    private int column;

    /**
     * Constructs {@link RCPosition} with row and column as thier parameters which
     * indicate its position.
     * 
     * @param row
     *            row number
     * @param column
     *            column number
     */
    public RCPosition(int row, int column) {
	this.row = row;
	this.column = column;
    }

    /**
     * Constructs {@link RCPosition} from string as parameter. String will be parsed
     * in order to construct {@link RCPosition}. String should be in the form of
     * num,num. So two numbers should be separated with a comma.
     * 
     * @param position
     *            string interpretation of {@link RCPosition}
     */
    public RCPosition(String position) {
	int index = position.indexOf(',');
	String first = position.substring(0, index);
	String second = position.substring(index + 1);
	this.row = Integer.valueOf(first);
	this.column = Integer.valueOf(second);
    }

    /**
     * Returns row of the object.
     * 
     * @return row number
     */
    public int getRow() {
	return this.row;
    }

    /**
     * Returns column of the object.
     * 
     * @return column number
     */
    public int getColumn() {
	return this.column;
    }

    @Override
    public String toString() {
	return "(" + this.row + ", " + this.column + ")";
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + column;
	result = prime * result + row;
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	RCPosition other = (RCPosition) obj;
	if (column != other.column)
	    return false;
	if (row != other.row)
	    return false;
	return true;
    }

}
