package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * The listd command expects no arguments. If stack is not empty it will write
 * all paths from stack. Otherwise, it will write: 'Nema pohranjenih
 * direktorija.'
 */
public class ListdShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (!arguments.isEmpty()) {
			env.writeln("Command 'listd' should not have any arguments.");
			return ShellStatus.CONTINUE;
		}
		@SuppressWarnings("unchecked")
		Stack<Path> stack = (Stack<Path>) env.getSharedData("cdstack");
		if (stack == null) {
			// stack is empty
			env.writeln("Nema pohranjenih direktorija.");
			return ShellStatus.CONTINUE;
		}
		
		for(int i = stack.size()-1; i >= 0; --i) {
			env.writeln(stack.get(i).toString());
		}
		return ShellStatus.CONTINUE;
	}

	/**
	 * @return "listd" as String
	 */
	@Override
	public String getCommandName() {
		return "listd";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("The listd command expects no arguments.");
		list.add("If stack is not empty it will write all paths from stack.");
		list.add("Otherwise, it will write: 'Nema pohranjenih direktorija.'");

		return Collections.unmodifiableList(list);
	}
}
