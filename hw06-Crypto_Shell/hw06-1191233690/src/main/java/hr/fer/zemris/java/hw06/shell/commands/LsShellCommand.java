package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * This command lists all content of directory.
 * Expects only one path of the directory.
 * */
public class LsShellCommand implements ShellCommand{

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> list = Utility.splitArguments(env, arguments);
		if (list == null) {
			return ShellStatus.CONTINUE;
		}
		if (list.size() == 1) {
			Path p = Paths.get(list.get(0));
			try {
				Files.walk(p, 1)
					.filter(path -> path.getNameCount() > p.getNameCount())
					.forEach(path -> {
						try {
							// IOException occurs if not possible to get attributes.
							BasicFileAttributeView faView = Files.getFileAttributeView(
									path, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS
									);
							BasicFileAttributes attributes = faView.readAttributes();
								
							
							if(Files.isDirectory(path)) {
								env.write("d");
							} else {
								env.write("-");
							}
							if(Files.isReadable(path)) {
								env.write("r");
							} else {
								env.write("-");
							}
							if(Files.isWritable(path)) {
								env.write("w");
							} else {
								env.write("-");
							}
							if(Files.isExecutable(path)) {
								env.write("x");
							} else {
								env.write("-");
							}
							
							env.write(String.format("%10d", attributes.size()));
							
							FileTime fileTime = attributes.creationTime();
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));
							env.write(" " + formattedDateTime + " ");
							
							env.writeln(path.getFileName().toString());
						} catch(SecurityException e) {
							env.writeln("You can't see this data, because of security.");
						} catch(IOException e) {
							env.writeln("Could not get attributes.");
						}
					});
			} catch(IOException e) {
				env.writeln("Could not find file.");
			}
		} else {
			env.writeln("Command 'ls' should be given 1 argument.");
		}
		return ShellStatus.CONTINUE;
	}
	
	/**
	 * @return "ls" as String
	 * */
	@Override
	public String getCommandName() {
		return "ls";
	}
	
	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("This command lists all content of directory."); 
		list.add("Expects only one path of the directory.");
		
		return Collections.unmodifiableList(list);
	}
}
