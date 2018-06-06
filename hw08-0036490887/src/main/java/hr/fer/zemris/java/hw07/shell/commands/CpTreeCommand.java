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
 * {@link CpTreeCommand} encapsulate cptree command from shell which 
 * task is to copy all files from one subtree to the another. If first or
 * second location doesn't exsist, exception will be thrown. 
 * @author dario
 *
 */
public class CpTreeCommand implements ShellCommand {

	/**
	 * string, name of the command
	 */
	private static final String COMMAND_NAME = "cptree";
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] sep = UtilParsing.parse(arguments);
		if(sep.length != 2) {
			throw new ShellException("Cptree command can't be called with " + 
					String.valueOf(sep.length)+ " arguments.");
		}
		Path path1 = Paths.get(env.getCurrentDirectory().toString(), sep[0]);
		Path path2 = Paths.get(env.getCurrentDirectory().toString(), sep[1]);
		if(!Files.exists(path1) || !Files.exists(path2)) {
			throw new ShellException("Dir1 or dir2 doesnt exsist.");
		}
		
		
		try {
			Files.walkFileTree(path1, new FileVisitor<Path>() {
				
				Path current = Paths.get("");
				@Override
				public FileVisitResult postVisitDirectory(Path arg0, IOException arg1) throws IOException {
					current = current.getParent();
					env.commands().get("cd").executeCommand(env, "../");
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult preVisitDirectory(Path path, BasicFileAttributes arg1) throws IOException {
					current = Paths.get(current.toString(), path.getFileName().toString());
					
					Files.createDirectories(
							Paths.get(
									path2.toString(), 
									current.toString() 
									
							  )
							);
					env.commands().get("cd").executeCommand(env, path.getFileName().toString());
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFile(Path path, BasicFileAttributes arg1) throws IOException {
				
					env.commands().get("copy").executeCommand(env,
							 path.getFileName() + " " + "\""+
							          path2.toString() + "/" +
									  current.toString() + "/" + 
									  path.getFileName().toString()+
							 "\""
							);
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
		return Arrays.asList("CpTree copies all subtree from [DIR1] to the [DIR2]. If the dir1 or",
							" doesn't exsist, error will accour.",
							 "cptree [DIR1] [DIR2]");
	}

}
