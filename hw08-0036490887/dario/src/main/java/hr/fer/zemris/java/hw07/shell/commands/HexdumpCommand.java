package hr.fer.zemris.java.hw07.shell.commands;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.java.hw07.Util;
import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.exceptions.ShellException;

/**
 * {@link HelpCommand} encapsulate help command in shell. Its task is to print 
 * command descriptions and also to print on which command is able to call.
 * @author dario
 *
 */

public class HexdumpCommand implements ShellCommand {

	/**
	 * string, name of the command
	 */
	private static final String COMMAND_NAME = "hexdump";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] sep = UtilParsing.parse(arguments);
		if (sep.length != 1) {
			throw new ShellException(
					"Hexdump command can't be called with " + String.valueOf(sep.length) + " arguments.");
		}
		Path file = Paths.get(env.getCurrentDirectory().toString(), sep[0]);
		if (!Files.exists(file)) {
			throw new ShellException("File doesn't exsist. " + sep[0]);
		}
		if (Files.isDirectory(file)) {
			throw new ShellException("File is directory. " + sep[0]);
		}
		byte[] data = new byte[16];
		int len = 0;
		int br = 0;
		try (InputStream is = Files.newInputStream(file)) {
			while ((len = is.read(data)) != -1) {
				env.write(String.format("%08x: ", 16 * (br++)));
				for (int i = 0; i < 8 && i < len; ++i) {
					String hexSign = Util.bytetohex(new byte[] { data[i] });
					if (i != 0)
						env.write(" ");
					env.write(hexSign);
				}
				env.write("|");
				for (int i = 8; i < 16 && i < len; ++i) {
					String hexSign = Util.bytetohex(new byte[] { data[i] });
					if (i != 0)
						env.write(" ");
					env.write(hexSign);
				}
				env.write(" | ");
				for (int i = 0; i < len; ++i) {
					env.write(data[i] < 32 || data[i] > 127 ? "." : String.valueOf((char) data[i]));
				}
				env.writeln("");
			}
		} catch (IOException ex) {
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return COMMAND_NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		return Arrays.asList(new String[] { "Hexdump command prints all content of the file. ", 
											"It prints 16 bytes per one line, if the char is lower then 32 dot will be printed instead. ", 
											"hexdump FILE"});
	}

}
