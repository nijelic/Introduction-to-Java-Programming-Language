package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.regex.PatternSyntaxException;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.commands.util.Utility;
import hr.fer.zemris.java.hw06.shell.commands.util.NameBuilder;
import hr.fer.zemris.java.hw06.shell.commands.util.NameBuilderParser;
import hr.fer.zemris.java.hw06.shell.commands.util.FilterResult;

/**
 * Used for mass rename/move of files. Sub-commands: filter, groups, show,
 * execute Filter: lists filtered files. Groups: lists filtered files and their
 * groups Show: shows renaming simulation Execute: renames and moves files
 */
public class MassrenameShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> list = Utility.splitArguments(env, arguments);
		if (list == null) {
			return ShellStatus.CONTINUE;
		}
		if (list.size() < 4) {
			env.writeln("Number of arguments should be 4 or more.");
		}
		switch (list.get(2)) {
		case "filter":
			filterGroupSubcommands(env, list);
			break;
		case "groups":
			filterGroupSubcommands(env, list);
			break;
		case "show":
			showExecuteSubcommands(env, list);
			break;
		case "execute":
			showExecuteSubcommands(env, list);
			break;
		}

		return ShellStatus.CONTINUE;
	}

	/**
	 * This function is used for 'filter' or 'groups' sub-command.
	 * Writes what is appropriate for sub-command.
	 * @param env environment
	 * @param list list of arguments of command massrename
	 * */
	private void filterGroupSubcommands(Environment env, List<String> list) {
		if (list.size() != 4) {
			env.writeln("Wrong number of arguments. Expected number was 4: path, path, name of command and regex.");
			return;
		}
		Path pathFromArg = Paths.get(list.get(0));
		Path p = env.getCurrentDirectory().resolve(pathFromArg);
		List<FilterResult> results;

		try {
			results = filter(p, list.get(3));
		} catch (PatternSyntaxException e) {
			env.writeln("Regex was invalid.");
			return;
		} catch (IOException e) {
			env.writeln("Could not find given path.");
			return;
		}

		if (list.get(2).equals("filter")) {
			for (FilterResult f : results) {
				env.writeln(f.toString());
			}
		} else {
			for (FilterResult f : results) {
				env.write(f.toString());
				for (int i = 0, length = f.numberOfGroups(); i <= length; ++i) {
					env.write(" " + i + ": " + f.group(i));
				}
				env.writeln("");
			}
		}

	}

	/**
	 * This function is used for 'show' or 'execute' subcommand.
	 * Writes what is appropriate for subcommand.
	 * @param env environment
	 * @param list list of arguments of command massrename
	 * */
	private void showExecuteSubcommands(Environment env, List<String> list) {
		if (list.size() != 5) {
			env.writeln(
					"Wrong number of arguments. Expected number was 5: path, path, name of command, regex and expression.");
			return;
		}
		Path givenPath1 = Paths.get(list.get(0));
		Path p = env.getCurrentDirectory().resolve(givenPath1);
		Path givenPath2 = Paths.get(list.get(1));
		Path p2 = env.getCurrentDirectory().resolve(givenPath2);
		List<FilterResult> results;

		try {
			results = filter(p, list.get(3));
		} catch (PatternSyntaxException e) {
			env.writeln("Regex was invalid.");
			return;
		} catch (IOException e) {
			env.writeln("Could not find given path.");
			return;
		}

		try {
			NameBuilderParser parser;
			parser = new NameBuilderParser(list.get(4));
			NameBuilder builder = parser.getNameBuilder();

			// show
			if (list.get(2).equals("show")) {
				for (FilterResult result : results) {
					StringBuilder sb = new StringBuilder();
					builder.execute(result, sb);
					env.writeln(result.toString() + " => " + sb.toString());
				}
				// execute
			} else {
				for (FilterResult result : results) {
					StringBuilder sb = new StringBuilder();
					builder.execute(result, sb);
					String newName = sb.toString();
					env.writeln(givenPath1.resolve(result.toString()) + " => " + givenPath2.resolve(newName));
					try {
						Files.move(p.resolve(result.toString()), p2.resolve(newName), StandardCopyOption.REPLACE_EXISTING);
					} catch (IOException e) {
						env.writeln("Could not copy this file: " + p.resolve(result.toString()));
					}
				}
			}
		} catch (IllegalStateException | NumberFormatException e) {
			env.writeln("Error occured while parsing expresion: \'" + e.getMessage() + "\'");
			return;
		}
	}

	/**
	 * This method goes through directory and filters names of files.
	 * Returns list of names as list of FilterResult.
	 * @param dir directory in which are files
	 * @param pattern pattern used for comparing
	 * @return list of FilterResult
	 * */
	private static List<FilterResult> filter(Path dir, String pattern) throws IOException {
		List<FilterResult> results = new ArrayList<>();
		Pattern pat = Pattern.compile(pattern, Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
		Files.walk(dir, 1).filter(path -> path.getNameCount() > dir.getNameCount()).forEach(path -> {
			try {
				if (!Files.isDirectory(path)) {
					Matcher m = pat.matcher(path.getFileName().toString());
					if (m.matches()) {
						results.add(new FilterResult(m));
					}
				}
			} catch (SecurityException e) {
			}
		});
		return results;
	}

	/**
	 * @return "massrename" as String
	 */
	@Override
	public String getCommandName() {
		return "massrename";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("Used for mass rename/move of files.");
		list.add("Subcommands: filter, groups, show, execute");
		list.add("Filter: lists filtered files. Needed 4 args: 2 paths, name of sub-command and pattern.");
		list.add("Groups: lists filtered files and their groups. Needed 4 args: 2 paths, name of sub-command and pattern.");
		list.add("Show: shows renaming simulation. Needed 5 args: 2 paths, name of sub-command, pattern and expression.");
		list.add("Execute: renames and moves files. Needed 5 args: 2 paths, name of sub-command, pattern and expression.");

		return Collections.unmodifiableList(list);
	}
}