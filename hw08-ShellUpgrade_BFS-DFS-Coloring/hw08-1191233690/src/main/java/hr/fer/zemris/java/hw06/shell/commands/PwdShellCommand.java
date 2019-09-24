package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * This class implements 'pwd' command.
 * This command expects no arguments and writes current directory.
 * */
public class PwdShellCommand implements ShellCommand{
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(!arguments.isEmpty()) {
			env.writeln("Command 'pwd' should not have any arguments.");
			return ShellStatus.CONTINUE;
		}
		env.writeln(env.getCurrentDirectory().toString());
		return ShellStatus.CONTINUE;
	}
	
	/**
	 * @return "pwd" as String
	 * */
	@Override
	public String getCommandName() {
		return "pwd";
	}
	
	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("The pwd command expects no argument. Otherwise, it won't write current directory.");
		list.add("It writes current directory to shell.");
		
		return Collections.unmodifiableList(list);
	}
}
