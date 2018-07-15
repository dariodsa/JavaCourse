package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

/**
 * Class {@link PrimDemo} in their main method, which is only method of that
 * class, runs graphical thread and creates new Window which will create GUI
 * specified in the task 4.
 * 
 * @author dario
 *
 */
public class PrimDemo {
    /**
     * Main method which runs graphical thread which will be able to run GUI and set
     * everything up.
     * 
     * @param args
     *            not used
     */
    public static void main(String[] args) {
	SwingUtilities.invokeLater(Window::new);
    }

    /**
     * Class Window extends {@link JFrame} and sets up new graphical window with
     * specified graphical look.
     * 
     * @author dario
     *
     */
    public static class Window extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5088668362875885825L;
	/**
	 * default width
	 */
	private static int WIDTH = 400;
	/**
	 * default height
	 */
	private static int HEIGHT = 400;
	/**
	 * button next
	 */
	private JButton btnNext;
	/**
	 * left list
	 */
	private JList<Integer> leftList;
	/**
	 * right list
	 */
	private JList<Integer> rightList;
	/**
	 * model of prime numbers
	 */
	private PrimListModel model;

	/**
	 * Constructs window with specified size, sets visibility to true and default
	 * close operation to DISPOSE_ON_CLOSE.
	 */
	public Window() {
	    setSize(WIDTH, HEIGHT);
	    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	    setVisible(true);
	    initGUI();
	}

	/**
	 * Initialize graphical user interface, setting up button next to be at the
	 * bottom of the screen. Two JList split the rest of that equally setting their
	 * size equal.
	 */
	private void initGUI() {
	    setLayout(new BorderLayout());
	    Container cp = getContentPane();

	    btnNext = new JButton("sljedeći");
	    btnNext.addActionListener((e) -> btnNextCliked());

	    model = new PrimListModel();

	    leftList = new JList<>(model);
	    rightList = new JList<>(model);

	    cp.add(btnNext, BorderLayout.SOUTH);
	    JPanel panel = new JPanel(new GridLayout(1, 2));
	    panel.add(new JScrollPane(leftList));
	    panel.add(new JScrollPane(rightList));
	    cp.add(panel, BorderLayout.CENTER);
	}

	/**
	 * Method will call when user click on button next. It adds new number to model
	 * getting it from model.
	 */
	private void btnNextCliked() {
	    model.next();
	    return;
	}

    }
}
