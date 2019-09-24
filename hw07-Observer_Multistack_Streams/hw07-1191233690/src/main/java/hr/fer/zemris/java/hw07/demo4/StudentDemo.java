package hr.fer.zemris.java.hw07.demo4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Map;

/**
 * This class demonstrates usage of stream and {@link StudentRecord}.
 * */
public class StudentDemo {

	/**
	 * Main method
	 * */
	public static void main(String[] args) {
		List<String> lines;
		try {
			lines = Files.readAllLines(Paths.get("./studenti.txt"));
		} catch(IOException e) {
			System.out.println("File not found");
			return;
		}
		List<StudentRecord> records = convert(lines);
		
		System.out.println("Zadatak 1\n=========");
		System.out.println(vratiBodovaViseOd25(records));
		
		System.out.println("\nZadatak 2\n=========");
		System.out.println(vratiBrojOdlikasa(records));
		
		System.out.println("\nZadatak 3\n=========");
		List<StudentRecord> list = vratiListuOdlikasa(records);
		for(StudentRecord r:list) {
			System.out.println(r);
		}
		
		System.out.println("\nZadatak 4\n=========");
		list = vratiSortiranuListuOdlikasa(records);
		for(StudentRecord r:list) {
			System.out.println(r);
		}
		
		System.out.println("\nZadatak 5\n=========");
		List<String> listString = vratiPopisNepolozenih(records);
		for(String str:listString) {
			System.out.println(str);
		}
		
		System.out.println("\nZadatak 6\n=========");
		Map<Integer, List<StudentRecord>> map = razvrstajStudentePoOcjenama(records);
		for(Integer grade : map.keySet()) {
			System.out.println("Ocjena " + grade);
			list = map.get(grade);
			for(StudentRecord r:list) {
				System.out.println(r);
			}
		}
		
		System.out.println("\nZadatak 7\n=========");
		Map<Integer, Integer> map2 = vratiBrojStudenataPoOcjenama(records);
		System.out.println(map2);
		
		System.out.println("\nZadatak 8\n=========");
		Map<Boolean, List<StudentRecord>> map3 = razvrstajProlazPad(records);
		for(Boolean grade : map3.keySet()) {
			System.out.println("Prolaz " + grade);
			list = map3.get(grade);
			for(StudentRecord r:list) {
				System.out.println(r);
			}
		}
	}
	
	/**
	 * Converts lines to StudentRecords.
	 * @param lines list of strings
	 * @param records list of StudentRecords
	 * */
	private static List<StudentRecord> convert(List<String> lines) {
		List<StudentRecord> records = new ArrayList<StudentRecord>();
		try {
			for(String line : lines) {
				String [] items = line.split("\\s+");
				records.add(new StudentRecord(
						items[0], 
						items[1], 
						items[2], 
						Double.parseDouble(items[3]), 
						Double.parseDouble(items[4]), 
						Double.parseDouble(items[5]), 
						Integer.parseInt(items[6])
						));
			}
		}catch(IndexOutOfBoundsException e) {
			System.out.println("Exception occured: " + e.getMessage() + "\nline read: " + records.size());
		}
		return records;
	}
	
	/**
	 * Counts number of students that have more than 25 points.
	 * @param records of StudentRecord
	 * @return count number of students
	 * */
	private static long vratiBodovaViseOd25(List<StudentRecord> records) {
		return records.stream()
				.filter(student -> {
					return student.getBodoviKolokvij() + student.getBodoviVjezbe() + student.getBodoviZavrsni() >= 25;
				})
				.count();
	}
	
	/**
	 * Counts number of students that have A.
	 * @param records of StudentRecord
	 * @return count number of students
	 * */
	private static long vratiBrojOdlikasa(List<StudentRecord> records) {
		return records.stream()
				.filter(student -> student.getOcjena() == 5)
				.count();
	}
	
	/**
	 * Returns list of students that have A.
	 * @param records of StudentRecord
	 * @return list of students that have A.
	 * */
	private static List<StudentRecord> vratiListuOdlikasa(List<StudentRecord> records) {
		return records.stream()
				.filter(student -> student.getOcjena() == 5)
				.collect(Collectors.toList());
	}
	
	/**
	 * Returns sorted list of students that have A.
	 * @param records of StudentRecord
	 * @return sorted list of students that have A.
	 * */
	private static List<StudentRecord> vratiSortiranuListuOdlikasa(List<StudentRecord> records) {
		return vratiListuOdlikasa(records).stream()
				.sorted((s1, s2) -> {
					Double sumS1 = s1.getBodoviKolokvij() + s1.getBodoviVjezbe() + s1.getBodoviZavrsni();
					Double sumS2 = s2.getBodoviKolokvij() + s2.getBodoviVjezbe() + s2.getBodoviZavrsni();
					return sumS2.compareTo(sumS1); 
				})
				.collect(Collectors.toList());
	}
	
	/**
	 * Returns sorted list of jmbags of students that have F.
	 * @param records of StrudentRecords
	 * @return sorted list of jmbags of students that have F
	 * */
	private static List<String> vratiPopisNepolozenih(List<StudentRecord> records) {
		return records.stream()
				.filter(s -> (s.getOcjena() == 1))
				.map(student -> student.getJmbag())
				.sorted((s1, s2) -> s1.compareTo(s2))
				.collect(Collectors.toList());
	}
	
	/**
	 * Returns map with keys of grades and values of student list with that grades.
	 * @param records of StrudentRecords
	 * @return map with keys of grades and values of student list with that grades
	 * */
	private static Map<Integer, List<StudentRecord>> razvrstajStudentePoOcjenama(List<StudentRecord> records) {
		return records.stream()
				.collect(Collectors.groupingBy(StudentRecord::getOcjena));
	}
	
	
	/**
	 * Returns map with keys of grades and number of students with that grades.
	 * @param records of StrudentRecords
	 * @return map with keys of grades and number of students with that grades.
	 * */
	private static Map<Integer, Integer> vratiBrojStudenataPoOcjenama(List<StudentRecord> records) {
		return records.stream()
				.collect(Collectors.toMap(StudentRecord::getOcjena, e->1, (s, e) -> s+e));
	}
	
	/**
	 * Returns map that has keys true/false with values of student list respect to passing exams.
	 * @param records of StrudentRecords
	 * @return map that has keys true/false with values of student list respect to passing exams.
	 * */
	private static Map<Boolean, List<StudentRecord>> razvrstajProlazPad(List<StudentRecord> records) {
		return records.stream()
				.collect(Collectors.partitioningBy(s -> (s.getOcjena()>=2)));
	}
}
