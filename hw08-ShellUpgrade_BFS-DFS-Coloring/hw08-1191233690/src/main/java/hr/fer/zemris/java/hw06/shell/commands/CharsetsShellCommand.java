package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.List;
import java.nio.charset.Charset;
import java.util.Collections;
import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Used for listing names of supported charsets for Java platform.
 * */
public class CharsetsShellCommand implements ShellCommand{

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (!arguments.isEmpty()) {
			env.writeln("Command 'charsets' takes no arguments.");
			return ShellStatus.CONTINUE;
		}
		for(String key : Charset.availableCharsets().keySet()) {
			env.writeln(key);
		}
		return ShellStatus.CONTINUE;
	}
	
	/**
	 * @return "charsets" as String
	 * */
	@Override
	public String getCommandName() {
		return "charsets";
	}
	
	
	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("Command charsets takes no arguments and lists names of supported charsets for your Java platform.");
		list.add("A single charset name is written per line.");
		return  Collections.unmodifiableList(list);
	}
}
