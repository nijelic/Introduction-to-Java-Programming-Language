package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * The mkdir command takes a single argument: directory name, and creates the appropriate directory.
 * */
public class MkdirShellCommand implements ShellCommand{

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> list = Utility.splitArguments(env, arguments);
		if (list == null) {
			return ShellStatus.CONTINUE;
		}
		if (list.size() == 1) {
			try{
				Path p = Paths.get(list.get(0));
				Files.createDirectories(p);
			} catch(UnsupportedOperationException e) {
				env.writeln("Could not create directory: " + list.get(0));
			} catch(FileAlreadyExistsException e) {
				env.writeln("Could not create directory. '" + list.get(0)+"' already exists.");
			} catch(SecurityException e) {
				env.writeln("Write access to the new directory is not provided.");
			} catch(IOException e) {
				env.writeln("Could not create directory: " + list.get(0) + "; Maybe parent doesn't exist.");
			}
			
		} else {
			env.writeln("Command 'mkdir' should be given 1 argument, the name of new directory.");
		}
		return ShellStatus.CONTINUE;
	}
	
	/**
	 * @return "mkdir" as String
	 * */
	@Override
	public String getCommandName() {
		return "mkdir";
	}
	
	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("The mkdir command takes a single argument: directory name, and creates the appropriate directory.");
		
		return Collections.unmodifiableList(list);
	}
}
