package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * This command is used to inform user about commands. If there is no argument it lists commands.
 * Otherwise, expect only one argument, the name of command.
 * */
public class HelpShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments.isEmpty()) {
			env.writeln("All supported commands are:");
			for(var key : env.commands().keySet()) {
				env.writeln(key);
			}
			return ShellStatus.CONTINUE;
		}
		List<String> list = Utility.splitArguments(env, arguments);
		if (list == null) {
			return ShellStatus.CONTINUE;
		}
		if (list.size() == 1) {
			ShellCommand command = env.commands().get(list.get(0));
			if(command == null) {
				env.writeln("Command '" + list.get(0) + "' doesn't exist.");
				return ShellStatus.CONTINUE;
			}
			for(String line : command.getCommandDescription()) {
				env.writeln(line);
			}
		} else {
			env.writeln("Command 'help' should be given none or 1 argument.");
		}
		return ShellStatus.CONTINUE;
	}
	
	@Override
	public String getCommandName() {
		return "help";
	}
	
	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("This command is used to inform user about commands.");
		list.add("If there is no argument it lists commands.");
		list.add("Otherwise, expect only one argument, the name of command.");
		
		return Collections.unmodifiableList(list);
	}
}
