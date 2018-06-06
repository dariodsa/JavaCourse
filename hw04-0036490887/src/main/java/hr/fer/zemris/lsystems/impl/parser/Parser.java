package hr.fer.zemris.lsystems.impl.parser;

import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;
import hr.fer.zemris.lsystems.impl.parser.exceptions.AngleException;
import hr.fer.zemris.lsystems.impl.parser.exceptions.AxiomException;
import hr.fer.zemris.lsystems.impl.parser.exceptions.CommandException;
import hr.fer.zemris.lsystems.impl.parser.exceptions.OriginException;
import hr.fer.zemris.lsystems.impl.parser.exceptions.ProductionException;
import hr.fer.zemris.lsystems.impl.parser.exceptions.UnitLengthDegreeScalerException;
import hr.fer.zemris.lsystems.impl.parser.exceptions.UnitLengthException;

/**
 * Parse the configuration text of the {@link LSystemBuilderImpl}. By parsing
 * the text configuration it will set up all necessary atributes which will be
 * find out to in the text configuration. Parsing will start by calling
 * {@link LSystemBuilderImpl#configureFromText(String[]).
 * 
 * @author dario
 * @see {@link LSystemBuilderImpl}
 */
public class Parser {

	/**
	 * final string which can be found in the text which will be parsed, so called
	 * key words
	 */
	private final String ORIGIN = "origin";
	private final String ANGLE = "angle";
	private final String UNITLENGTH = "unitLength";
	private final String UNITLENGTHDEGREESCALER = "unitLengthDegreeScaler";
	private final String COMMAND = "command";
	private final String AXIOM = "axiom";
	private final String PRODUCTION = "production";
	/**
	 * String array which will be parsed
	 */
	private String[] lines = new String[150];
	/**
	 * System, which we will set attributes by the
	 */
	private LSystemBuilderImpl system;

	/**
	 * Constructs the {@link Parser} with the lines which will be parsed and the
	 * results will be inserted into {@link LSystemBuilderImpl} system.
	 * 
	 * @param lines
	 *            - strings which will be parsed
	 * @param system
	 *            - in which will result be inserted
	 */
	public Parser(String[] lines, LSystemBuilderImpl system) {
		this.lines = lines;
		this.system = system;
	}

	/**
	 * It parse lines and puts the result into {@link LSystemBuilderImpl} as the
	 * atributes of that object.
	 */
	public void parseMe() {
		for (String line : lines) {
			String[] segment = line.split(" +");
			switch (segment[0]) {
			case ORIGIN:
				if (segment.length < 3 || segment.length > 3)
					throw new OriginException("Wrong number of atributes");
				try {
					double x = Double.parseDouble(segment[1]);
					double y = Double.parseDouble(segment[2]);
					system.setOrigin(x, y);
				} catch (NumberFormatException ex) {
					throw new OriginException(ex);
				}
				break;
			case ANGLE:
				if (segment.length < 2 || segment.length > 2)
					throw new AngleException("Wrong number of atributes");
				try {
					double angle = Double.parseDouble(segment[1]);
					system.setAngle(angle);
				} catch (NumberFormatException ex) {
					throw new AngleException(ex);
				}
				break;
			case UNITLENGTH:
				if (segment.length < 2 || segment.length > 2)
					throw new UnitLengthException("Wrong number of atributes");
				try {
					double len = Double.parseDouble(segment[1]);
					system.setUnitLength(len);
				} catch (NumberFormatException ex) {
					throw new UnitLengthException(ex);
				}
				break;
			case UNITLENGTHDEGREESCALER:

				String S = "";
				for (int i = 1; i < segment.length; ++i)
					S += segment[i];
				boolean twoNumbers = false;
				String first = "";
				String second = "";
				for (int i = 0; i < S.length(); ++i) {
					if (S.charAt(i) == '/') {
						twoNumbers = true;
					} else if (S.charAt(i) != ' ') {
						if (twoNumbers) {
							second += S.charAt(i);
						} else {
							first += S.charAt(i);
						}
					}
				}
				if (twoNumbers) {
					try {
						double firstNum = Double.parseDouble(first);
						double secondNum = Double.parseDouble(second);
						system.setUnitLengthDegreeScaler(firstNum / secondNum);
					} catch (NumberFormatException ex) {
						throw new UnitLengthDegreeScalerException(ex);
					}
				} else {
					try {
						double firstNum = Double.parseDouble(first);

						system.setUnitLengthDegreeScaler(firstNum);
					} catch (NumberFormatException ex) {
						throw new UnitLengthDegreeScalerException(ex);
					}
				}
				break;
			case AXIOM:
				if (segment.length < 2 || segment.length > 2)
					throw new AxiomException("Wrong number of atributes");
				system.setAxiom(segment[1]);
				break;
			case COMMAND:
				if (segment.length < 3)
					throw new CommandException("Wrong number of atributes");
				String name = segment[1];
				String rest = "";
				for (int i = 2; i < segment.length; ++i)
					rest += segment[i] + " ";
				// System.out.println(name+ " c " +rest);
				system.registerCommand(name.charAt(0), rest);
				break;
			case PRODUCTION:
				if (segment.length < 3)
					throw new ProductionException("Wrong number of atributes");
				String nameProduction = segment[1];
				String action = "";
				for (int i = 2; i < segment.length; ++i)
					action += segment[i] + " ";
				system.registerProduction(nameProduction.charAt(0), action);

				// System.out.println(nameProduction.charAt(0)+ " " +action);
				break;
			default:
				break;
			}
		}
	}
}
