package hr.fer.zemris.java.hw17.trazilica;

import java.util.Map;
import java.util.Map.Entry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.charset.StandardCharsets;

/**
 * This is an example of search engine. It needs to have stopwords as
 * "zaustavneRijeci.txt" and some articles as "clanci".
 * 
 * @author Jelić, Nikola
 */
public class Konzola {

	/**
	 * stopwords, which will be removed from vocabulary.
	 */
	private static final String stopwordsFile = "zaustavneRijeci.txt";

	/**
	 * epsilon is used to decide if number is equal to zero
	 */
	private static final Double EPSILON = 1E-7;

	/**
	 * vocabulary sets words as key and indices as values
	 */
	private static Map<String, Integer> vocabulary = new HashMap<>();
	
	/**
	 * list of all term frequencies
	 */
	private static List<Integer[]> termFrequencies = new ArrayList<>();

	/**
	 * term frequencies - inverse document frequency
	 */
	private static List<Double[]> TFIDFs = new ArrayList<>();

	/**
	 * list of paths of the articles
	 */
	private static List<Path> listOfPaths = new ArrayList<>();

	/**
	 * inverse document frequencies
	 */
	private static Double[] inverseDocumentFrequency;

	/**
	 * temporary result values of the query, which are in pair with resultPath
	 */
	private static List<Double> resultValues;

	/**
	 * temporary result paths of the query
	 */
	private static List<Path> resultPaths;

	/**
	 * Main method starts the console. Needs only one argument. Path of articles.
	 *  
	 * @param args needs only one argument - path of articles
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Expected only one argumet: path to directroy.");
			return;
		}
		Path path = Paths.get(args[0]);
		
		if (!Files.exists(path) || !Files.isDirectory(path)) {
			System.out.println("Given path doesn't exist or is not directory.");
			return;
		}

		try {
			Set<String> vocabularyBuilder = createVocabulary(path);
			
			Integer index = 0;
			for (String word : vocabularyBuilder) {
				vocabulary.put(word, index++);
			}
			System.out.println("Size of vocabulary is " + index + ".");
			System.out.println("\n-------------------------------------\n");
			readTermFrequencies(path);
			createIDF();
			createTFIDFs();
			userCommunication();
		} catch (IOException e) {
			System.out.println("Error occured while reading files: " + e.getMessage());
		}
	}

	/**
	 * This method is used for communication with user.
	 */
	private static void userCommunication() {
		boolean queried = false;
		Scanner sc = new Scanner(System.in);
		while (true) {
			System.out.print("Enter command > ");
			String[] input = sc.nextLine().split(" ");
			if (input.length == 0) {
				System.out.println("You didn't enter command.");
				continue;
			}

			switch (input[0]) {
			case "query":
				queried = true;
				processQuery(input);
				break;
			case "exit":
				sc.close();
				return;
			case "results":
				if (!queried) {
					System.out.println("You need to query first.");
					break;
				}
				writeResults();
				break;
			case "type":
				if (!queried) {
					System.out.println("You need to query first.");
					break;
				}
				processType(input);
				break;
			default:
				System.out.println("You entered wrong command.");
			}
			System.out.println("\n-------------------------------------\n");
		}
	}

	/**
	 * This method process the "type" command. Writes article if number as argument is right.
	 * 
	 * @param input command "type" and one number.
	 */
	private static void processType(String[] input) {
		if (input.length != 2) {
			System.out.println("You should enter two arguments: 'type' and integer.");
			return;
		}
		try {
			Integer index = Integer.parseInt(input[1]);
			if (index < 0 || resultValues.size() <= index) {
				System.out.println("You have entered wrong index.");
				return;
			}
			List<String> text = Files.readAllLines(resultPaths.get(index), StandardCharsets.UTF_8);
			for (String t : text) {
				System.out.println(t);
			}
		} catch (NumberFormatException | IOException e) {
			System.out.println("Error occured or you have entered unparsable argumet. Should be integer.");
		}
	}

