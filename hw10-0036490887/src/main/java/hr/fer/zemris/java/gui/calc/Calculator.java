package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.Container;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Stack;
import java.util.function.DoubleBinaryOperator;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

/**
 * Class {@link Calculator} is class which runs task 2 from homework. It is
 * responsible for creating a window and putting all necessary buttons and
 * labels inside. It gives user to run basic mathematical operations such as
 * addition, subtraction, division, trigonometric function, logarithmic
 * function. It offers also inverse function of function which have that form.
 * It supports saving the results on the stack and popping it on the screen.
 * 
 * @author dario
 *
 */
public class Calculator {
    /**
     * Creates new graphical thread which will create new window.
     * 
     * @param args
     *            not used
     */
    public static void main(String[] args) {
	SwingUtilities.invokeLater(Window::new);
    }

    /**
     * Public static class {@link Window} which extends {@link JFrame} and arranges
     * and sets up all graphical things. It sets window title, size, layout and adds
     * all component to the window's content pane.
     * 
     * @author dario
     *
     */
    public static class Window extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * title of a window
	 */
	private static final String TITLE = "Kalkulator, task 2";
	/**
	 * name of the button reciprocal value
	 */
	private static final String RECP = "1/x";
	/**
	 * name of the button log
	 */
	private static final String LOG = "log";
	/**
	 * name of the button natural log
	 */
	private static final String LN = "ln";
	/**
	 * name of the button power
	 */
	private static final String POWER = "x^n";
	/**
	 * name of the button sin
	 */
	private static final String SIN = "sin";
	/**
	 * name of the button cos
	 */
	private static final String COS = "cos";
	/**
	 * name of the button tan
	 */
	private static final String TAN = "tan";
	/**
	 * name of the button ctg
	 */
	private static final String CTG = "ctg";
	/**
	 * name of the button equal
	 */
	private static final String EQUAL = "=";
	/**
	 * name of the button div
	 */
	private static final String DIV = "/";
	/**
	 * name of the button mul
	 */
	private static final String MUL = "*";
	/**
	 * name of the button add
	 */
	private static final String ADD = "+";
	/**
	 * name of the button sub
	 */
	private static final String SUB = "-";
	/**
	 * name of the button inv
	 */
	private static final String INV = "Inv";
	/**
	 * name of the button push
	 */
	private static final String PUSH = "Push";
	/**
	 * name of the button pop
	 */
	private static final String POP = "Pop";
	/**
	 * name of the button result
	 */
	private static final String RES = "Res";
	/**
	 * name of the button clear
	 */
	private static final String CLR = "clr";
	/**
	 * unary function which are currently using , see {@link NormalFunctions} and
	 * {@link InverseFunctions}
	 */
	private Functions function;
	/**
	 * list of observers on changing the inverse function
	 */
	private List<FunctionListeners> observers;
	/**
	 * private stack, used in PUSH and POP commands in Calculator
	 */
	private Stack<Double> stack = new Stack<>();
	/**
	 * JCalcLabel, the result of calculation will be written on it
	 */
	private JCalcLabel result;
	/**
	 * model of a calculator
	 */
	private CalcModel model = new CalcModeImpl();

	/**
	 * Default constructor, which responsibility is to set up a title, set size of
	 * window and to call private initGUI method.
	 */
	public Window() {
	    super(TITLE);
	    this.observers = new ArrayList<>();
	    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	    // setSize(600, 600);
	    setVisible(true);
	    initGUI();
	}

	/**
	 * Initialize all graphical components and adds it to content pane.
	 */
	private void initGUI() {
	    setLayout(new CalcLayout(20));
	    Container cp = getContentPane();

	    this.result = new JCalcLabel("");
	    result.setHorizontalAlignment(SwingConstants.RIGHT);
	    result.setBorder(BorderFactory.createLineBorder(Color.black));
	    model.addCalcValueListener(result);

	    cp.add(result, new RCPosition(1, 1));
	    function = new NormalFunctions();
	    initNumbers();
	    initFunctions();
	    pack();
	}

