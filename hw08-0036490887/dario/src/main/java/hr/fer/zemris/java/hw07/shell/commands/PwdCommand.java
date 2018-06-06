package hr.fer.zemris.java.hw07.shell.commands;

import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.exceptions.ShellException;

/**
 * {@link PwdCommand} encapsulate pwd command from shell which task is 
 * to print current directory of the shell which is accessible by
 *  {@link Environment#getCurrentDirectory()} .
 * @author dario
 *
 */
public class PwdCommand implements ShellCommand {

	/**
	 * string, name of the command
	 */
	private static final String COMMAND_NAME = "pwd";
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		String[] sep = arguments.split(" +");
		if(sep[0].length() != 0) {
			throw new ShellException("Pwd command can't be called with " + 
					String.valueOf(sep.length)+ " arguments.");
		}
		
		env.writeln(env.getCurrentDirectory().toAbsolutePath().toString());
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return COMMAND_NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		return Arrays.asList("Print the full filename of the current working directory.",
							 "run: pwd");
	}

}
