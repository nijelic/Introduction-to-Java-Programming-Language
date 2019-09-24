package hr.fer.zemris.java.hw06.shell;

import java.util.SortedMap;
import java.util.TreeMap;
import java.io.Closeable;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import hr.fer.zemris.java.hw06.shell.commands.CatShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.CdShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.CharsetsShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.CopyShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.DropdShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.ExitShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.HelpShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.HexdumpShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.ListdShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.LsShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.MassrenameShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.MkdirShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.PopdShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.PushdShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.PwdShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.SymbolShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.TreeShellCommand;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Main class which runs Shell.
 * */
public class MyShell {

	/**
	 * Main method starts Shell.
	 * */
	public static void main(String[] args) {
		
		/**
		 * Implementation of Environment. Used for communication with user.
		 * Implements Closeable because needs to close Scanner.
		 * */
		class MyEnvironment implements Environment, Closeable {
			
			/**
			 * Used for scanning input of user.
			 * */
			private Scanner scan;
			/**
			 * Used for security check if scan closes.
			 * */
			private boolean scanClosed;
			/**
			 * multilineSymbol used for marking multilines
			 * */
			private Character multilineSymbol;
			/**
			 * promptSymbol is used for marking input line.
			 * */
			private Character promptSymbol;
			/**
			 * morelinesSymbol is used for marking more lines by user.
			 * */
			private Character morelinesSymbol;
			/**
			 * commands is used to pair commands' name with commands' object
			 * */
			private SortedMap<String, ShellCommand> commands;
			/**
			 * Contains current directory
			 * */
			private Path currentDirectory;
			/**
			 * Used for sharing data between commands
			 * */
			private Map<String, Object> sharedData;
			
			/**
			 * Default constructor. Sets all private variables to default.
			 * */
			public MyEnvironment() {
				scan = new Scanner(System.in);
				scanClosed = false;
				multilineSymbol = '|';
				promptSymbol = '>';
				morelinesSymbol = '\\';
				commands = new TreeMap<>();
				commands.put("cat", new CatShellCommand());
				commands.put("charsets", new CharsetsShellCommand());
				commands.put("copy", new CopyShellCommand());
				commands.put("exit", new ExitShellCommand());
				commands.put("hexdump", new HexdumpShellCommand());
				commands.put("ls", new LsShellCommand());
				commands.put("mkdir", new MkdirShellCommand());
				commands.put("tree", new TreeShellCommand());
				commands.put("symbol", new SymbolShellCommand());
				commands.put("help", new HelpShellCommand());
				commands.put("cd", new CdShellCommand());
				commands.put("pwd", new PwdShellCommand());
				commands.put("dropd", new DropdShellCommand());
				commands.put("pushd", new PushdShellCommand());
				commands.put("listd", new ListdShellCommand());
				commands.put("popd", new PopdShellCommand());
				commands.put("massrename", new MassrenameShellCommand());
				currentDirectory = Paths.get(".");
				sharedData = new HashMap<>();
			}
			
			/**
			 * Used for closing Scanner scan.
			 * */
			public void close() {
				if(scanClosed == false) {
					scan.close();
					scanClosed = true;
				}
			}
			
			@Override
			public String readLine() throws ShellIOException {
				String line;
				try {
					line = scan.nextLine();
				} catch (NoSuchElementException | IllegalStateException e) {
					throw new ShellIOException(e.getMessage());
				}
				return line;
			}
			
			@Override
			public void write(String text) throws ShellIOException {
				try {
					System.out.print(text);
				} catch (Exception e) {
					throw new ShellIOException(e.getMessage());
				}
			}
			
			@Override
			public void writeln(String text) throws ShellIOException {
				try {
					System.out.println(text);
				} catch (Exception e) {
					throw new ShellIOException(e.getMessage());
				}
			}
			
			@Override
			public SortedMap<String, ShellCommand> commands() {
				return Collections.unmodifiableSortedMap(commands);
			}
			
			@Override
			public Character getMultilineSymbol() {
				return multilineSymbol;
			}
			
			@Override
			public void setMultilineSymbol(Character symbol) {
				multilineSymbol = symbol;
			}
			
			@Override
			public Character getPromptSymbol() {
				return promptSymbol;
			}
			
			@Override
			public void setPromptSymbol(Character symbol) {
				promptSymbol = symbol;
			}
			
			@Override
			public Character getMorelinesSymbol() {
				return morelinesSymbol;
			}
			
			@Override
			public void setMorelinesSymbol(Character symbol) {
				morelinesSymbol = symbol;			
			}
			
			@Override
			public Path getCurrentDirectory() {
				return currentDirectory.toAbsolutePath().normalize();
			}
			
			@Override
			public void setCurrentDirectory(Path path) throws IOException{
				if(!path.toFile().isDirectory()) {
					throw new IOException("No such directory.");
				}
				currentDirectory = path.toAbsolutePath().normalize();
			}
			
			@Override
			public Object getSharedData(String key) {
				if (key == null) {
					return null;
				}
				return sharedData.get(key);
			}
			
			@Override
			public void setSharedData(String key, Object value) {
				if(key == null) {
					return;
				}
				sharedData.put(key, value);
			}
		}
		
		
		try(MyEnvironment env = new MyEnvironment()) {
			ShellStatus status = ShellStatus.CONTINUE;
			env.writeln("Welcome to MyShell v 1.0");
			
			while(status != ShellStatus.TERMINATE) {
				env.write(env.getPromptSymbol() + " ");
				String l = readLineOrLines(env);
				int boundaryIndex = l.indexOf(' ') == -1 ? l.length() : l.indexOf(' ');
				String commandName = l.substring(0, boundaryIndex);
				String arguments = l.substring(boundaryIndex);
				ShellCommand command = env.commands().get(commandName);
				if(command == null) {
					env.writeln("Wrong command. Use 'help'.");
					continue;
				}
				status = command.executeCommand(env, arguments);
			} 
		} catch(ShellIOException e) {
			// Terminates shell.
		}
		
	}
	
	/**
	 * Used for parsing input from user.
	 * @param env {@link Environment} used for communicating with user.
	 * */
	private static String readLineOrLines(Environment env) {
		StringBuilder string = new StringBuilder();
		String line = env.readLine();
		
		while(line.length() > 0 && line.charAt(line.length()-1) == env.getMorelinesSymbol()) {
			string.append(line.substring(0, line.length()-1));
			env.write(env.getMultilineSymbol() + " ");
			line = env.readLine();
		}
		string.append(line);
		return string.toString();
	}

}
