package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.MyShell;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * This command is used to terminate {@link MyShell} by user.
 * */
public class ExitShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(!arguments.isEmpty()) {
			env.writeln("Command 'exit' should not have any arguments.");
			return ShellStatus.CONTINUE;
		}
		return ShellStatus.TERMINATE;
	}
	
	/**
	 * @return "exit" as String
	 * */
	@Override
	public String getCommandName() {
		return "exit";
	}
	
	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("The exit command expects no argument. Otherwise, it won't terminate it.");
		list.add("It terminates the Shell.");
		
		return Collections.unmodifiableList(list);
	}
}
