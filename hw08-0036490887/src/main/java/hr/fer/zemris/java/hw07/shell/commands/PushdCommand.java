package hr.fer.zemris.java.hw07.shell.commands;

import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.exceptions.ShellException;

/**
 * {@link PushdCommand} encapsulate command pushd from shell which task is to 
 * push current path to the stack, and for the current it sets one received 
 * from arguments.  
 * @author dario
 *
 */
public class PushdCommand implements ShellCommand {

	/**
	 * string, name of the command
	 */
	private static final String COMMAND_NAME = "pushd";
	/**
	 * key for stack
	 */
	private static final String KEY = "cdstack";
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		String[] sep = UtilParsing.parse(arguments);
		if(sep.length != 1) {
			throw new ShellException("Pushd command can't be called with " + 
					String.valueOf(sep.length)+ " arguments.");
		}
		
		checkIfPathIsValid(env, sep[0]);
		
		if(env.getSharedData(KEY) != null) {
			@SuppressWarnings("unchecked")
			Stack<Path> stack = (Stack<Path>) (env.getSharedData(KEY));
			stack.add(Paths.get(env.getCurrentDirectory().toString(), sep[0]));
		}
		env.commands().get("cd").executeCommand(env, arguments);
		
		return ShellStatus.CONTINUE;
	}
	/**
	 * Checks if the path is valid. If it is not, it will throw {@link ShellException}.
	 * @param env environment for current directory
	 * @param path path from shell
	 */
	private void checkIfPathIsValid(Environment env, String path) {
		try {
			Path newPath = Paths.get(env.getCurrentDirectory().toString(), path);
			if(!Files.exists(newPath)) {
				throw new ShellException("Path is not valid.");
			}
		} catch(InvalidPathException ex) { throw new ShellException("Path is not valid.");}
		
	}

	@Override
	public String getCommandName() {
		return COMMAND_NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		return Arrays.asList("Adds  a  directory to the top of the directory stack and sets DIR ",
							 " as the new shell path.",
						     "pushd [DIR]");	
	}

}
