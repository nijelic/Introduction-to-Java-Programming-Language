package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * The 'popd' command expects no arguments.
 * It pops path from stack and sets it as current if possible.
 * */
public class PopdShellCommand implements ShellCommand {

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
		Path p = stack.pop();
		try {
			if (p.toFile().isDirectory()) {
				env.setCurrentDirectory(p);
			} else {
				env.writeln("Couldn't set path. Directory doesn't exist, but path was poped from stack.");
			}
		} catch (IOException e) {
			env.writeln("Could not change current directory.");
			return ShellStatus.CONTINUE;
		}
		if (stack.isEmpty()) {
			env.setSharedData("cdstack", null);
		}

		return ShellStatus.CONTINUE;
	}

	/**
	 * @return "popd" as String
	 */
	@Override
	public String getCommandName() {
		return "popd";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("The 'popd' command expects no arguments.");
		list.add("It pops path form stack and sets it as current if possible.");

		return Collections.unmodifiableList(list);
	}
}