	/**
	 * Process command "query" which can have arbitrary number of arguments.
	 * @param input complete row as input.
	 */
	private static void processQuery(String[] input) {

		Double[] vector = vectorizeAndPrintQuery(input);

		Map<Path, Double> similarities = new HashMap<>();
		int index = 0;
		for (Double[] tfidf : TFIDFs) {
			Double tfidfNorm = Math.sqrt(dotProduct(tfidf, tfidf));
			Double vectorNorm = Math.sqrt(dotProduct(vector, vector));
			if (tfidfNorm < EPSILON || vectorNorm < EPSILON) {
				similarities.put(listOfPaths.get(index++), 0.0);
			} else {
				similarities.put(listOfPaths.get(index++), dotProduct(tfidf, vector) / (tfidfNorm * vectorNorm));
			}
		}

		List<Entry<Path, Double>> list = sortedList(similarities);

		resultPaths = new ArrayList<Path>();
		resultValues = new ArrayList<Double>();
		index = 0;
		for (Entry<Path, Double> e : list) {
			if (index == 10 || e.getValue() < EPSILON) {
				break;
			}
			resultPaths.add(e.getKey());
			resultValues.add(e.getValue());
			index++;
		}

		System.out.println("Best results:");
		writeResults();
	}

	/**
	 * Vectorize query and prints out to the console.
	 * 
	 * @param input complete row as input.
	 * @return vector of query as numbers
	 */
	private static Double[] vectorizeAndPrintQuery(String[] input) {
		System.out.print("Query is: [");
		int index = 1;
		Double[] vector = new Double[vocabulary.size()];
		for (int i = 0; i < vector.length; i++) {
			vector[i] = 0.0;
		}
		for (; index < input.length - 1; index++) {
			input[index].toLowerCase();
			if (vocabulary.containsKey(input[index])) {
				vector[vocabulary.get(input[index])]++;
				System.out.print(input[index] + ", ");
			}
		}
		if (input.length > 1) {
			input[index].toLowerCase();
			if (vocabulary.containsKey(input[index])) {
				vector[vocabulary.get(input[index])]++;
				System.out.print(input[index]);
			}
		}
		System.out.println("]");
		return vector;
	}

	/**
	 * Writes results to the console.
	 */
	private static void writeResults() {
		int size = resultPaths.size();
		for (int index = 0; index < size; index++) {
			System.out.format("[%d] (%.4f) %s%n", index, resultValues.get(index), resultPaths.get(index));
		}
	}

	/**
	 * Descending sort of similarities. Bigger number means better similarity.  
	 * @param similarities that needs to be sorted.
	 * @return sorted list of entries.
	 */
	private static List<Entry<Path, Double>> sortedList(Map<Path, Double> similarities) {
		Set<Entry<Path, Double>> entrySet = similarities.entrySet();
		List<Entry<Path, Double>> entryList = new ArrayList<Map.Entry<Path, Double>>();
		for (Entry<Path, Double> e : entrySet) {
			entryList.add(e);
		}
		entryList.sort((e1, e2) -> {
			if (e2.getValue() - e1.getValue() < 0)
				return -1;
			else if (Math.abs(e2.getValue() - e1.getValue()) < EPSILON)
				return 0;
			else
				return 1;
		});
		return entryList;
	}

	/**
	 * Calculates dot product of two vectors.
	 * @param v1 first vector
	 * @param v2 second vector
	 * @return result as dot product
	 */
	private static Double dotProduct(Double[] v1, Double[] v2) {
		Double value = 0.0;
		if (v1.length != v2.length) {
			return null;
		}
		for (int index = 0; index < v1.length; index++) {
			value += v1[index] * v2[index];
		}
		return value;
	}

