package hr.fer.zemris.java.hw07.shell;

import java.io.FileDescriptor;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.SortedMap;

import hr.fer.zemris.java.hw07.shell.exceptions.ShellException;

/**
 * MyShell is main class which executes task from second part and allows user to
 * enter commands and to get result for them on the standard output.
 * 
 * @author dario
 *
 */
public class MyShell {

	/**
	 * environment in which shell commands will be run
	 */
	private static Environment environment;

	/**
	 * map of the all available commands in the shell
	 */
	private static SortedMap<String, ShellCommand> commands;

	/**
	 * current status of the shell
	 */
	private static ShellStatus status;

	/**
	 * Main method which accepts input and executes them with the following output
	 * to the output stream.
	 * 
	 * @param args
	 *            not used
	 */

	public static void main(String[] args) {

		if (args.length != 0) {
			System.out.println("Arguments are not used.");
			System.exit(1);
		}
		FileReader reader = new FileReader(FileDescriptor.in);
		FileWriter writer = new FileWriter(FileDescriptor.out);

		environment = new EnvironmentImpl(reader, writer);

		commands = environment.commands();

		status = ShellStatus.CONTINUE;

		while (status == ShellStatus.CONTINUE) {

			String line = environment.readLine();
			String commandName = getCommandName(line);
			String arguments = getArguments(line);
			
			ShellCommand command = commands.get(commandName);
			
			if (command == null) {
				System.err.println("That command doesn't exsist.");
				continue;
			}
			
			try {
				status = command.executeCommand(environment, arguments);
			} catch (ShellException ex) {
				System.out.println("\nERROR: " + ex.getMessage());
			} catch (Exception ex) {
				System.out.println("Fatal error.");
				break;
			}

		}

		try {
			reader.close();
			writer.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	/**
	 * Returns arguments from the input line. They appear after first space to the
	 * end.
	 * 
	 * @param line
	 *            input line
	 * @return arguments
	 */
	private static String getArguments(String line) {
		if (!line.contains(" "))
			return "";
		return line.substring(line.indexOf(' ') + 1);
	}

	/**
	 * Returns command name from the input line.
	 * 
	 * @param line
	 *            input line
	 * @return command , or first item in splitting by spaces
	 */
	private static String getCommandName(String line) {
		StringBuilder command = new StringBuilder();
		for (int i = 0, len = line.length(); i < len; ++i) {
			if (line.charAt(i) == ' ')
				break;
			command.append(line.charAt(i));
		}
		return command.toString();
	}
}
