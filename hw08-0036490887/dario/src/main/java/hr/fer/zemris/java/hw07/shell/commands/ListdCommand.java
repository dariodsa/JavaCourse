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
 * Command {@link ListdCommand} encapsulate listd which task is to 
 * print all path that are on the stack with the specific key. 
 * @author dario
 *
 */
public class ListdCommand implements ShellCommand {

	/**
	 * string, name of the command
	 */
	private static final String COMMAND_NAME = "listd";
	/**
	 * key for stack
	 */
	private static final String KEY = "cdstack";
	/**
	 * empty stack
	 */
	private static final String EMPTY_STACK = "Nema pohranjenih direktorija.";
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		String[] sep = arguments.split(" +");
		if(sep[0].length() != 0) {
			throw new ShellException("Listd command can't be called with " + 
					String.valueOf(sep.length)+ " arguments.");
		}
		
		if(env.getSharedData(KEY) != null) {
			@SuppressWarnings("unchecked")
			Stack<Path> stack = (Stack<Path>) env.getSharedData(KEY);
			if(stack.isEmpty()) {
				env.writeln(EMPTY_STACK);
			} else {
				for(Path path : stack) {
					env.writeln(path.toString());
				}
			}
		} else {
			env.writeln(EMPTY_STACK);
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return COMMAND_NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		return Arrays.asList("Listd prints all path which are on the stack begining with one ",
				 " which are new.",
				 "run: listd");
	}

}
