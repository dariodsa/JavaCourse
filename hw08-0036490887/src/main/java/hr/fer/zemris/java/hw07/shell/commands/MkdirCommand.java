package hr.fer.zemris.java.hw07.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.exceptions.ShellException;
import hr.fer.zemris.java.hw07.shell.exceptions.ShellIOException;

/**
 * {@link MkdirCommand} encapsulate mkdir command from shell and its 
 * task is to create new folder in the current folder named by the argument.
 *  
 * @author dario
 *
 */
public class MkdirCommand implements ShellCommand{
	/**
	 * string, name of the command
	 */
	private static final String COMMAND_NAME = "mkdir";
	
	/**
	 * Returns shell status terminate in order to exit the shell.
	 * @return {@link ShellStatus} TERMINATE 
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		String[] sep = UtilParsing.parse(arguments);
		if(sep.length != 1) {
			throw new ShellException("Mkdir command can't be called with " + 
					String.valueOf(sep.length)+ " arguments.");
		}
		
		try {
			Path newDirectory = Paths.get(
					env.getCurrentDirectory().toString(),
					sep[0]
					);
			
			Files.createDirectory(newDirectory);
		} catch (IOException e) {
			throw new ShellIOException(e.getMessage());
		}
		env.writeln("Directory " + sep[0] + " was successfully created.");
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return COMMAND_NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		return Arrays.asList(new String[] {"Create the DIRECTORY(ies), if they do not already exist."
										, "mkdir DIRECTORY"});
	}
}
