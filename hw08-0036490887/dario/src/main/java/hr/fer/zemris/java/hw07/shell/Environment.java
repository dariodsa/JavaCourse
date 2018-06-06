package hr.fer.zemris.java.hw07.shell;

import java.nio.file.Path;
import java.util.SortedMap;

import hr.fer.zemris.java.hw07.shell.exceptions.ShellException;
import hr.fer.zemris.java.hw07.shell.exceptions.ShellIOException;

/**
 * Interface {@link Environment} encapsulate needed methods for implementing shell enviroment.
 * It offers {@link #write(String)} as well {@link #readLine()}. It is possible to set 
 * properties such as {@link #setPromptSymbol(Character)}, {@link #setMorelinesSymbol(Character)}.
 * 
 * @see ShellCommand
 * @author dario
 * @version 1.0
 * @since 1.0
 *
 */

public interface Environment {
	/**
	 * Returns line read from standard input.
	 * @return line from standard input
	 * @throws ShellIOException
	 */
	String readLine() throws ShellIOException;
	/**
	 * Writes text at the standard output stream
	 * @param text which will be printed at the standard
	 * output stream
	 * @throws ShellIOException
	 */
	void write(String text) throws ShellIOException;
	/**
	 * Writes text at the standard output stream and 
	 * new line after that.
	 * @param text which will be printed at the standard
	 * output stream
	 * @throws ShellIOException
	 */
	void writeln(String text) throws ShellIOException;
	/**
	 * Returns current command in the environment. 
	 * @return map {@link String} to {@link ShellCommand}, 
	 * command name to shell commmand interface
	 */
	SortedMap<String, ShellCommand> commands();
	/**
	 * Returns current multi line symbol
	 * @return {@link Character} multiline symbol
	 */
	Character getMultilineSymbol();
	/**
	 * Sets new multiline symobl.
	 * @param symbol new multiline symbol
	 */
	void setMultilineSymbol(Character symbol);
	/**
	 * Returns current prompt symbol.
	 * @return {@link Character} prompt symbol
	 */
	Character getPromptSymbol();
	/**
	 * Sets new prompt symbol.
	 * @param symbol new prompt symbol
	 */
	void setPromptSymbol(Character symbol);
	/**
	 * Returns new more lines symbol.
	 * @return {@link Character} more lines symbol
	 */
	Character getMorelinesSymbol();
	/**
	 * Sets new more lines symbol.
	 * @param symbol new more lines symbol
	 */
	void setMorelinesSymbol(Character symbol);
	/**
	 * Returns absolute normalize path which is equal 
	 * to the current directory path. 
	 * @return current directory path
	 */
	Path getCurrentDirectory();
	
	/**
	 * Sets the new working directory given in the path. 
	 * If the directory doesn't exist method will throw 
	 * exception. 
	 * @param path path of new working directory
	 * @throw {@link ShellException}
	 */
	void setCurrentDirectory(Path path);
	/**
	 * Returns shared data under specific key. 
	 * @param key key used to share data
	 * @return data under specific key
	 */
	Object getSharedData(String key);
	/**
	 * Set shared data under specific key.
	 * @param key {@link String} key of data
	 * @param value {@link Object} value of shared data
	 */
	void setSharedData(String key, Object value);
	
	
	
	
	
}
