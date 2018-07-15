package hr.fer.zemris.java.hw07.shell.commands;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.exceptions.ShellException;


/**
 * Command {@link DropdCommand} removes peek path from the stack and removes it. 
 * Currently directory remains unchanged.  If the stack is empty, exception will
 * be thrown. 
 * @author dario
 *
 */
public class DropdCommand implements ShellCommand {

	/**
	 * string, name of the command
	 */
	private static final String COMMAND_NAME = "listd";
	/**
	 * key for stack
	 */
	private static final String KEY = "cdstack";
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		String[] sep = arguments.split(" +");
		if(sep[0].length() != 0) {
			throw new ShellException("Dropd command can't be called with " + 
					String.valueOf(sep.length)+ " arguments.");
		}
		
		if(env.getSharedData(KEY) != null) {
			@SuppressWarnings("unchecked")
			Stack<Path> stack = (Stack<Path>) env.getSharedData(KEY);
			if(stack.isEmpty()) {
				throw new ShellException("Stack is empty.");
			} else {
				stack.pop();
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
		return Arrays.asList("Dropd removes peek path from the stack and removes it.",
							 "Currently directory remains unchanged.",
							 "run: dropd");
	}

}
