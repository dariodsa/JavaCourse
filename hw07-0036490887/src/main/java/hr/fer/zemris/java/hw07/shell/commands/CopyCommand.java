package hr.fer.zemris.java.hw07.shell.commands;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
 * {@link CopyCommand} encapsulate copy command from shell which copies files
 * from source to destination or inside directory.
 * 
 * @author dario
 *
 */
public class CopyCommand implements ShellCommand {

	/**
	 * string, name of the command
	 */
	private static final String COMMAND_NAME = "tree";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {

		String[] sep = UtilParsing.parse(arguments);
		if (sep.length != 2)
			throw new ShellException("Copy command can't be called with " + String.valueOf(sep.length) + " arguments.");

		Path sourceFile = Paths.get(sep[0]);
		Path destinationFile = Paths.get(sep[1]);

		if (!Files.isDirectory(destinationFile) && !Files.exists(destinationFile)) {
			try {
				Files.createFile(destinationFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (Files.isDirectory(destinationFile)) {
			Path destinationParent = Paths.get(sep[1]);
			destinationFile = Paths.get(destinationParent.toAbsolutePath().toString() + "/" + sourceFile.getFileName());

		} else if (Files.exists(destinationFile)) {
			env.writeln("Do you want to overwrite? y/n ");
			String line = env.readLine();
			if (line.startsWith("n")) {
				env.writeln("Aborted.");
				return ShellStatus.CONTINUE;
			}

		}
		try {
			copyFromSourceToDestination(sourceFile, destinationFile);
		} catch (IOException e) {
			throw new ShellException("Problems with reading/writing in files from copy command.");
		}

		return ShellStatus.CONTINUE;
	}

	/**
	 * @param sourceFile
	 * @param destinationFile
	 * @throws IOException
	 */
	private void copyFromSourceToDestination(Path sourceFile, Path destinationFile) throws IOException {
		InputStream is = Files.newInputStream(sourceFile);
		OutputStream os = Files.newOutputStream(destinationFile);

		byte[] buf = new byte[2048];
		int len = 0;

		while ((len = is.read(buf)) != -1) {

			os.write(buf, 0, len);
		}

		os.close();
		is.close();
	}

	@Override
	public String getCommandName() {
		return COMMAND_NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		return Arrays.asList("Copies file from source to the destination or inside directory.",
				"copy SOURCE [DEST | DIRECTORY]");
	}

}
