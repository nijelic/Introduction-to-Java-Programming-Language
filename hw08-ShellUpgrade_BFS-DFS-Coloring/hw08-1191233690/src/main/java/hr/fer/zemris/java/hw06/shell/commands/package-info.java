/**
 * Implementations of commands:
 * 
 * {@link hr.fer.zemris.java.hw06.shell.commands.CatShellCommand}
 * Used for 'cat' command which opens given file and writes its content to console.
 * 
 * 
 * {@link hr.fer.zemris.java.hw06.shell.commands.CharsetsShellCommand}
 * Used for listing names of supported charsets for Java platform.
 * 
 * 
 * {@link hr.fer.zemris.java.hw06.shell.commands.CopyShellCommand}
 * The copy command copies file to directory. Need two arguments.
 * 
 * 
 * {@link hr.fer.zemris.java.hw06.shell.commands.ExitShellCommand}
 * This command is used to terminate {@link MyShell} by user.
 * 
 * 
 * {@link hr.fer.zemris.java.hw06.shell.commands.HelpShellCommand}
 * This command is used to inform user about commands. If there is no argument it lists commands.
 * Otherwise, expect only one argument, the name of command.
 * 
 * 
 * {@link hr.fer.zemris.java.hw06.shell.commands.HexdumpShellCommand}
 * Used for hexdumping file to MyShell. In each line prints: 
 * line number as hex | 16 hexadecimals of 16 data symbols | 16 data symbols. 
 * 
 * 
 * {@link hr.fer.zemris.java.hw06.shell.commands.LsShellCommand}
 *  This command lists all content of directory.
 * 	Expects only one path of the directory.
 * 
 * 
 * {@link hr.fer.zemris.java.hw06.shell.commands.MkdirShellCommand}
 * The mkdir command takes a single argument: directory name, and creates the appropriate directory.
 * 
 * 
 * {@link hr.fer.zemris.java.hw06.shell.commands.SymbolShellCommand}
 * This command is used for informing and updating PROMPT, MORELINES and MULTILINE symbol.
 * It takes one or two arguments. If one, accepts only: PROMPT, MORELINES and MULTILINE.
 * If two, first argument must be PROMPT, MORELINES or MULTILINE. Second can be some Character symbol.
 * This way you change symbol which renders.
 * 
 * 
 * {@link hr.fer.zemris.java.hw06.shell.commands.TreeShellCommand}
 * The tree command expects a single argument: directory name and prints a tree 
 * (each directory level shifts output two charatcers to the right).
 * 
 * 
 * {@link hr.fer.zemris.java.hw06.shell.commands.Utility}
 * Used for spliting arguments in Shell Commands.
 * 
 * {@link hr.fer.zemris.java.hw06.shell.commands.CdShellCommand}
 * Accepts only one argument: new current directory.
 * 
 * {@link hr.fer.zemris.java.hw06.shell.commands.PwdShellCommand}
 * This class implements 'pwd' command.
 * This command expects no arguments and writes current directory.
 * 
 * {@link hr.fer.zemris.java.hw06.shell.commands.PushdShellCommand}
 * The 'pushd' command expects only one argument.
 * If path exist it pushes current directory to stack and sets new current directory to given path.
 * 
 * {@link hr.fer.zemris.java.hw06.shell.commands.PopdShellCommand}
 * The 'popd' command expects no arguments.
 * It pops path from stack and sets it as current if possible.
 * 
 * {@link hr.fer.zemris.java.hw06.shell.commands.ListdShellCommand}
 * The listd command expects no arguments. If stack is not empty it will write
 * all paths from stack. Otherwise, it will write: 'Nema pohranjenih
 * direktorija.'
 * 
 * {@link hr.fer.zemris.java.hw06.shell.commands.DropdShellCommand}
 * The 'dropd' command expects no argument.
 * If path exists in stack pops it. Otherwise, nothing changes.
 * 
 * {@link hr.fer.zemris.java.hw06.shell.commands.MassrenameShellCommand}
 * Used for mass rename/move of files. Sub-commands: filter, groups, show,
 * execute Filter: lists filtered files. Groups: lists filtered files and their
 * groups Show: shows renaming simulation Execute: renames and moves files
 * 
 * @author Jelić, Nikola
 *
 */
package hr.fer.zemris.java.hw06.shell.commands;

