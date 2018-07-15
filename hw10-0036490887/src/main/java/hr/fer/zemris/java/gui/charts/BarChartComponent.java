package hr.fer.zemris.java.gui.charts;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.util.List;
import java.util.Objects;
import java.util.OptionalInt;

import javax.swing.JComponent;

/**
 * Class {@link BarChartComponent} has responsibility of drawing everything on
 * the graphical variable. It accepts {@link BarChart} in the constructor from
 * where it will read values, minimal value of y, maximal value of y, step
 * between and titles of both axis.
 * 
 * @author dario
 *
 */
public class BarChartComponent extends JComponent {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * bar chart
     */
    private BarChart barChart;
    /*
     * border size from each component
     */
    private int BORDERSIZE = 15;
    /**
     * height of draw surface
     */
    private int height;
    /**
     * width of draw surface
     */
    private int width;
    /**
     * minimal x of all values
     */
    private int minX;
    /**
     * minimal y of all values
     */
    private int minY;
    /**
     * maximal x of all values
     */
    private int maxX;
    /**
     * maximal y of all values
     */
    private int maxY;
    /**
     * x coordinate of intersection of x and y axis
     */
    private int xBegin;
    /**
     * y coordinate of intersection of x and y axis
     */
    private int yBegin;

    /**
     * Constructs {@link BarChartComponent} with {@link BarChart} as parameter.
     * Minimal and maximal value of x component as well as y is taken 
     * from bar chart and written in this object as private attributes. 
     * 
     * @param barChart
     *            needed to know all bounds and values
     * @throws NullPointerException barchart musn't be null
     */
    public BarChartComponent(BarChart barChart) {

	Objects.requireNonNull(barChart);
	
	this.barChart = barChart;
	OptionalInt _xmin = barChart.getList().stream().mapToInt(XYValue::getX).min();
	OptionalInt _xmax = barChart.getList().stream().mapToInt(XYValue::getX).max();
	if (!_xmax.isPresent() || !_xmin.isPresent())
	    return;
	this.minX = _xmin.getAsInt();
	this.minY = barChart.getMinY();
	this.maxX = _xmax.getAsInt();
	this.maxY = barChart.getMaxY();

	while (Math.abs(maxY - minY) % barChart.getStepY() != 0) {
	    maxY++;
	}

    }

    @Override
    public void paint(Graphics g) {
	Graphics2D g2D = (Graphics2D) g;
	AffineTransform defaultAt = g2D.getTransform();
	Rectangle r = g.getClip().getBounds();
	this.width = r.y + r.width;
	this.height = r.x + r.height;

	int heightSize = createYAxis(g2D, BORDERSIZE + g2D.getFontMetrics().getHeight() + BORDERSIZE, BORDERSIZE,
		height - 2 * g2D.getFontMetrics().getHeight() - 2 * BORDERSIZE);

	int widthSize = createXAxis(g2D, xBegin, width - BORDERSIZE);

	setValues(g2D, widthSize, heightSize, xBegin, yBegin, minX, minY);

	g2D.setColor(Color.BLACK);
	FontMetrics fm = g.getFontMetrics();
	int w1 = fm.stringWidth(barChart.getXAxisTitle());

	g2D.drawString(barChart.getXAxisTitle(), (width - BORDERSIZE + xBegin) / 2 - w1 / 2, height - BORDERSIZE);

	AffineTransform at = AffineTransform.getQuadrantRotateInstance(-1);

	int w2 = fm.stringWidth(barChart.getYAxisTitle());

	g2D.setTransform(at);
	g2D.drawString(barChart.getYAxisTitle(), -((BORDERSIZE + yBegin) / 2 + w2 / 2), BORDERSIZE);
	g2D.setTransform(defaultAt);

	addFileName(g2D);
    }

    /**
     * Creates orange bars which indicate how values read from the data. X value is on 
     * the x axis and y value is on the y axis. 
     * @param g2D {@link Graphics2D} on which bars will be drawn
     * @param width width of a one bar
     * @param height height of a bar with value 1
     * @param xBegin x coordinate in pixel, which represents intersection of axis, or there local (0,0)   
     * @param yBegin y coordinate in pixel, which represents intersection of axis, or there local (0,0)
     * @param minX minimal values on x axis
     * @param minY minimal values on y axis
     */
    private void setValues(Graphics2D g2D, int width, int height, int xBegin, int yBegin, int minX, int minY) {
	List<XYValue> list = barChart.getList();

	g2D.setColor(Color.ORANGE);

	for (XYValue value : list) {
	    int x = Math.abs(value.getX() - minX) * width + xBegin;
	    int y = yBegin - Math.abs(value.getY() - minY) * height;

	    g2D.fillRect(x + 2, y, width - 2, height * Math.abs(value.getY() - minY));
	}
    }

