package hr.fer.zemris.java.hw07;

import java.io.FileDescriptor;
import java.io.FileReader;
import java.io.FileWriter;

import org.junit.*;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.EnvironmentImpl;
import hr.fer.zemris.java.hw07.shell.exceptions.ShellException;

public class ShellTest {

	private Environment environment;

	@Before
	public void init() {
		FileReader reader = new FileReader(FileDescriptor.in);
		FileWriter writer = new FileWriter(FileDescriptor.out);
		this.environment = new EnvironmentImpl(reader, writer);
	}

	@Test(expected = ShellException.class)
	public void mkdirWrongNumOfArguments() {
		this.environment.commands().get("mkdir").executeCommand(environment, "nista nista");
	}

	@Test(expected = ShellException.class)
	public void copyWrongNumOfArguments() {
		this.environment.commands().get("copy").executeCommand(environment, "nista nista nista");
	}

	@Test(expected = ShellException.class)
	public void catWrongNumOfArguments() {
		this.environment.commands().get("cat").executeCommand(environment, "nista nista op");
	}

	@Test(expected = ShellException.class)
	public void hexDumpWrongNumOfArguments() {
		this.environment.commands().get("hexdump").executeCommand(environment, "nista nista");
	}

	@Test(expected = ShellException.class)
	public void hexDumpFolder() {
		this.environment.commands().get("hexdump").executeCommand(environment, "src");
	}

	@Test(expected = ShellException.class)
	public void hexDumpFileNotFound() {
		this.environment.commands().get("hexdump").executeCommand(environment, "vernisnids");
	}

	@Test(expected = ShellException.class)
	public void lsFolderNotFound() {
		this.environment.commands().get("ls").executeCommand(environment, "vernisnids");
	}

	@Test(expected = ShellException.class)
	public void lsFile() {
		this.environment.commands().get("ls").executeCommand(environment, "pom.xml");
	}

	@Test(expected = ShellException.class)
	public void treeFile() {
		this.environment.commands().get("ls").executeCommand(environment, "pom.xml");
	}

	@Test(expected = ShellException.class)
	public void treeFolderNotFound() {
		this.environment.commands().get("ls").executeCommand(environment, "versnidss");
	}

	@Test(expected = ShellException.class)
	public void catFolder() {
		this.environment.commands().get("cat").executeCommand(environment, "src");
	}

	@Test(expected = ShellException.class)
	public void catFileNotFound() {
		this.environment.commands().get("cat").executeCommand(environment, "dashdgsaj");
	}
	
	@Test(expected = ShellException.class)
	public void helpWrongNumOfArguemnts() {
		this.environment.commands().get("help").executeCommand(environment, "dasd ghj gdsj");
	}
	
	@Test(expected = ShellException.class)
	public void helpCommandNotFound() {
		this.environment.commands().get("help").executeCommand(environment, "sha256sum");
	}
}
