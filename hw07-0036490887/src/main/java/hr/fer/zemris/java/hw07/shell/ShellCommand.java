package hr.fer.zemris.java.hw07.shell;

import java.util.List;
/**
 * Interface {@link ShellCommand} specifies methods which every new shell 
 * command should implement. 
 * @author dario
 *
 */
public interface ShellCommand {
	/**
	 * Executes command in the given {@link Environment} with the given arguments 
	 * as String.
	 * @param env {@link Environment} in which command will be run
	 * @param arguments with what arguments command will be run
	 * @return {@link ShellStatus} status after executing the command
	 */
	ShellStatus executeCommand(Environment env, String arguments);
	/**
	 * Returns command name which can be run by calling its name.
	 * @return name of the command
	 */
	String getCommandName();
	/**
	 * Returns a description (usage instructions) of a command in multiple line, 
	 * that is the reason of the List<String>.
	 * @return list of strings , instructions  
	 */
	List<String> getCommandDescription();
}