	private void initFunctions() {

	    JButton oneDiviX = new UnaryOpButton(RECP, "reciprocalValue", model, this);
	    JButton log = new UnaryOpButton(LOG, "log", model, this);
	    JButton ln = new UnaryOpButton(LN, "ln", model, this);
	    JButton sin = new UnaryOpButton(SIN, "sin", model, this);
	    JButton cos = new UnaryOpButton(COS, "cos", model, this);
	    JButton tan = new UnaryOpButton(TAN, "tan", model, this);
	    JButton ctg = new UnaryOpButton(CTG, "ctg", model, this);

	    JButton add = new BinaryOpButton(ADD, (x, y) -> x + y, model);
	    JButton mul = new BinaryOpButton(MUL, (x, y) -> x * y, model);
	    JButton sub = new BinaryOpButton(SUB, (x, y) -> x - y, model);
	    JButton div = new BinaryOpButton(DIV, (x, y) -> x / y, model);
	    JButton power = new BinaryOpButton(POWER, (x, y) -> function.power(x, y), model);

	    JCheckBox inv = new JCheckBox(INV);
	    inv.addActionListener((e) -> {

		if (inv.isSelected()) {
		    function = new InverseFunctions();
		} else {
		    function = new NormalFunctions();
		}
		for (FunctionListeners l : observers) {
		    l.newState(function);
		}
	    });
	    JButton push = new JButton(PUSH);
	    push.addActionListener((e) -> {
		stack.push(model.getValue());
	    });
	    JButton pop = new JButton(POP);
	    pop.addActionListener((e) -> {
		if (stack.isEmpty()) {
		    JOptionPane.showMessageDialog(this, "Stog je prazan.", "Stog", JOptionPane.ERROR_MESSAGE);
		    return;
		}
		model.setValue(stack.pop());
	    });
	    JButton clr = new JButton(CLR);
	    clr.addActionListener((e) -> model.clear());
	    JButton res = new JButton(RES);
	    res.addActionListener((e) -> {
		model.clearAll();
	    });
	    JButton equal = new JButton(EQUAL);
	    equal.addActionListener((e) -> {
		double result = model.getPendingBinaryOperation().applyAsDouble(model.getActiveOperand(),
			model.getValue());
		try {
		    model.setValue(result);
		} catch(IllegalArgumentException ex) {
		    JOptionPane.showMessageDialog(this, "Ne znam provesti operacije nad velikim brojevima.", "Veliki brojevi", JOptionPane.ERROR_MESSAGE);
		}
	    });

	    Container cp = getContentPane();

	    cp.add(oneDiviX, new RCPosition(2, 1));
	    cp.add(log, new RCPosition(3, 1));
	    cp.add(ln, new RCPosition(4, 1));
	    cp.add(power, new RCPosition(5, 1));
	    cp.add(sin, new RCPosition(2, 2));
	    cp.add(cos, new RCPosition(3, 2));
	    cp.add(tan, new RCPosition(4, 2));
	    cp.add(ctg, new RCPosition(5, 2));

	    cp.add(add, new RCPosition(5, 6));
	    cp.add(sub, new RCPosition(4, 6));
	    cp.add(mul, new RCPosition(3, 6));
	    cp.add(div, new RCPosition(2, 6));
	    cp.add(inv, new RCPosition(5, 7));

	    cp.add(push, new RCPosition(3, 7));
	    cp.add(pop, new RCPosition(4, 7));
	    cp.add(clr, new RCPosition(1, 7));
	    cp.add(equal, new RCPosition(1, 6));
	    cp.add(res, new RCPosition(2, 7));
	}

	/**
	 * Returns {@link Functions} which will be called in {@link UnaryOpButton}. It
	 * can return {@link NormalFunctions} or {@link InverseFunctions}.
	 * 
	 * @return current functions based on inverse button
	 */
	public Functions getFunction() {
	    return this.function;
	}

	/**
	 * Adds new {@link FunctionListeners} which observes all changes on button
	 * inverse.
	 * 
	 * @param l
	 *            {@link FunctionListeners} listener
	 */
	public void addListener(FunctionListeners l) {
	    observers.add(l);
	}

	/**
	 * Creates all buttons with numbers and sets it to the right position.
	 */
	private void initNumbers() {
	    Container cp = getContentPane();
	    JButton[] numbers = new JButton[10];
	    for (int i = 0; i < 10; ++i) {
		numbers[i] = new JButton(java.lang.Character.valueOf((char) (i + '0')).toString());
		RCPosition position = getNumberPositon(i);
		cp.add(numbers[i], position);
		int br = i;
		numbers[i].addActionListener((e) -> {
		    model.insertDigit(br);
		});
	    }
	    JButton dot = new JButton(".");
	    dot.addActionListener((e) -> {
		model.insertDecimalPoint();
	    });
	    cp.add(dot, new RCPosition(5, 5));

	    JButton plusMinus = new JButton("+/-");
	    plusMinus.addActionListener((e) -> {
		model.swapSign();
	    });

	    cp.add(plusMinus, new RCPosition(5, 4));
	}

