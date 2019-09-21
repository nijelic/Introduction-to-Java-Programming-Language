package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * The tree command expects a single argument: directory name and prints a tree 
 * (each directory level shifts output two charatcers to the right).
 * */
public class TreeShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> list = Utility.splitArguments(env, arguments);
		if (list == null) {
			return ShellStatus.CONTINUE;
		}
		if (list.size() != 1) {
			env.writeln("Expected only one argument, the directory name.");
			return ShellStatus.CONTINUE;
		}

		Path p = Paths.get(list.get(0));
		
		if(Files.isDirectory(p) == false) {
			env.writeln("You gave path, but it is not directory.");
			return ShellStatus.CONTINUE;
		}
		
		try {
			Files.walkFileTree(p, new SimpleFileVisitor<Path>() {
				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
					int depth = file.getNameCount() - 1;
					env.writeln("  ".repeat(depth) + file.getName(depth));
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
					int depth = dir.getNameCount() - 1;
					env.writeln("  ".repeat(depth) + dir.getName(depth));
					return FileVisitResult.CONTINUE;
				}
			});
		} catch (IOException e) {
			env.writeln("Directory not found.");
		}
		return ShellStatus.CONTINUE;
	}

	/**
	 * @return "tree" as String
	 */
	@Override
	public String getCommandName() {

		return "tree";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("The tree command expects a single argument: directory name and prints a tree.");
		list.add("Each directory level shifts output two charatcers to the right.");
		
		return Collections.unmodifiableList(list);
	}
}
