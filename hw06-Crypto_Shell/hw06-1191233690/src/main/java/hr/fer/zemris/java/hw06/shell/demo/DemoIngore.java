package hr.fer.zemris.java.hw06.shell.demo;

import java.nio.file.Paths;
import java.nio.file.Path;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.SimpleFileVisitor;
import java.nio.charset.Charset;
import java.nio.file.FileVisitResult;
import java.nio.file.attribute.BasicFileAttributes;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.BufferedInputStream;
import java.text.SimpleDateFormat;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.LinkOption;
import java.nio.file.attribute.FileTime;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import hr.fer.zemris.java.hw06.crypto.Util;
import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.commands.Utility;


/**
 * IGNORE this class. 
 * */
public class DemoIngore {

	private static final int DATA_CHUNK = 16;
	
	public static void main(String[] args) {

		Scanner scan = new Scanner(System.in);

		String rut = "../../rut";
		String string = new String("anb c");
		Path pa = Paths.get(rut);
		System.out.println(pa.getNameCount());
		try {
			BasicFileAttributes attrs = Files.readAttributes(pa, BasicFileAttributes.class);
			Files.walkFileTree(pa, new SimpleFileVisitor<Path>() {
				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
					System.out.println("  ".repeat(file.getNameCount() - 1) + file.getName(file.getNameCount() - 1));
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {

					System.out.println("  ".repeat(dir.getNameCount() - 1) + dir.getName(dir.getNameCount() - 1));
					return FileVisitResult.CONTINUE;

				}
			});
		} catch (IOException e) {
			System.out.println("nije uspio");
		}
		try {
			Path p = Paths.get("../../rut/dva/0/test.txt");
			BufferedReader br = Files.newBufferedReader(p, Charset.defaultCharset());

			char[] buff = new char[16];

			while (true) {
				int i = br.read(buff);
				if (i != 16) {
					System.out.println(new String(buff, 0, i));
					break;
				}
				System.out.println(new String(buff));
			}
		} catch (IOException e) {
			System.out.println("nema filea");

		}

		/*
		 * System.out.println(String.format("%10d", 12351L)); try { SimpleDateFormat sdf
		 * = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); Path path =
		 * Paths.get("../../rut"); Files.walk(path, 1).filter(pat -> pat.getNameCount()
		 * > path.getNameCount()).map(pat ->
		 * pat.getFileName()).forEach(System.out::println); BasicFileAttributeView
		 * faView = Files.getFileAttributeView( path, BasicFileAttributeView.class,
		 * LinkOption.NOFOLLOW_LINKS ); BasicFileAttributes attributes =
		 * faView.readAttributes(); FileTime fileTime = attributes.creationTime();
		 * String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));
		 * System.out.println(formattedDateTime); } catch(IOException e) {
		 * System.out.println("nema filea"); }
		 */

		/*
		 * Path p = Paths.get("../../rut"); try { Files.walk(p, 1) .filter(path ->
		 * path.getNameCount() > p.getNameCount()) .forEach(path -> { try {
		 * 
		 * System.out.println(path);
		 * 
		 * if(Files.isDirectory(path)) { System.out.print("d"); } else {
		 * System.out.print("-"); } if(Files.isReadable(path)) { System.out.print("r");
		 * } else { System.out.print("-"); } if(Files.isWritable(path)) {
		 * System.out.print("w"); } else { System.out.print("-"); }
		 * if(Files.isExecutable(path)) { System.out.print("x"); } else {
		 * System.out.print("-"); }
		 * 
		 * BasicFileAttributeView faView = Files.getFileAttributeView( path,
		 * BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS );
		 * BasicFileAttributes attributes = faView.readAttributes();
		 * 
		 * System.out.print(String.format("%10d", attributes.size()));
		 * 
		 * FileTime fileTime = attributes.creationTime(); SimpleDateFormat sdf = new
		 * SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); String formattedDateTime =
		 * sdf.format(new Date(fileTime.toMillis())); System.out.print(" " +
		 * formattedDateTime + " "); System.out.println(path.getFileName().toString());
		 * } catch(SecurityException e) {
		 * System.out.println("You can't see this data, because of security."); }
		 * catch(IOException e) { System.out.println("Could not get attributes."); } });
		 * 
		 * } catch(IOException e) { System.out.println("Could not find file."); }
		 */

		/*
		 * Path input = Paths.get("../../rut/dva/0/test.txt"); Path output =
		 * Paths.get("../../rut/jen/test.txt"); if(Files.exists(input) == false) {
		 * System.out.println("File you want to copy doesn't exist."); return; }
		 * if(input.toFile().isFile() == false) {
		 * System.out.println("First argument is not file."); scan.close(); return; }
		 * if(output.toFile().isDirectory()) { output =
		 * output.resolve(input.getFileName()); } if(Files.exists(output)) {
		 * System.out.println("'" + output +
		 * "' already exists. Do you want to overwrite it? Type Yes/No.\n"+ ">" + " ");
		 * if(scan.nextLine().toLowerCase().equals("yes") == false) {
		 * System.out.println("RETURNED"); return; } }
		 * 
		 * InputStream is = null; OutputStream os = null; try { is =
		 * Files.newInputStream(input); os = Files.newOutputStream(output); byte[] buff
		 * = new byte[4096];
		 * 
		 * while (true) { int r = is.read(buff); if (r < 0) { break; } if (r == 4096) {
		 * os.write(buff); } else { os.write(buff, 0, r); } } is.close(); os.close(); }
		 * catch (IOException ex) {
		 * System.out.println("Problem occured while copying."); }
		 */

		Path input = Paths.get("../../rut/dva/0/test.txt");

		try {
			BufferedReader br = Files.newBufferedReader(input, Charset.defaultCharset());
			String line = br.readLine();
			String whatLeft = line;
			int lineNumber = 0;
			int firstNotDumpedIndex = 0;
			while (line != null) {
				while (line != null && whatLeft.length() < DATA_CHUNK) {
					line = br.readLine();
					if (line == null) {
						break;
					}
					whatLeft = whatLeft.substring(firstNotDumpedIndex) + line;
					firstNotDumpedIndex = 0;
				}

				if(whatLeft.isEmpty()) {
					break;
				}
				firstNotDumpedIndex = writeHexdump( whatLeft, lineNumber);
				lineNumber += (int) (whatLeft.length() / DATA_CHUNK * DATA_CHUNK);
				line = br.readLine();
				if(line == null) {
					
					whatLeft = whatLeft.substring(firstNotDumpedIndex);
					if(whatLeft.isEmpty() == false) {
						writeHexdump( whatLeft, lineNumber);
					}
					break;
				}
				whatLeft = whatLeft.substring(firstNotDumpedIndex) + line;
				firstNotDumpedIndex = 0;
			}
		} catch (IOException ex) {
			System.out.println("Problem occured while hexdumping.");
		}

	scan.close();
	}