	/**
	 * Creates TFIDFs from term frequencies and inverse document frequency.
	 */
	private static void createTFIDFs() {
		for (Integer[] tf : termFrequencies) {
			Double[] tfidf = new Double[tf.length];
			for (int index = 0; index < tf.length; index++) {
				tfidf[index] = tf[index] * inverseDocumentFrequency[index];
			}
			TFIDFs.add(tfidf);
		}
	}

	/**
	 * Creates IDF from inverse document frequency and term frequencies.
	 */
	private static void createIDF() {
		inverseDocumentFrequency = new Double[vocabulary.size()];
		for (int index = 0; index < inverseDocumentFrequency.length; index++) {
			inverseDocumentFrequency[index] = 0.0;
		}
		for (Integer[] vec : termFrequencies) {
			for (int index = 0; index < inverseDocumentFrequency.length; index++) {
				if (vec[index] > 0) {
					inverseDocumentFrequency[index]++;
				}
			}
		}
		int size = termFrequencies.size();
		for (int index = 0; index < inverseDocumentFrequency.length; index++) {
			inverseDocumentFrequency[index] = Math.log(size / inverseDocumentFrequency[index]);
		}
	}

	/**
	 * Creates term frequencies and pairs with paths of files.
	 * @param path of articles
	 * @throws IOException if error occurs while reading files.
	 */
	private static void readTermFrequencies(Path path) throws IOException {
		Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				Integer[] termFrequncy = new Integer[vocabulary.size()];
				for (int i = 0; i < termFrequncy.length; i++) {
					termFrequncy[i] = 0;
				}
				readAllWordsFromFile(file, (str) -> {
					if (vocabulary.containsKey(str)) {
						termFrequncy[vocabulary.get(str)]++;
					}
				});
				termFrequencies.add(termFrequncy);
				listOfPaths.add(file);
				return FileVisitResult.CONTINUE;
			}

		});
	}

	/**
	 * Starting method for creating vocabulary.
	 * @param path of the articles
	 * @return vocabularyBuilder which contain all relevant words.
	 * @throws IOException if error occurs while reading file.
	 */
	private static Set<String> createVocabulary(Path path) throws IOException {
		Set<String> vocabularyBuilder = new HashSet<String>();
		Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				readAllWordsFromFile(file, (str) -> vocabularyBuilder.add(str));
				return FileVisitResult.CONTINUE;
			}

		});
		clearStopwords(path, vocabularyBuilder);
		return vocabularyBuilder;
	}

	/**
	 * Reads all words from a file.
	 * @param path of a file.
	 * @param recordWord used for specific recording.
	 * @throws IOException if error occurs while reading file.
	 */
	private static void readAllWordsFromFile(Path path, Consumer<String> recordWord) throws IOException {
		if (Files.isDirectory(path)) {
			return;
		}
		if (!Files.isReadable(path)) {
			return;
		}
		List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
		for (String line : lines) {
			readWordsFromLine(line, recordWord);
		}
	}

	/**
	 * Parses words from line with word consumer.
	 * @param line row of text
	 * @param recordWord used for various strings recording.
	 */
	private static void readWordsFromLine(String line, Consumer<String> recordWord) {
		int index = 0;
		int length = line.length();

		while (index < length) {
			StringBuilder sb = new StringBuilder();
			Character character = line.charAt(index++);
			while (index < length && Character.isAlphabetic(character)) {
				sb.append(character);
				character = line.charAt(index++);
			}
			if (sb.length() != 0) {
				recordWord.accept(sb.toString().toLowerCase());
			}
		}
	}

	/**
	 * Removes stopwords from vocabularyBuilder.
	 * @param path to stopwords file.
	 * @param vocabularyBuilder 
	 * @throws IOException if error occurs while reading file of stopwords.
	 */
	private static void clearStopwords(Path path, Set<String> vocabularyBuilder) throws IOException {
		path = path.getParent().resolve(stopwordsFile);
		
		List<String> stopwords = Files.readAllLines(path, StandardCharsets.UTF_8);
		
		for (String word : stopwords) {
			vocabularyBuilder.remove(word);
		}
	}
}
