package hr.fer.zemris.lsystems.impl;

import java.awt.Color;
import java.awt.geom.CubicCurve2D;

import hr.fer.zemris.java.custom.collections.Dictionary;
import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.commands.ColorCommand;
import hr.fer.zemris.lsystems.impl.commands.DrawCommand;
import hr.fer.zemris.lsystems.impl.commands.PopCommand;
import hr.fer.zemris.lsystems.impl.commands.PushCommand;
import hr.fer.zemris.lsystems.impl.commands.RotateCommand;
import hr.fer.zemris.lsystems.impl.commands.ScaleCommand;
import hr.fer.zemris.lsystems.impl.commands.SkipCommand;
import hr.fer.zemris.lsystems.impl.parser.Parser;
import hr.fer.zemris.lsystems.impl.parser.exceptions.ParserException;
import hr.fer.zemris.math.Vector2D;

public class LSystemBuilderImpl implements LSystemBuilder {

	/**
	 * Dictionary in which we will insert all commands.
	 */
	private Dictionary registerCommand;
	/**
	 * Dictionary in which we will insert all productions.
	 */
	private Dictionary registerProduction;
	/**
	 * Initial turtle state
	 */
	TurtleState turtleState;
	/**
	 * unit length of the turtle state
	 */
	private double unitLength = 0.1;
	/**
	 * unit length degree scaler of the turtle state
	 */
	private double unitLengthDegreeScaler = 1;
	/**
	 * origin of the turtle state
	 */
	private Vector2D origin = new Vector2D(0, 0);
	/**
	 * angle of the turtle state
	 */
	private double angle = 0;
	/**
	 * axiom, or level 0 commands
	 */
	private String axiom = "";

	/**
	 * Sets up the {@link LSystemBuilderImpl}, with the init {@link TurtleState} and
	 * configured two dictionaries register commands and register productions.
	 */
	public LSystemBuilderImpl() {
		registerCommand = new Dictionary();
		registerProduction = new Dictionary();
		initTurtle();
	}

	/**
	 * Sets up unitLength.
	 * 
	 * @param unitLength
	 * @return {@link LSystemBuilder} this same object with set up attributes
	 */
	@Override
	public LSystemBuilder setUnitLength(double unitLength) {
		turtleState.setStep(unitLength);
		this.unitLength = unitLength;
		return this;
	}

	/**
	 * Sets the init position of the {@link TurtleState}.
	 * 
	 * @param x
	 *            coordinate of the position
	 * @param y
	 *            coordinate of the position
	 * @return {@link LSystemBuilder} this same object with set up attributes
	 */
	@Override
	public LSystemBuilder setOrigin(double x, double y) {
		turtleState.setPosition(new Vector2D(x, y));
		origin = new Vector2D(x, y);
		System.out.println(x + " " + y + " " + origin.getX() + " " + origin.getY());
		return this;
	}

	/**
	 * Sets up the init angle of the {@link TurtleState}.
	 * 
	 * @param angle
	 * @return {@link LSystemBuilder} this same object with set up attributes
	 */
	@Override
	public LSystemBuilder setAngle(double angle) {
		this.angle = angle;
		turtleState.getDirection().rotate(angle);
		return this;
	}

	/**
	 * Sets the axiom, init commands on the level 0.
	 * 
	 * @param axiom
	 *            new axiom
	 * @return {@link LSystemBuilder} this same object with set up attributes
	 */
	@Override
	public LSystemBuilder setAxiom(String axiom) {
		this.axiom = new String(axiom);
		return this;
	}

	/**
	 * Sets the unitLengthDegreeScaler.
	 * 
	 * @param unitLengthDegreeScaler
	 * @return {@link LSystemBuilder} this same object with set up attributes
	 */
	@Override
	public LSystemBuilder setUnitLengthDegreeScaler(double unitLengthDegreeScaler) {
		this.unitLengthDegreeScaler = unitLengthDegreeScaler;
		return this;
	}

	/**
	 * Adds new production with the symbol as the key, and string, production as the
	 * value.
	 * 
	 * @param symbol
	 *            key value of that pair
	 * @param production
	 *            list of the commands
	 * @return {@link LSystemBuilder} this same object with set up attributes
	 */
	@Override
	public LSystemBuilder registerProduction(char symbol, String production) {
		registerProduction.put(Character.valueOf(symbol), production);
		return this;

	}

	/**
	 * Adds new command with the symbol as the key, and {@link Command} as the
	 * value.
	 * 
	 * @param symbol
	 *            key of the value
	 * @param action
	 *            string of action which will be transformed in {@link Command}
	 * @return {@link LSystemBuilder} this same object with set up attributes
	 */
	@Override
	public LSystemBuilder registerCommand(char symbol, String action) {
		Command command = generateCommand(action);
		registerCommand.put(Character.valueOf(symbol), command);
		return this;
	}

	/**
	 * It configures attributes from the given String[] lines.
	 * 
	 * @param line
	 *            from which we will parse attributes
	 * @return {@link LSystemBuilder} with init attributes
	 */
	@Override
	public LSystemBuilder configureFromText(String[] lines) {
		Parser parser = new Parser(lines, this);
		try {
			parser.parseMe();
		} catch (ParserException ex) {
			System.out.println(ex.getClass().getCanonicalName() + " " + ex.getMessage());
		}
		return this;
	}

