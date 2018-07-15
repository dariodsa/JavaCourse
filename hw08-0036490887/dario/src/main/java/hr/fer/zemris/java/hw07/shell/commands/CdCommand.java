package hr.fer.zemris.java.hw07.shell.commands;

import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.exceptions.ShellException;

/**
 * {@link CdCommand} encapsulate cd command from shell which task 
 * is to with given new path set it to the shell so it that becomes 
 * new working directory.
 * @author dario
 *
 */
public class CdCommand implements ShellCommand {

	/**
	 * string, name of the command
	 */
	private static final String COMMAND_NAME = "pwd";
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		String[] sep = UtilParsing.parse(arguments);
		if(sep.length != 1) {
			throw new ShellException("Cd command can't be called with " + 
					String.valueOf(sep.length)+ " arguments.");
		}
		
		/*System.out.println(Paths.get(env.getCurrentDirectory().toString(),
					sep[0]
					).toAbsolutePath().toString());*/
		
		try {
			
			env.setCurrentDirectory(
					Paths.get(env.getCurrentDirectory().toString(),
					sep[0]
					));
		} catch(InvalidPathException ex) {
			throw new ShellException(ex.getCause());
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return COMMAND_NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		return Arrays.asList("Change the current directory to DIR.",
							 "cd [DIR]");
	}

}
