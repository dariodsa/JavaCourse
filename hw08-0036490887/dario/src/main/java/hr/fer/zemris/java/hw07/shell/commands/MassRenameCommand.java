package hr.fer.zemris.java.hw07.shell.commands;

import java.awt.image.ReplicateScaleFilter;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.builder.NameBuilder;
import hr.fer.zemris.java.hw07.shell.builder.NameBuilderInfo;
import hr.fer.zemris.java.hw07.shell.builder.NameBuilderInfoImpl;
import hr.fer.zemris.java.hw07.shell.builder.NameBuilderParser;
import hr.fer.zemris.java.hw07.shell.exceptions.ShellException;

/**
 * Command {@link MassRenameCommand} encapsulate massrename command from shell
 * which task is to select file from some subtree ones which passing regex
 * checking. They will be moved in the other subtree under new name which is
 * given in specific format, which would be parsed.
 * 
 * @author dario
 *
 */
public class MassRenameCommand implements ShellCommand {

	/**
	 * string, name of the command
	 */
	private static final String COMMAND_NAME = "massrename";
	/**
	 * filter keyword
	 */
	private static final String FILTER = "filter";
	/**
	 * groups keyword
	 */
	private static final String GROUPS = "groups";
	/**
	 * execute keyword
	 */
	private static final String EXECUTE = "execute";
	/**
	 * show keyword
	 */
	private static final String SHOW = "show";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {

		String[] sep = UtilParsing.parse2(arguments);

		Path dir1 = Paths.get(env.getCurrentDirectory().toString(), sep[0]);
		Path dir2 = Paths.get(env.getCurrentDirectory().toString(), sep[1]);

		String CMD = sep[2];
		String mask = sep[3];

		Pattern pattern = Pattern.compile(mask, Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
		
		try {
			if (CMD.equals(FILTER)) {

				Files.list(dir1).filter((p) -> pattern.matcher(p.getFileName().toString()).matches())
						.forEach((p) -> env.writeln(p.getFileName().toString()));

			} else if (CMD.equals(GROUPS)) {
				Files.list(dir1).filter((p) -> pattern.matcher(p.getFileName().toString()).matches()).forEach((p) -> {
					Matcher matcher = pattern.matcher(p.getFileName().toString());
					matcher.find();
					StringBuilder builder = new StringBuilder();

					builder.append(matcher.group(0) + " ");
					for (int i = 0; i < matcher.groupCount(); ++i) {
						builder.append(i + ": " + matcher.group(i) + " ");
					}

					env.writeln(builder.toString());
				});
			} else if (CMD.equals(SHOW)) {

				String expression = sep[3];
				NameBuilderParser parser = new NameBuilderParser(expression);
				NameBuilder builder = parser.getNameBuilder();
				Files.list(dir1).filter((p) -> pattern.matcher(p.getFileName().toString()).matches()).forEach((p) -> {
					Matcher matcher = pattern.matcher(p.getFileName().toString());
					matcher.find();

					NameBuilderInfo info = new NameBuilderInfoImpl(matcher);
					builder.execute(info);
					String newName = info.getStringBuilder().toString();
					

					env.writeln(p.getFileName().toString() + " => " + newName);
				});

			} else if (CMD.equals(EXECUTE)) {

				String expression = sep[3];
				
				NameBuilderParser parser = new NameBuilderParser(expression);
				NameBuilder builder = parser.getNameBuilder();
				Files.list(dir1).filter((p) -> pattern.matcher(p.getFileName().toString()).matches()).forEach((p) -> {
					Matcher matcher = pattern.matcher(p.getFileName().toString());
					matcher.find();
					NameBuilderInfo info = new NameBuilderInfoImpl(matcher);
					builder.execute(info);
					String newName = info.getStringBuilder().toString();
					
					
					try {
						Files.move(p, Paths.get(dir2.toString(), newName), StandardCopyOption.REPLACE_EXISTING);
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
			}
		} catch (Exception ex) {
			throw new ShellException(ex.getMessage());
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return COMMAND_NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		return Arrays.asList("Massrename changes name of files in the DIR1 subtree if the files match ",
				"MASKA regex and it will be moved in the DIR2 using new name defined in the rest.",
				"massrename [DIR1] [DIR2] [CMD] [MASKA] rest");
	}

}
