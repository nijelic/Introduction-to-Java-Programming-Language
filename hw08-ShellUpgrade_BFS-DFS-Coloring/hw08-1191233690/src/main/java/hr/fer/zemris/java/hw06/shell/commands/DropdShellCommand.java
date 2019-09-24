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
 * The 'dropd' command expects no argument.
 * If path exists in stack pops it. Otherwise, nothing changes.
 * */
public class DropdShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (!arguments.isEmpty()) {
			env.writeln("Command 'popd' should not have any arguments.");
			return ShellStatus.CONTINUE;
		}
		@SuppressWarnings("unchecked")
		Stack<Path> stack = (Stack<Path>) env.getSharedData("cdstack");
		if (stack == null) {
			env.writeln("Stack is empty. Nothing has changed.");
			return ShellStatus.CONTINUE;
		}
		stack.pop();
		if (stack.isEmpty()) {
			env.setSharedData("cdstack", null);
		}
		return ShellStatus.CONTINUE;
	}
	
	/**
	 * @return "dropd" as String
	 * */
	@Override
	public String getCommandName() {
		return "dropd";
	}
	
	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("The 'dropd' command expects no argument.");
		list.add("If path exists in stack pops it. Otherwise, nothing changes.");
		
		return Collections.unmodifiableList(list);
	}
}
