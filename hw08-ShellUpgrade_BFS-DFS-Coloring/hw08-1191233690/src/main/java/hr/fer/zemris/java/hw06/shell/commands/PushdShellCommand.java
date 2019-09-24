package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.commands.util.Utility;

/**
 * The 'pushd' command expects only one argument.
 * If path exist it pushes current directory to stack and sets new current directory to given path.
 * */
public class PushdShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> list = Utility.splitArguments(env, arguments);
		if (list == null) {
			return ShellStatus.CONTINUE;
		}
		if (list.size() != 1) {
			env.writeln("Command 'pushd' expects only one argument. Argument of the directory.");
			return ShellStatus.CONTINUE;
		}
		Path p = Paths.get(list.get(0));
		p = env.getCurrentDirectory().resolve(p);
		
		if(!p.toFile().isDirectory()) {
			env.writeln("Given path is not directory path.");
			return ShellStatus.CONTINUE;
		}
		
		@SuppressWarnings("unchecked")
		Stack<Path> stack = (Stack<Path>)env.getSharedData("cdstack");
		if(stack == null) {
			stack = new Stack<Path>();
			stack.add(env.getCurrentDirectory());
			env.setSharedData("cdstack", stack);
		} else {
			stack.add(env.getCurrentDirectory());
		}
		try {
			env.setCurrentDirectory(p);
		} catch(IOException e) {
			env.writeln("Couldn't set given path as current path");
		}
		return ShellStatus.CONTINUE;
	}
	
	/**
	 * @return "pushd" as String
	 * */
	@Override
	public String getCommandName() {
		return "pushd";
	}
	
	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("The pushd command expects only one argument.");
		list.add("If path exist it pushes current directory to stack and sets new current directory to given path.");
		
		return Collections.unmodifiableList(list);
	}
}
