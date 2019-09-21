package hr.fer.zemris.java.GlasanjeUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * Utility used for servlets of Glasanje type.
 * 
 * @author Jelić, Nikola
 */
public class Util {
	
	/**
	 * Class of pairs.
	 * 
	 * @author Jelić, Nikola
	 *
	 * @param <K>
	 * @param <V>
	 */
	public static class Pair<K, V> {
		/**
		 * First element of pair.
		 */
		public K key;
		/**
		 * Second element of pair.
		 */
		public V value;
		/**
		 * Base constructor
		 * @param key first element of pair
		 * @param value second element of pair
		 */
		public Pair(K key, V value) {
			this.key = key;
			this.value = value;
		}
		
		@Override
		public int hashCode() {
			return Objects.hash(key, value);
		}
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			@SuppressWarnings("unchecked")
			Pair<K, V> other = (Pair<K,V>) obj;
			return Objects.equals(key, other.key) && Objects.equals(value, other.value);
		}		
	}
	
	/**
	 * This method creates "glasanje-rezultati.txt" if doesn't exist.
	 * 
	 * @param path path of "glasanje-rezultati.txt"
	 * @param path2 path of "glasanje-definicija.txt"
	 * @throws IOException if occurs while reading or writing strings. 
	 */
	public static void createFile(Path path, Path path2) throws IOException {
		String[] array = Files.readString(path2).split("\n");
		StringBuilder sb = new StringBuilder();
		
		for(Integer i = 0; i < array.length; i++) {
			String[] song = array[i].split("\t");
			sb.append(song[0]+"\t0\n");
		}
		if(array.length > 0) {
			sb.deleteCharAt(sb.length()-1);
		}
		Files.writeString(path, sb.toString());
	}
	
	/**
	 * Reads "glasanje-definicija.txt" file and returns array of lines.
	 * @param req request used for getting path
	 * @return array of lines.
	 * @throws IOException if Exception occurs while reading strings.
	 */
	public static String[] readDefinition(HttpServletRequest req) throws IOException {
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
		String[] array = Files.readString(Paths.get(fileName)).split("\n");
		return array;
	}
	
	/**
	 * Reads "glasanje-rezultati.txt" file and returns array of lines or creates new one if doesn't exist.
	 * 
	 * @param req request used for getting path
	 * @return array of lines.
	 * @throws IOException if Exception occurs while reading strings.
	 */
	public static String[] readResults(HttpServletRequest req) throws IOException {
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		Path path = Paths.get(fileName);
		if(!Files.exists(path)) {
			Path path2 = Paths.get(req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt"));
			createFile(path, path2);
		}
		String[] str = Files.readString(path).split("\n");
		return str;
	}
	
	/**
	 * Returns list of votes per author.
	 * 
	 * @param req request used for getting path.
	 * @return list of authors and theirs votes.
	 * @throws IOException if occurs while reading file.
	 */
	public static List<Pair<String, Integer>> authorsAndVotes(HttpServletRequest req) throws IOException {
		String[] results = Util.readResults(req);
		String[] definition = Util.readDefinition(req);
		List<Pair<String, Integer>> list = new ArrayList<>();
		
		for(Integer i = 0; i < results.length; i++) {
			String[] votes = results[i].split("\t");
			for(Integer j = 0; j < definition.length; j++) {
				String[] songs = definition[j].split("\t");
				if(votes[0].equals(songs[0])) {
					try {
						Integer value = Integer.parseInt(votes[1]);
						list.add(new Pair<>(songs[1], value));
					} catch(NumberFormatException e) {	
					}
				}
			}
		}
		return list;
	}
}
