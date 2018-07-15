package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * Class {@link CalcLayout} implements {@link LayoutManager2} and its task is to
 * implements all methods from {@link LayoutManager2} and to take care of all
 * components that will be added to it. It's task is to arrange it in a grid
 * with 5 rows and 7 columns, of which all are equal size expect of a (1,1)
 * which length is 5 so position (1,2), (1.3) and others are invalid.
 * 
 * @author dario
 *
 */
public class CalcLayout implements LayoutManager2 {

    /**
     * number of rows
     */
    private static final int ROWS = 5;
    /**
     * number of columns
     */
    private static final int COLUMNS = 7;
    /**
     * top, left, right and bottom space between two components
     */
    private int space;
    /**
     * two dimensional array of components, location in array corresponds with
     * location in the grid
     */
    private Component[][] values;
    /**
     * function which checks if the position is in the specific range
     */
    private Predicate<RCPosition> function;

    /**
     * Default constructor of {@link CalcLayout} which sets {@link #space} on 0.
     */
    public CalcLayout() {
	this(0);
    }

    /**
     * Constructs {@link CalcLayout} and sets {@link #space} to the given value.
     * 
     * @param space
     *            new space value of the {@link CalcLayout}
     */
    public CalcLayout(int space) {
	if (space < 0) {
	    throw new IllegalArgumentException("Space between can't be less then zero.");
	}
	this.space = space;
	this.values = new Component[ROWS][COLUMNS];

	this.function = (t) -> {
	    if (t.getRow() < 1 || t.getColumn() < 1) {
		return false;
	    }
	    if (t.getColumn() > COLUMNS || t.getRow() > ROWS) {
		return false;
	    }
	    if (t.getColumn() > 1 && t.getRow() == 1 && t.getColumn() < 6) {
		return false;
	    }
	    return true;
	};

    }

    @Override
    public void addLayoutComponent(String position, Component component) {
	try {
	    RCPosition pos = new RCPosition(position);
	    addLayoutComponent(component, pos);
	} catch (Exception ex) {
	    throw new CalcLayoutException("Position is not valid.");
	}
    }

    @Override
    public void layoutContainer(Container container) {
	Insets in = container.getInsets();
	int width = container.getWidth() - in.left - in.right;
	int height = container.getHeight() - in.top - in.bottom;

	int itemWidth = (width - (COLUMNS - 1) * space) / COLUMNS;
	int itemHeight = (height - (ROWS - 1) * space) / ROWS;
	for (int i = 0; i < ROWS; ++i) {
	    for (int j = 0; j < COLUMNS; ++j) {
		if (values[i][j] == null)
		    continue;
		int x = itemWidth * j + j * space;
		int y = itemHeight * i + i * space;
		if (i == 0 && j == 0) {
		    values[i][j].setBounds(x, y, itemWidth * 5 + 4 * space, itemHeight);
		} else {
		    values[i][j].setBounds(x, y, itemWidth, itemHeight);
		}
	    }
	}
    }

    @Override
    public Dimension minimumLayoutSize(Container container) {

	int width = 0;
	int height = 0;

	for (int i = 0; i < ROWS; ++i) {
	    for (int j = 0; j < COLUMNS; ++j) {
		if (values[i][j] == null)
		    continue;

		if (values[i][j].getMinimumSize() == null)
		    continue;

		width = Math.max(values[i][j].getMinimumSize().width, width);
		height = Math.max(values[i][j].getMinimumSize().height, height);

		if (i == 0 && j == 0) {
		    width -= 4 * space;
		    width /= 5;
		}
	    }
	}

	width = width * COLUMNS + (COLUMNS - 1) * space;
	height = height * ROWS + (ROWS - 1) * space;

	Insets insets = container.getInsets();
	width += insets.left + insets.right;
	height += insets.top + insets.bottom;
	return new Dimension(width, height);
    }

    @Override
    public Dimension preferredLayoutSize(Container container) {

	int width = 0;
	int height = 0;

	for (int i = 0; i < ROWS; ++i) {
	    for (int j = 0; j < COLUMNS; ++j) {
		if (values[i][j] == null)
		    continue;

		if (values[i][j].getPreferredSize() == null)
		    continue;

		width = Math.max(values[i][j].getPreferredSize().width, width);
		height = Math.max(values[i][j].getPreferredSize().height, height);
		if (i == 0 && j == 0) {
		    width -= 4 * space;
		    width /= 5;
		}

	    }
	}

	width = width * COLUMNS + (COLUMNS - 1) * space;
	height = height * ROWS + (ROWS - 1) * space;

	Insets insets = container.getInsets();
	width += insets.left + insets.right;
	height += insets.top + insets.bottom;

	return new Dimension(width, height);
    }

    @Override
    public void removeLayoutComponent(Component component) {
	for (int i = 0; i < ROWS; ++i) {
	    for (int j = 0; j < COLUMNS; ++j) {
		if (values[i][j].equals(component)) {
		    values[i][j] = null;
		    return;
		}
	    }
	}
    }

    @Override
    public void addLayoutComponent(Component component, Object _position) {
	Objects.requireNonNull(_position);
	Objects.requireNonNull(component);

	if (!(_position instanceof RCPosition)) {
	    throw new CalcLayoutException("Give me RCPosition.");
	}
	RCPosition position = (RCPosition) _position;
	checkRange(this.function, position);

	if (values[position.getRow() - 1][position.getColumn() - 1] != null) {
	    throw new CalcLayoutException("Place is already taken.");
	}

	values[position.getRow() - 1][position.getColumn() - 1] = component;
    }

    /**
     * Checks if the given position fits the given range. If does not fit in that
     * range, {@link CalcLayoutException} will be thrown will appropriate message.
     * 
     * @param function
     *            function which will check if the position is in the range
     * @param position
     *            position which will be checked
     * @throws CalcLayoutException
     *             position is not valid
     */
    private void checkRange(Predicate<RCPosition> function, RCPosition position) {
	Objects.requireNonNull(function);
	if (!function.test(position)) {
	    throw new CalcLayoutException("Positon " + position + " is not valid.");
	}
    }

    @Override
    public float getLayoutAlignmentX(Container arg0) {

	return 0.5f;
    }

    @Override
    public float getLayoutAlignmentY(Container arg0) {

	return 0.5f;
    }

    @Override
    public void invalidateLayout(Container arg0) {

    }

    @Override
    public Dimension maximumLayoutSize(Container container) {

	int width = 0;
	int height = 0;

	for (int i = 0; i < ROWS; ++i) {
	    for (int j = 0; j < COLUMNS; ++j) {
		if (values[i][j] == null)
		    continue;

		if (values[i][j].getMaximumSize() == null)
		    continue;

		width = Math.max(values[i][j].getMaximumSize().width, width);
		height = Math.max(values[i][j].getMaximumSize().height, height);

		if (i == 0 && j == 0) {
		    width -= 4 * space;
		    width /= 5;
		}
	    }
	}

	width = width * COLUMNS + (COLUMNS - 1) * space;
	height = height * ROWS + (ROWS - 1) * space;

	Insets insets = container.getInsets();
	width += insets.left + insets.right;
	height += insets.top + insets.bottom;
	return new Dimension(width, height);
    }

}
