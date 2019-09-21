package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import hr.fer.zemris.java.hw06.shell.commands.util.Utility;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Accepts only one argument: new current directory.
 * */
public class CdShellCommand implements ShellCommand{
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> list = Utility.splitArguments(env, arguments);
		if (list == null) {
			return ShellStatus.CONTINUE;
		}
		if (list.size() != 1) {
			env.writeln("Command 'cd' should expects only one argument. Argument of the directory.");
			return ShellStatus.CONTINUE;
		}
		try {
			Path p = Paths.get(list.get(0));
			Path currentPath = env.getCurrentDirectory();
			currentPath = currentPath.resolve(p);
			env.setCurrentDirectory(currentPath);
		} catch (IOException e) {
			env.writeln(list.get(0) + " is not valid directory path.");
		}
		return ShellStatus.CONTINUE;
	}
	
	/**
	 * @return "cd" as String
	 * */
	@Override
	public String getCommandName() {
		return "cd";
	}
	
	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("The cd command expects only one argument.");
		list.add("Argument of the directory which will be set as new current directory.");
		
		return Collections.unmodifiableList(list);
	}

}