	private static int writeHexdump( String line, int lineNumber) {
		int length = line.length();
		if(length < DATA_CHUNK) {
			writeLinenumber( lineNumber);
			for(int i=0; i < DATA_CHUNK; ++i) {
				if(i < length) {
					writeIthData( line, i);
				} else {
					System.out.print("   ");
				}
				if(i % DATA_CHUNK == DATA_CHUNK/2 - 1) {
					System.out.print("|");
				}
			}
			
			writeCharacters(line.substring(0));
			return length;
		} else {
	
			for(int i = 0; i < length/DATA_CHUNK * DATA_CHUNK;) {
				
				lineNumber = writeLinenumber( lineNumber);
				int chunk = i + 16;
				for(; i < chunk; ++i) {
					writeIthData( line, i);	
					if(i % DATA_CHUNK == DATA_CHUNK/2 - 1) {
						System.out.print("|");
					}
				}
				writeCharacters(line.substring(chunk-16, chunk));
			}
			return length/DATA_CHUNK * DATA_CHUNK;
		}
		
	}
	
	private static void writeCharacters(String string) {
		StringBuilder stringBuilder = new StringBuilder();
		for(int i = 0, length = string.length(); i < length; i++ ) {
			if(string.charAt(i)< 32 || string.charAt(i) > 127) {
				stringBuilder.append('.');
			} else {
				stringBuilder.append(string.charAt(i));
			}
		}
		System.out.println("| " + stringBuilder);
	}
	
	private static void writeIthData( String line, int i) {
		byte[] symbol = new byte[1];
		if(line.charAt(i) > 127 || line.charAt(i) < 32) {
			symbol[0] = (byte)'.';
			
		} else {
			symbol[0] = (byte)line.charAt(i);
		}
		
		String hex = Util.bytetohex(symbol).toUpperCase();
		if(i % DATA_CHUNK < DATA_CHUNK/2) {
			System.out.print(" " + hex);
		} else {
			System.out.print(hex + " ");
		}
	}
	
	private static int writeLinenumber( int lineNumber) {
		byte[] bytes = new byte[4];
	
		bytes[0] = (byte) ((lineNumber & 0xFF000000) >> 24);
		bytes[1] = (byte) ((lineNumber & 0x00FF0000) >> 16);
		bytes[2] = (byte) ((lineNumber & 0x0000FF00) >> 8);
		bytes[3] = (byte) (lineNumber & 0x000000FF);
		System.out.print(Util.bytetohex(bytes).toUpperCase() + ":");
		lineNumber += 16;
		return lineNumber;
	}
}
