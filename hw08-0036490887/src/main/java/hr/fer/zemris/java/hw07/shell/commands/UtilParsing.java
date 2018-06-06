package hr.fer.zemris.java.hw07.shell.commands;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.exceptions.ShellException;

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
		if(insideString) {
			throw new ShellException("You didn't close string.");
		}
		return list.toArray(new String[0]);
	}
	
	/**
	 * Method parse arguments in the array, argument by argument. Seprator which is
	 * used here is space which has to be outside of a string.
	 * 
	 * @param arguments
	 * @return
	 */
	public static String[] parse2(String arguments) {

		List<String> list = new ArrayList<String>();
		StringBuilder builder = new StringBuilder();
		boolean insideString = false;

		for (int i = 0, len = arguments.length(); i < len; ++i) {

			if(list.size() < 1) {
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
			} else {
				if(arguments.charAt(i) == '"' && insideString) {
					
					list.add(builder.toString());
					insideString = false;
					builder.setLength(0);
				} else if(arguments.charAt(i) == '"' && !insideString) {
					insideString = true;
				} else if(builder.length() > 0 && !insideString 
						&&  (arguments.charAt(i) == ' ' || 
							 arguments.charAt(i) == '\t')) {
					list.add(builder.toString());
					
					builder.setLength(0);
				}
				else {
					builder.append(arguments.charAt(i));
				}
			}
		}
		if (builder.length() != 0) {
			list.add(builder.toString());
		}
		if(insideString) {
			throw new ShellException("You didn't close string.");
		}
		return list.toArray(new String[0]);
	}
	
}
