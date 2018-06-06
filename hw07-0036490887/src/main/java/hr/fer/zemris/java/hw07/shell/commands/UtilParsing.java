package hr.fer.zemris.java.hw07.shell.commands;

import java.util.ArrayList;
import java.util.List;

/**
 * UtilParsing is responsible for parsing a file paths in the arguments. It has
 * only one static method {@link #parse(String)} which operates that operation.
 * 
 * @author dario
 *
 */
public class UtilParsing {

	/**
	 * Method parse arguments in the array, argument by argument. Seprator which is
	 * used here is space which has to be outside of a string.
	 * 
	 * @param arguments
	 * @return
	 */
	public static String[] parse(String arguments) {

		List<String> list = new ArrayList<String>();
		StringBuilder builder = new StringBuilder();
		boolean insideString = false;

		for (int i = 0, len = arguments.length(); i < len; ++i) {

			if (arguments.charAt(i) == '\\' && len > i + 1 && arguments.charAt(i + 1) == '"') {
				builder.append('"');
				++i;
			} else if (arguments.charAt(i) == '\\' && len > i + 1 && arguments.charAt(i + 1) == '\\') {
				builder.append('\\');
				++i;
			} else if (arguments.charAt(i) == '\\') {
				builder.append('\\');
				builder.append('\\');
			} else if (arguments.charAt(i) == '"' && !insideString) {

				insideString = true;
			} else if (arguments.charAt(i) == '"' && insideString) {
				insideString = false;
			} else if (arguments.charAt(i) == ' ' && !insideString) {
				if (builder.length() != 0) {
					list.add(builder.toString());
					builder.setLength(0);
				}
			} else {
				builder.append(arguments.charAt(i));
			}
		}
		if (builder.length() != 0) {
			list.add(builder.toString());
		}

		return list.toArray(new String[0]);
	}
}
