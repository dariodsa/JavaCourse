package hr.fer.zemris.java.hw07.shell.commands;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.SortedMap;
import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * {@link CharsetsCommand} encapsulate charsets command from shell which returns
 * list of all available charsets in this shell.
 * 
 * @author dario
 *
 */
public class CharsetsCommand implements ShellCommand {

	/**
	 * string, name of the command
	 */
	private static final String COMMAND_NAME = "charsets";
	/**
	 * default message before listing charsets
	 */
	private static final String DEFAULT_MESSAGE = "Avaliable charsets: ";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		SortedMap<String, Charset> set = Charset.availableCharsets();
		env.writeln(DEFAULT_MESSAGE);
		for (String charSet : set.keySet()) {
			env.writeln("  " + charSet);
		}

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return COMMAND_NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		return Arrays.asList("Charsets command prints all available charset in which is ",
				"avaliable to use in this shell.", "run: charsets ");
	}

}
