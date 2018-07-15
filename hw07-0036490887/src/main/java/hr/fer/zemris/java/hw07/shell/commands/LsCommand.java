package hr.fer.zemris.java.hw07.shell.commands;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.exceptions.ShellException;
import hr.fer.zemris.java.hw07.shell.exceptions.ShellIOException;

/**
 * {@link LsCommand} encapsulate ls command from shell which task is to 
 * print content of the current folder without going deeper. 
 * 
 * @author dario
 *
 */
public class LsCommand implements ShellCommand{
	/**
	 * string, name of the command
	 */
	private static final String COMMAND_NAME = "ls";
	
	
	/**
	 * file visitor
	 */
	private FileVisitor<Path> visitor;
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		visitor = new LsVisitor(env);
		
		String[] sep = UtilParsing.parse(arguments);
		if(sep.length != 1) {
			throw new ShellException("Ls command can't be called with " + 
									String.valueOf(sep.length)+ " arguments.");
		}
		
		Path directory = Paths.get(sep[0]);
		if(!Files.exists(directory)) {
			throw new ShellException("File in ls arguments doesn't exsists.");
		}
		if(!Files.isDirectory(directory)) {
			throw new ShellException("File in ls arguments isn't directory.");
		}
		try {
			/*Files.list(Paths.get(directory)).
				forEach((path)-> {
					try {
						env.writeln(getStatus(path));
					} catch (Exception e) { throw new ShellIOException(e.getMessage());}
				});*/
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
		return Arrays.asList("List  information  about  the FILEs in folder which was",
							 "given as arguments.",
							 "ls folder");
	}
	/**
	 * Static class which implements {@link FileVisitor} specifies 
	 * how the files should be printed and it is also responsible for accessing 
	 * all files in the current folder. If there is some folders, its subtree is skipped. 
	 * @author dario
	 *
	 */
	static class LsVisitor implements FileVisitor<Path> {
		
		/**
		 * environment
		 */
		private Environment env;
		/**
		 * indicator that file doesn't have some property
		 */
		private static final char NONE      = '-';
		/**
		 * indicator of directory
		 */
		private static final char DIRECTORY = 'd';
		/**
		 * indicator of readable file
		 */
		private static final char READABLE  = 'r';
		/**
		 * indicator of writable file
		 */
		private static final char WRITEABLE = 'w';
		/**
		 * indicator of executable file
		 */
		private static final char EXECUTE   = 'x';
		/**
		 * when the first folder is entered 
		 */
		private boolean entry = false;
		
		/**
		 * Constructs {@link LsVisitor} with environment on which the results, 
		 * files will be printed.
		 * @param env shell's environment
		 */
		public LsVisitor(Environment env) {
			this.env = env;
			this.entry = false;
		}
		/**
		 * Returns status of file which will be shown in ls command.
		 * In status is include date of creation, name, size, is directory, readable,
		 * writable and executable. 
		 * @param path location of the file
		 * @return String status of file
		 * @throws IOException
		 */
		private String getStatus(Path path) throws IOException {
			char dir = Files.isDirectory(path)?DIRECTORY:NONE;
			char read = Files.isReadable(path)?READABLE:NONE;
			char write = Files.isWritable(path)?WRITEABLE:NONE;
			char exe = Files.isExecutable(path)?EXECUTE:NONE;
			
			BasicFileAttributeView faView = Files.getFileAttributeView(
					path, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS
					);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			BasicFileAttributes attributes = faView.readAttributes();
			FileTime fileTime = attributes.creationTime();
			String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));
		
			
			return String.format(
					"%c%c%c%c %10s %s %s",
					dir, read, write, exe, 
					String.valueOf(Files.size(path)), formattedDateTime, path.getFileName()
					);
		}
		@Override
		public FileVisitResult postVisitDirectory(Path arg0, IOException arg1) throws IOException {
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult preVisitDirectory(Path file, BasicFileAttributes arg1) throws IOException {
			if(entry) {
				env.writeln(getStatus(file));
				return FileVisitResult.SKIP_SUBTREE;
			}
			entry = true;
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes arg1) throws IOException {
			env.writeln(getStatus(file));
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFileFailed(Path arg0, IOException arg1) throws IOException {
			return FileVisitResult.CONTINUE;
		}
		
	}
}
