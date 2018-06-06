package hr.fer.zemris.java.hw07.shell.commands;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.exceptions.ShellException;

/**
 * {@link HelpCommand} is encapsulation of shell function help 
 * which prints help of function on which is called. It accepts one or none arguments.
 * 
 * @see ShellCommand
 * @author dario
 *
 */
public class HelpCommand implements ShellCommand {

	/**
	 * string, name of the command
	 */
	private static final String COMMAND_NAME = "help";
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String sep[] = UtilParsing.parse(arguments);
		Set<String> commands = env.commands().keySet();
		
		if(arguments.length() == 0) {
			env.writeln("Help command is supported for this commands: ");
			for(String command : commands) {
				env.writeln("  " + command);
			}
		} else {
			if(commands.contains(sep[0])) {
				List<String> helpLines = env.commands().get(sep[0]).getCommandDescription();
				for(String helpLine : helpLines) {
					env.writeln(helpLine);
				}
			} else throw new ShellException("There isn't help manual for this command. " + sep[0]);
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return COMMAND_NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		return Arrays.asList("Help command can be called with one or none arguments.",
							 "If there is only one help will print help of that command, ",
							 "otherwise is will print all commands on which help can be called.");
	}

}
