package hr.fer.zemris.java.hw07.shell;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw07.shell.commands.CatCommand;
import hr.fer.zemris.java.hw07.shell.commands.CharsetsCommand;
import hr.fer.zemris.java.hw07.shell.commands.CopyCommand;
import hr.fer.zemris.java.hw07.shell.commands.ExitCommand;
import hr.fer.zemris.java.hw07.shell.commands.HelpCommand;
import hr.fer.zemris.java.hw07.shell.commands.HexdumpCommand;
import hr.fer.zemris.java.hw07.shell.commands.LsCommand;
import hr.fer.zemris.java.hw07.shell.commands.MkdirCommand;
import hr.fer.zemris.java.hw07.shell.commands.SymbolCommand;
import hr.fer.zemris.java.hw07.shell.commands.TreeCommand;
import hr.fer.zemris.java.hw07.shell.exceptions.ShellIOException;

/**
 * 
 * @author dario
 *
 */
public class EnvironmentImpl implements Environment{
	
	/**
	 * welcome message
	 */
	private final String WELCOME_MESSAGE = "Welcome to MyShell v 1.0";
	/**
	 * promt symbol
	 */
	private Character PROMT_SYMBOL = '>';
	/**
	 * more lines symbol
	 */
	private Character MORE_LINES = '\\';
	/**
	 * multi line symbol
	 */
	private Character MULTI_LINE = '|';
	
	/**
	 * map of the all available commands in the shell
	 */
	private SortedMap<String, ShellCommand> commands;
	/**
	 * output stream
	 */
	private FileWriter writer;
	/**
	 * input stream
	 */
	private FileReader reader;
	
	/**
	 * Constructs new environment with given input stream, and output stream.
	 * Those streams are important for reading inputs and to write results
	 * of the commands.
	 * @param reader input stream
	 * @param writer output stream
	 */
	public EnvironmentImpl(FileReader reader, FileWriter writer) {
		
		this.reader = reader;
		this.writer = writer;
		commands = new TreeMap<>();
		commands.put("exit", new ExitCommand());
		commands.put("symbol", new SymbolCommand());
		commands.put("mkdir", new MkdirCommand());
		commands.put("ls", new LsCommand());
		commands.put("charsets", new CharsetsCommand());
		commands.put("cat", new CatCommand());
		commands.put("tree", new TreeCommand());
		commands.put("help", new HelpCommand());
		commands.put("hexdump", new HexdumpCommand());
		commands.put("copy", new CopyCommand());
		writeln(WELCOME_MESSAGE);
	}

	@Override
	public String readLine() throws ShellIOException {
		StringBuilder builder = new StringBuilder();
		int cnt = 0;
		while(true) {
			if(cnt == 0) {
				write(PROMT_SYMBOL + " ");
			} else {
				write(MULTI_LINE + " ");
			}
			
			String line;
			try {
				line = newLine();
			} catch (IOException e) {
				throw new ShellIOException(e.getCause());
			}
			
			if(line.lastIndexOf(MORE_LINES) != line.length() - 1) {
				builder.append(line);
				break;
			} else {
				builder.append(line.substring(0, line.length()-2));
			}
			builder.append(" ");
			++cnt;
		}
		return builder.toString();
	}
	/**
	 * Returns new line read from input stream which 
	 * was set in the constructor. 
	 * @return String new line from stream
	 * @throws IOException
	 */
	private String newLine() throws IOException {
		StringBuilder bob = new StringBuilder();
		int next = 0;
		while((next = reader.read()) != -1) {
			char sign = (char) next;
			if(sign == '\n') {
				break;
			}
			bob.append(sign);
		}
		return bob.toString();
	}

	@Override
	public void write(String text) throws ShellIOException {
		try {
			writer.append(text);
			writer.flush();
		} catch (IOException e) {
			throw new ShellIOException(e.getCause());
		}
		
	}

	@Override
	public void writeln(String text) throws ShellIOException {
		try {
			writer.append(text);
			writer.append(String.format("%n"));
			writer.flush();
		} catch (IOException e) {
			throw new ShellIOException(e.getCause());
		}
	}

	@Override
	public SortedMap<String, ShellCommand> commands() {
		return Collections.unmodifiableSortedMap(commands);
	}

	@Override
	public Character getMultilineSymbol() {
		return this.MULTI_LINE;
	}

	@Override
	public void setMultilineSymbol(Character symbol) {
		changeState("MULTILINES", this.MULTI_LINE, symbol);
		this.MULTI_LINE = symbol;
	}

	@Override
	public Character getPromptSymbol() {
		return this.PROMT_SYMBOL;
	}

	@Override
	public void setPromptSymbol(Character symbol) {
		changeState("PROMPT", this.PROMT_SYMBOL, symbol);
		this.PROMT_SYMBOL = symbol;
	}

	@Override
	public Character getMorelinesSymbol() {
		return this.MORE_LINES;
	}

	@Override
	public void setMorelinesSymbol(Character symbol) {
		changeState("MORELINES", this.MORE_LINES, symbol);
		this.MORE_LINES = symbol;		
	}
	/**
	 * Prints changes of the state. It prints which state has changed
	 * and old and new value.
	 * @param state name of the state which has been changed
	 * @param oldChar old value
	 * @param newChar new value
	 */
	private void changeState(String state, char oldChar, char newChar) {
		writeln("Symbol for " + state + " changed from '" + oldChar +"' to '" + newChar + "'");
	}
}
