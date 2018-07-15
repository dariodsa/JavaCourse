package hr.fer.zemris.java.hw07.shell.commands;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.exceptions.ShellException;
import hr.fer.zemris.java.hw07.shell.exceptions.ShellIOException;

/**
 * {@link TreeCommand} encapsulate tree command from shell, which task is to
 * print all files in the given directory printed in the tree structure, in
 * which going to right means deeper level of the file.
 * 
 * @author dario
 *
 */
public class TreeCommand implements ShellCommand {

	/**
	 * string, name of the command
	 */
	private static final String COMMAND_NAME = "tree";
	/**
	 * Default space between two different levels in printing.
	 */
	private static final int DIRECTORY_DEPTH = 2;
	/**
	 * file visitor
	 */
	private FileVisitor<Path> visitor;

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {

		visitor = new TreeVisiting(env);

		String[] sep = UtilParsing.parse(arguments);
		if (sep.length != 1) {
			throw new ShellException("Tree command can't be called with " + String.valueOf(sep.length) + " arguments.");
		}
		Path directory = Paths.get(env.getCurrentDirectory().toString(), sep[0]);
		try {
			Files.walkFileTree(directory, visitor);
		} catch (IOException e) {
			throw new ShellIOException(e.getMessage());
		}

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return COMMAND_NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		return Arrays.asList("Tree  is  a  recursive  directory listing program that produces a depth",
				"indented listing of files.", "tree DIRECTORY");
	}

	/**
	 * Static class which implements {@link FileVisitor} specify what should be done
	 * when we entry the directory or exit one, or we find file. When there is new
	 * directory space is increased, when we exit then is it resolved to last value.
	 * 
	 * @author dario
	 *
	 */
	static class TreeVisiting implements FileVisitor<Path> {

		/**
		 * space from left
		 */
		private int space;
		/**
		 * environment on which will be printed result
		 */
		private Environment env;

		/**
		 * Constructs {@link TreeVisiting} with the specific parameters.
		 * 
		 * @param env
		 *            environment on which the result will be printed
		 */
		public TreeVisiting(Environment env) {
			this.space = 0;
			this.env = env;
		}

		@Override
		public FileVisitResult postVisitDirectory(Path arg0, IOException arg1) throws IOException {
			this.space -= DIRECTORY_DEPTH;
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult preVisitDirectory(Path file, BasicFileAttributes arg1) throws IOException {

			if (space != 0) {
				env.writeln(String.format("%" + space + "s%s", "", file.getFileName()));
			} else {
				env.writeln(file.getFileName().toString());
			}

			this.space += DIRECTORY_DEPTH;
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes arg1) throws IOException {
			if (space != 0) {
				env.writeln(String.format("%" + space + "s%s", "", file.getFileName()));
			} else {
				env.writeln(file.getFileName().toString());
			}
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFileFailed(Path arg0, IOException arg1) throws IOException {
			return FileVisitResult.CONTINUE;
		}

	}

}
