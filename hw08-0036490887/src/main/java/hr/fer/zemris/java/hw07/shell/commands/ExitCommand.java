package hr.fer.zemris.java.hw07.shell.commands;

import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * {@link ExitCommand} encapsulate exit command which only task is to return 
 * {@link ShellStatus} terminate when is execute, by calling this you exit the shell.
 * @author dario
 *
 */
public class ExitCommand implements ShellCommand{

	/**
	 * string, name of the command
	 */
	private static final String COMMAND_NAME = "exit";
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		return ShellStatus.TERMINATE;
	}

	@Override
	public String getCommandName() {
		return COMMAND_NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		return Arrays.asList(new String[] {"Command exit exits the shell environment."});
	}

}
