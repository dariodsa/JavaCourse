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

/**
 * Command {@link RmTreeCommand} encapsulate rmtree command from shell which 
 * task is to delete all data from one subtree. <b>Be very carefull with 
 * calling this function.</b>
 * @author dario
 *
 */
public class RmTreeCommand implements ShellCommand {

	/**
	 * string, name of the command
	 */
	private static final String COMMAND_NAME = "rmtree";
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		String[] sep = UtilParsing.parse(arguments);
		if(sep.length != 1) {
			throw new ShellException("Rmtree command can't be called with " + 
					String.valueOf(sep.length)+ " arguments.");
		}
		Path directory = Paths.get(env.getCurrentDirectory().toString(), sep[0]);
		if(!Files.exists(directory)) throw new ShellException("Directory doesn't exist.");
		try {
			Files.walkFileTree(directory, new FileVisitor<Path>() {

				@Override
				public FileVisitResult postVisitDirectory(Path path, IOException arg1) throws IOException {
					Files.delete(path);
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult preVisitDirectory(Path arg0, BasicFileAttributes arg1) throws IOException {
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFile(Path path, BasicFileAttributes arg1) throws IOException {
					Files.delete(path);
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFileFailed(Path arg0, IOException arg1) throws IOException {
					return FileVisitResult.CONTINUE;
				}
				
			});
		} catch (IOException e) {
			
			throw new ShellException(e.getCause());
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return COMMAND_NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		return Arrays.asList("rmtree removes all files from the DIR subtree. BE VERY CAREFULL when you",
							 "call this function.",
							 "rmtree [DIR]");
	}

}
