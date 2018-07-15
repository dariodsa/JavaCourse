package hr.fer.zemris.java.gui.charts;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * Class {@link BarChartDemo} runs task specified in the task 3. Class
 * {@link BarChartDemo} extends JFrame so it arrange components in it's
 * container. In the constructor it will prepare all components to be ready for
 * showing. It creates new instance of {@link BarChartDemo} in main method.
 * 
 * @author dario
 *
 */
public class BarChartDemo extends JFrame {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /**
     * welcome message
     */
    private static final String WELCOME_MESSAGE = "Welcome to BarChartDemo.\n" 
	    					+ "Please enter path to input location: ";

    /**
     * Constructs {@link BarChartDemo} with {@link BarChartComponent} as parameter.
     * That component is important for plotting all values and other things that
     * goes with.
     * 
     * @param component
     *            one and only component of content pane, responsible of showing the
     *            bar chart
     */
    public BarChartDemo(BarChartComponent component) {
	setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	setVisible(true);
	setSize(800, 500);
	getContentPane().add(component);

    }

    /**
     * Runs task specified in the task 3. It asks the user path to the file from
     * STDIN and then plot the data on the screen.
     * 
     * @param args
     *            not used
     */
    public static void main(String[] args) {

	Scanner scanner = new Scanner(System.in);
	System.out.print(WELCOME_MESSAGE);
	Path path = Paths.get(scanner.nextLine());
	scanner.close();

	try {
	    List<String> lines = Files.readAllLines(path);
	    String xAxisTitle = lines.get(0);
	    String yAxisTitle = lines.get(1);
	    List<XYValue> values = addValues(lines.get(2));
	    int minY = Integer.parseInt(lines.get(3));
	    int maxY = Integer.parseInt(lines.get(4));
	    int stepY = Integer.parseInt(lines.get(5));
	    BarChart barChart = new BarChart(path.getFileName().toString(), values, xAxisTitle, yAxisTitle, minY, maxY, stepY);
	    BarChartComponent component = new BarChartComponent(barChart);
	    SwingUtilities.invokeLater(() -> new BarChartDemo(component));

	} catch (IOException | IndexOutOfBoundsException | IllegalArgumentException e) {
	    System.out.println("Parsing file " + path.getFileName() + " failed.");
	}
    }

    /**
     * Returns list of {@link XYValue} values which are parsed from string line
     * which was given as the parameter.
     * 
     * @param line
     *            string from which parsing will be done
     * @return list of {@link XYValue} parsed from string
     * @throws IllegalArgumentException
     *             parsing error
     */
    private static List<XYValue> addValues(String line) {
	Objects.requireNonNull(line);

	List<XYValue> result = new ArrayList<>();
	String[] splited = line.split(" +");

	for (String value : splited) {
	    String[] values = value.split(",");

	    if (values.length != 2) {
		throw new IllegalArgumentException("Parsing error.");
	    }

	    result.add(new XYValue(Integer.parseInt(values[0]), Integer.parseInt(values[1])));
	}
	return result;
    }
}
