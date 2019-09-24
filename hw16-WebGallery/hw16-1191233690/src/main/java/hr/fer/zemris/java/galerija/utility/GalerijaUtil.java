package hr.fer.zemris.java.galerija.utility;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletContext;

/**
 * Utility used for "Gallery" page.
 * 
 * @author Jelić, Nikola
 */
public class GalerijaUtil {

	/**
	 * Number of rows per image info.
	 */
	static final int ROWS_PER_IMAGE = 3;
	
	/**
	 * Modulo of the image name row number.
	 */
	static final int ROW_OF_IMAGE_NAME = 0;
	
	/**
	 * Modulo of the image tags row number.
	 */
	static final int ROW_OF_IMAGE_TAGS = 2;
	/**
	 * Returns list of tags from opisnik.txt.
	 * 
	 * @param req HttpServletRequest is used for getting real path.
	 * @return list of tags as strings
	 * @throws IOException if exception occurs while getting tags from file.
	 */
	public static String[] tagsList(ServletContext context) throws IOException {
		String[] lines = Files.readString(Paths.get(context.getRealPath("WEB-INF/opisnik.txt")))
				.split("\n");
		Set<String> set = new HashSet<String>();
		for (int i = ROW_OF_IMAGE_TAGS; i < lines.length; i += ROWS_PER_IMAGE) {
			String[] tags = lines[i].split(",");
			for (int j = 0; j < tags.length; j++) {
				set.add(tags[j].trim());
			}
		}
		lines = new String[set.size()];
		int i = 0;
		for (String s : set) {
			lines[i++] = s;
		}
		return lines;
	}

	/**
	 * Returns list of names of images with specific tag.
	 * 
	 * @param req HttpServletRequest is used for getting real path.
	 * @param tag used for searching images.
	 * @return list of images
	 * @throws IOException if exception occurs while getting tags from file.
	 */
	public static String[] imagesList(ServletContext context, String tag) throws IOException {
		
		String[] lines = Files.readString(Paths.get(context.getRealPath("WEB-INF/opisnik.txt")))
				.split("\n");
		Set<String> set = new HashSet<String>();
		for (int i = ROW_OF_IMAGE_TAGS; i < lines.length; i += ROWS_PER_IMAGE) {
			String[] tags = lines[i].split(",");
			for (int j = 0; j < tags.length; j++) {
				if (tags[j].trim().equals(tag)) {
					set.add(lines[i - 2]);
					break;
				}
			}
		}
		lines = new String[set.size()];
		int i = 0;
		for (String s : set) {
			lines[i++] = s;
		}
		return lines;
	}

	/**
	 * Returns list of informations about specific image.
	 * 
	 * @param req HttpServletRequest is used for getting real path.
	 * @param img specific image name used for searching informations.
	 * @return list of information.
	 * @throws IOException if exception occurs while getting tags from file.
	 */
	public static String[] infoList(ServletContext context, String img) throws IOException {
		String[] lines = Files.readString(Paths.get(context.getRealPath("WEB-INF/opisnik.txt")))
				.split("\n");

		for (int i = ROW_OF_IMAGE_NAME; i < lines.length; i += ROWS_PER_IMAGE) {
				if (lines[i].trim().equals(img)) {
					return new String[] { lines[i + 1], lines[i + 2] };
				}
		}
		return null;
	}
}