	/**
	 * Returns correct {@link RCPosition} based on a question where should button
	 * with given number go. It will return null if the number is not in the
	 * specific range. Range is from 0 to 9 inclusive ( numbers on the calculator).
	 * 
	 * @param i
	 *            number
	 * @return {@link RCPosition} of a number
	 */
	private RCPosition getNumberPositon(int i) {
	    RCPosition position = null;
	    switch (i) {
	    case 0:
		position = new RCPosition(5, 3);
		break;
	    case 1:
		position = new RCPosition(4, 3);
		break;
	    case 2:
		position = new RCPosition(4, 4);
		break;
	    case 3:
		position = new RCPosition(4, 5);
		break;
	    case 4:
		position = new RCPosition(3, 3);
		break;
	    case 5:
		position = new RCPosition(3, 4);
		break;
	    case 6:
		position = new RCPosition(3, 5);
		break;
	    case 7:
		position = new RCPosition(2, 3);
		break;
	    case 8:
		position = new RCPosition(2, 4);
		break;
	    case 9:
		position = new RCPosition(2, 5);
		break;
	    default:
		break;

	    }
	    return position;
	}

    }

    /**
     * Class {@link JCalcLabel} implements {@link CalcValueListener} and extends
     * {@link JLabel}. It listens a changes of a calculator value and produce that
     * value in text so that is why {@link JLabel} and {@link CalcValueListener} are
     * in the game.
     * 
     * @author dario
     */
    public static class JCalcLabel extends JLabel implements CalcValueListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Construct {@link JCalcLabel} and set text.
	 * 
	 * @param text
	 *            text value of {@link JCalcLabel}
	 */
	public JCalcLabel(String text) {
	    super(text);
	}

	@Override
	public void valueChanged(CalcModel model) {
	    this.setText(model.toString());
	}

    }

    /**
     * Class {@link BinaryOpButton} extends JButton which will run specific
     * {@link DoubleBinaryOperator} on click action and it updates new value in
     * calcModel.
     * 
     * @author dario
     *
     */
    public static class BinaryOpButton extends JButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs {@link BinaryOpButton} with specific attributes.
	 * 
	 * @param title
	 *            title of a button
	 * @param function
	 *            function which will be run on click
	 * @param model
	 *            model of a calculator
	 */
	public BinaryOpButton(String title, DoubleBinaryOperator function, CalcModel model) {
	    super(title);

	    Objects.requireNonNull(function);
	    Objects.requireNonNull(model);

	    addActionListener((e) -> {

		try {

		    model.setValue(model.getPendingBinaryOperation().applyAsDouble(model.getActiveOperand(),
			    model.getValue()));
		} catch (Exception ignorable) {
		}
		model.setValue(model.getValue());
		model.setActiveOperand(model.getValue());
		model.setPendingBinaryOperation(function);
		// model.clear();

	    });
	}
    }

    /**
     * Class {@link UnaryOpButton} extends JButton and implements
     * {@link FunctionListeners}. It will run specific method when the user click on
     * it. It says that it will listen to changes in the inverse button.
     * 
     * @author dario
     *
     */
    public static class UnaryOpButton extends JButton implements FunctionListeners {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * function which will be ran
	 */
	private Functions function;
	/**
	 * method name which will be ran
	 */
	private String methodName;
	/**
	 * calcModel, important for updating result
	 */
	private CalcModel calcModel;

	/**
	 * Constructs {@link UnaryOpButton} with specific parameters.
	 * 
	 * @param title
	 *            title of a button
	 * @param methodName
	 *            method name which will be run on click
	 * @param calcModel
	 *            model of calculator
	 * @param calculator
	 *            reference on a {@link Window}
	 */
	public UnaryOpButton(String title, String methodName, CalcModel calcModel, Window calculator) {
	    super(title);

	    Objects.requireNonNull(methodName);
	    Objects.requireNonNull(calcModel);
	    Objects.requireNonNull(calculator);

	    calculator.addListener(this);
	    function = calculator.getFunction();
	    this.methodName = methodName;
	    this.calcModel = calcModel;
	    addActionListener((e) -> btnClicked());

	}

	/**
	 * Runs method name when the user clicks on a this button.
	 */
	private void btnClicked() {
	    try {
		Method method = Functions.class.getDeclaredMethod(methodName, double.class);
		Object result = method.invoke(function, calcModel.getValue());
		calcModel.setValue((double) result);
	    } catch(IllegalArgumentException ex) {
		    JOptionPane.showMessageDialog(this, "Ne znam provesti operacije nad velikim brojevima.", "Veliki brojevi", JOptionPane.ERROR_MESSAGE);
	    } catch (Exception ex) {
		ex.printStackTrace();
		System.err.println(ex.getMessage());
	    }
	}

	@Override
	public void newState(Functions function) {
	    this.function = function;
	}
    }
}
