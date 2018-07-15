package hr.fer.zemris.java.hw07.shell.commands;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.exceptions.ShellException;

/**
 * {@link PopdCommand} encapsulate popd command from shell which task is to 
 * remove path from the stack and set it for the current path. It also check if in
 * the meanwhile that path is not existing. In that case, exception will be thrown. 
 * @author dario
 *
 */
public class PopdCommand implements ShellCommand {

	/**
	 * string, name of the command
	 */
	private static final String COMMAND_NAME = "popd";
	/**
	 * key for stack
	 */
	private static final String KEY = "cdstack";
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		String[] sep = arguments.split(" +");
		if(sep[0].length() != 0) {
			throw new ShellException("Popd command can't be called with " + 
					String.valueOf(sep.length)+ " arguments.");
		}
		if(env.getSharedData(KEY) != null) {
			@SuppressWarnings("unchecked")
			Stack<Path> stack = (Stack<Path>) env.getSharedData(KEY);
			if(stack.isEmpty()) {
				throw new ShellException("Stack is empty.");
			} else {
				Path newPath = stack.pop();
				if(Files.exists(newPath)) {
					throw new ShellException("Path from the peek is not found. Working directory is not changed.");
				}
				env.setCurrentDirectory(newPath);
			}
		} else {
			throw new ShellException("Stack is empty.");
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return COMMAND_NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		return Arrays.asList("Removes entries from the directory stack.  With no arguments, removes", 
				             "the top directory from the stack, and changes to the new top directory.",
				             "popd");
	}

}