	/**
	 * Returns the new instance of the {@link SystemDrawer}
	 */
	@Override
	public LSystem build() {

		return new SystemDrawer();
	}

	/**
	 * {@link SystemDrawer} class which will be returned in the method
	 * {@link LSystemBuilderImpl#build()}
	 * 
	 * @author dario
	 *
	 */
	class SystemDrawer implements LSystem {

		private Context context;

		/**
		 * Draws the commands which was given by the {@link #generate(int)} , by the
		 * calling {@link Painter}.
		 */
		@Override
		public void draw(int level, Painter painter) {
			context = new Context();
			initTurtle();
			context.pushState(turtleState);

			turtleState.setStep(unitLength * Math.pow(unitLengthDegreeScaler, level));
			String result = generate(level);
			for (int i = 0, len = result.length(); i < len; ++i) {
				Command command = (Command) registerCommand.get(Character.valueOf(result.charAt(i)));
				if (command != null) {
					command.execute(context, painter);
				} else {
					// System.out.println(result.charAt(i));
				}
			}
		}

		/**
		 * It generates String, or list of commands, on the given level.
		 * 
		 * @param maxLevel
		 *            on which level we will create productions of commands
		 * @return String given result
		 */
		@Override
		public String generate(int maxLevel) {

			StringBuilder result = new StringBuilder(axiom);
			StringBuilder temp = new StringBuilder();
			for (int level = 0; level < maxLevel; ++level) {
				String values = result.toString();
				for (int i = 0, len = values.length(); i < len; ++i) {
					Object value = registerProduction.get(Character.valueOf(values.charAt(i)));
					if (value != null) {
						temp.append((String) value);
					} else {
						temp.append(values.charAt(i));
					}
				}
				result = new StringBuilder(temp.toString());
				temp = new StringBuilder();
			}
			return result.toString();
		}
	}

	/**
	 * Returns the current turtle state.
	 * 
	 * @return current {@link TurtleState}
	 */
	public TurtleState getTurtleState() {
		return this.turtleState;
	}

	/**
	 * Skips blanks in the given string and returns the position of the first non
	 * blank char.
	 * 
	 * @param data
	 *            string in which we will look non blank char
	 * @param position
	 *            from which we will search non empty char
	 * @return position of the non empty char from position
	 */
	private int skipBlanks(String data, int position) {
		for (int len = data.length(); position < len; ++position) {
			if (data.charAt(position) == '\n' || data.charAt(position) == '\t' || data.charAt(position) == '\r'
					|| data.charAt(position) == ' ')
				continue;
			else
				return position;
		}
		return position;
	}

	/**
	 * Returns {@link Command} which will be created from the string action.
	 * 
	 * @param action
	 *            string which will be looked at
	 * @return {@link Command} formed from string
	 */
	private Command generateCommand(String action) {
		Command command = null;
		if (action.startsWith("draw ")) {
			Double move = Double.parseDouble(getNext(action, 2));
			command = new DrawCommand(move);
		} else if (action.startsWith("skip ")) {
			Double move = Double.parseDouble(getNext(action, 2));
			command = new SkipCommand(move);
		} else if (action.startsWith("scale ")) {
			Double scale = Double.parseDouble(getNext(action, 2));
			command = new ScaleCommand(scale);
		} else if (action.startsWith("rotate ")) {
			Double angle = Double.parseDouble(getNext(action, 2));
			command = new RotateCommand(angle);
		} else if (action.startsWith("push")) {
			command = new PushCommand();
		} else if (action.startsWith("pop")) {
			command = new PopCommand();
		} else if (action.startsWith("color ")) {
			Color color = Color.decode("#" + getNext(action, 2));
			command = new ColorCommand(color);
		} else
			throw new IllegalArgumentException(String.format("%s.%n Unknown command.", action));

		return command;
	}

	/**
	 * Returns the i-th string in the string action. String separator is blank char.
	 * 
	 * @param action
	 *            string which will be splited by blank
	 * @param i
	 *            - ith string
	 * @return ith string in the string action
	 */
	private String getNext(String action, int i) {
		StringBuilder data = new StringBuilder();
		int pos = skipBlanks(action, 0);
		for (int k = 0; k < i; ++k) {
			if (pos >= action.length())
				break;
			while (nonBlank(action.charAt(pos))) {
				if (k + 1 == i) {
					data.append(action.charAt(pos));
				}
				++pos;
				if (pos == action.length())
					break;
			}
			pos = skipBlanks(action, pos);
		}

		return data.toString();
	}

	/**
	 * Returns true if the character is non blank character. Non blank character is
	 * '\n', '\r', '\t' and space.
	 * 
	 * @param character
	 *            which will be tested
	 * @return true if the given character is non blank, false otherwise
	 */
	private boolean nonBlank(char character) {
		return character != '\n' && character != ' ' && character != '\r' && character != '\t';
	}

	/**
	 * Inits the turtle state, which was set at the beginning of the process.
	 */
	private void initTurtle() {
		turtleState = new TurtleState();
		turtleState.setColor(Color.BLACK);
		turtleState.setDirection(new Vector2D(1, 0));
		turtleState.getDirection().rotate(angle);
		turtleState.setPosition(origin.copy());
		turtleState.setStep(0.1);
	}
}
