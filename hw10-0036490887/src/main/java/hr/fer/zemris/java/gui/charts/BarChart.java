package hr.fer.zemris.java.gui.charts;

import java.util.List;

/**
 * Class {@link BarChart} encapsulate all necessary data needed for plot, or bar
 * chart to be plotted. It's constructor must have {@link XYValue} values which
 * will be on plot, titles of both axis and minimal and maximal value on y axis
 * so as space between two consecutive values.
 * 
 * @author dario
 *
 */
public class BarChart {

    /**
     * list of values
     */
    private List<XYValue> list;
    /**
     * title of x axis
     */
    private String xAxisTitle;
    /**
     * title of y axis
     */
    private String yAxisTitle;
    /**
     * minimal value on y axis
     */
    private int minY;
    /**
     * maximal value on y axis
     */
    private int maxY;
    /**
     * space between two components on y axis
     */
    private int spaceY;
    /**
     * file name from values were loaded
     */
    private String fileName;

    /**
     * Constructs {@link BarChart} with list of values, title of x and y axis, y
     * axis minimal, maximal value and space on them.
     * 
     * @param fileName
     *            file name of file from which all parameters were loaded
     * @param list
     *            list of values which will be plotted
     * @param xAxisTitle
     *            x axis title
     * @param yAxisTitle
     *            y axis title
     * @param minY
     *            minimal value on y axis
     * @param maxY
     *            maximal value on y axis
     * @param spaceY
     *            space between two consecutive values on y axis
     */
    public BarChart(String fileName, List<XYValue> list, String xAxisTitle, String yAxisTitle, int minY, int maxY,
	    int spaceY) {
	this.fileName = fileName;
	this.list = list;
	this.xAxisTitle = xAxisTitle;
	this.yAxisTitle = yAxisTitle;
	this.minY = minY;
	this.maxY = maxY;
	this.spaceY = spaceY;
    }

    /**
     * Returns list of values which will be plotted on the bar chart.
     * 
     * @return list of values.
     */
    public List<XYValue> getList() {
	return list;
    }

    /**
     * Returns title of the x axis.
     * 
     * @return title of x axis
     */
    public String getXAxisTitle() {
	return xAxisTitle;
    }

    /**
     * Returns title of the y axis.
     * 
     * @return title of y axis
     */
    public String getYAxisTitle() {
	return yAxisTitle;
    }

    /**
     * Returns minimal value on the y axis.
     * 
     * @return y axis's minimal value
     */
    public int getMinY() {
	return minY;
    }

    /**
     * Returns maximal value on the y axis.
     * 
     * @return y axis's maximal value
     */
    public int getMaxY() {
	return maxY;
    }

    /**
     * Returns space between two consecutive values on the y axis
     * 
     * @return space on the y axis
     */
    public int getStepY() {
	return spaceY;
    }

    /**
     * Returns name of the file from which the files were loaded
     * 
     * @return name of the file from which the files were loaded
     */
    public String getFileName() {
	return this.fileName;
    }

}
