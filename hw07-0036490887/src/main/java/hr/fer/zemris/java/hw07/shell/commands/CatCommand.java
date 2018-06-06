package hr.fer.zemris.java.hw07.shell.commands;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.exceptions.ShellException;

/**
 * {@link CatCommand} encapsulate cat command from shell which outputs file's
 * content to the shell output. It doesn't matter is the file text document or
 * binary file.
 * 
 * @author dario
 *
 */
public class CatCommand implements ShellCommand {

	/**
	 * string, name of the command
	 */
	private static final String COMMAND_NAME = "cat";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {

		String[] sep = UtilParsing.parse(arguments);

		if (sep.length < 1 || sep.length > 2) {
			throw new ShellException("Cat command can't be called with " + String.valueOf(sep.length) + " arguments.");
		}
		Charset charSet;

		if (sep.length == 1) {
			charSet = Charset.defaultCharset();
		} else {
			charSet = Charset.forName(sep[1]);
		}

		if (!Files.exists(Paths.get(sep[0]))) {
			throw new ShellException("File in cat command is not found.");
		}

		try {
			writeFile(sep[0], charSet, env);
		} catch (IOException e) {
			throw new ShellException(e.getMessage());
		}
		return ShellStatus.CONTINUE;
	}

	/**
	 * Writes to the environment file data decoded by the given charset.
	 * 
	 * @param fileLocation
	 *            location of the file
	 * @param charSet
	 *            charSet using for decoding
	 * @param env
	 *            environment in which will data be printed
	 * @throws IOException
	 *             file is not found
	 */
	private void writeFile(String fileLocation, Charset charSet, Environment env) throws IOException {

		Path path = Paths.get(fileLocation);
		InputStream is = Files.newInputStream(path);
		byte[] data = new byte[2048];
		@SuppressWarnings("unused")
		int len = 0;
		while ((len = is.read(data)) != -1) {
			String text = new String(data, charSet);
			env.write(text);
		}
		is.close();
	}

	@Override
	public String getCommandName() {
		return COMMAND_NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		return Arrays.asList(new String[] { "Concatenate file to standard output.", "cat FILE" });
	}

}