    /**
     * Creates x axis with minimal value and maximal value and as well all inside
     * that interval with step 1.
     * 
     * @param g2D
     *            {@link Graphics2D} in which axis will be drawn
     * @param xMin
     *            minimal x component in pixel on which axis will be drawn
     * @param xMax
     *            maximal x component in pixel on which axis will be drawn
     * @return width of a one value
     */
    private int createXAxis(Graphics2D g2D, int xMin, int xMax) {

	int num = Math.abs(maxX - minX) + 1;
	int step = Math.abs(xMax - xMin + 1) / num;
	int xComp = xMin;

	for (int xVal = minX; xVal <= maxX; ++xVal) {
	    String str = String.valueOf(xVal);
	    int oldValue = xComp;
	    int newValue = xComp + step;

	    g2D.setColor(Color.LIGHT_GRAY);
	    g2D.drawLine(newValue, yBegin + 5, newValue, BORDERSIZE);
	    g2D.setColor(Color.BLACK);
	    int xCompCenter = (newValue + oldValue) / 2 + g2D.getFontMetrics().stringWidth(str) / 2;

	    g2D.drawString(str, xCompCenter, yBegin + g2D.getFontMetrics().getHeight());
	    xComp += step;
	}

	g2D.fillPolygon(new int[] { width - BORDERSIZE + 2, width - BORDERSIZE + 2, width - BORDERSIZE + 8 },
		new int[] { yBegin - 4, yBegin + 4, yBegin }, 3);

	return step;
    }

    /**
     * Method creates y axis with some specifications, like what is minimal and
     * maximal value, what step should be and sizes in pixel.
     * 
     * @param g2D
     *            {@link Graphics2D} on which axis will be drawn
     * @param x
     *            x component in pixel , on which axis will be drawn
     * @param Ymin
     *            minimal y component in pixel
     * @param Ymax
     *            maximal y component in pixel
     * @return height of a one value in chart
     */
    private int createYAxis(Graphics2D g2D, int x, int Ymin, int Ymax) {
	int stepY = barChart.getStepY();

	int stepPixel = Math.abs(Ymax - Ymin) / Math.abs(maxY - minY);

	int firstY = 0;
	int lastY = 0;
	int currentValue = minY;
	while (true) {
	    if (currentValue > maxY) {
		currentValue = maxY;
	    }
	    String str = String.valueOf(currentValue);

	    int w = g2D.getFontMetrics().stringWidth(str);
	    int h = g2D.getFontMetrics().getHeight();

	    int offset = Math.abs(currentValue - minY);
	    int currY = Ymax - offset * stepPixel;

	    g2D.drawString(str, x - w + 5, currY + h);
	    g2D.setColor(Color.LIGHT_GRAY);
	    g2D.drawLine(x + 7, currY + h / 2 + 2, width - BORDERSIZE, currY + h / 2 + 2);
	    g2D.setColor(Color.BLACK);
	    lastY = currY + h / 2 + 2;

	    if (currentValue == minY) {
		firstY = currY + h / 2 + 2;
		xBegin = x + 11;
		yBegin = lastY;
	    }

	    if (currentValue == maxY)
		break;
	    currentValue += stepY;
	}
	g2D.fillPolygon(new int[] { xBegin, xBegin - 4, xBegin + 4 }, new int[] { Ymin - 8, Ymin, Ymin }, 3);

	g2D.drawLine(xBegin, yBegin, xBegin, Ymin);
	return Math.abs(firstY - lastY) / Math.abs(maxY - minY);
    }

    /**
     * Adds file name from which data was parsed to the top of the
     * screen center.
     * 
     * @param g2D
     *            {@link Graphics2D} on which string will be drawn
     */
    private void addFileName(Graphics2D g2D) {
	String text = barChart.getFileName();
	int widthOfText = g2D.getFontMetrics().stringWidth(text);
	int x = width/2 - widthOfText/2;
	int y = g2D.getFontMetrics().getHeight();
	g2D.drawString(text, x, y);
    }
}
