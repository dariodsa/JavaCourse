package hr.fer.zemris.java.hw07.shell.commands;

import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.exceptions.ShellException;

/**
 * {@link SymbolCommand} encapsulate command symbol from shell which task is to
 * set environment variables such as PROMT, MULTILINE and MORELINE. It is also
 * avaliable to print current value of it.
 * 
 * @author dario
 *
 */
public class SymbolCommand implements ShellCommand {

	/**
	 * string, name of the command
	 */
	private static final String COMMAND_NAME = "symbol";

	/**
	 * argument in command, prompt
	 */
	private final String PROMPT_ARG = "PROMPT";
	/**
	 * argument in command, more lines
	 */
	private final String MORELINES_ARG = "MORELINES";
	/**
	 * argument in command, multiline
	 */
	private final String MULTILINE_ARG = "MULTILINE";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {

		String[] sep = arguments.split(" +");
		//System.out.println(arguments);
		if (sep[0].equals(PROMPT_ARG)) {
			if (sep.length == 1) {
				env.writeln("Symbol for PROMPT is '" + env.getPromptSymbol() + "'");
			} else {
				env.setPromptSymbol(sep[1].charAt(0));
			}

		} else if (sep[0].equals(MORELINES_ARG)) {
			if (sep.length == 1) {
				env.writeln("Symbol for MORELINES is '" + env.getMorelinesSymbol() + "'");
			} else {
				env.setMorelinesSymbol(sep[1].charAt(0));
			}
		} else if (sep[0].equals(MULTILINE_ARG)) {
			if (sep.length == 1) {
				env.writeln("Symbol for MULTILINE is '" + env.getMultilineSymbol() + "'");
			} else {
				env.setMultilineSymbol(sep[1].charAt(0));
			}
		} else {
			throw new ShellException("Unknown identificator name after symbol." + sep[0]);
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return COMMAND_NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		return Arrays
				.asList(new String[] { "Sets value of the PROMT, MULTILINE and MORELINE variables, or print its value.",
						"New symbol is optional, if there is no such, current value will be printed.",
						"symbol [MORELINES | PROMPT | MULTILINE] new_symbol" });
	}

}
