package hr.fer.zemris.java.hw06.demo4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Demo class which execute 4. task from the homework.
 * @author dario
 *
 */
public class StudentDemo {
	/**
	 * location of document
	 */ 
	private static final String DOCUMENT_LOCATION = "./src/main/resources/studenti.txt";
	/**
	 * separator used in the document
	 */
	private static final String SEPARATOR_IN_DOCUMENT = "\t";
	
	/**
	 * Main method which execute eight methods specified in the task instruction.
	 * @param args not used
	 */
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		List<StudentRecord> records = null;
		try {
			List<String> lines = Files.readAllLines(Paths.get(DOCUMENT_LOCATION));
			records = convert(lines);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		long broj = vratiBodovaViseOd25(records);
		long broj5 = vratiBrojOdlikasa(records);
		List<StudentRecord> odlikasi = vratiListuOdlikasa(records);
		List<StudentRecord> odlikasiSortirano = vratiSortiranuListuOdlikasa(records);
		List<String> nepolozeniJMBAGovi = vratiPopisNepolozenih(records);
		Map<Integer, List<StudentRecord>> mapaPoOcjenama = razvrstajStudentePoOcjenama(records);
		Map<Integer, Integer> mapaPoOcjenama2 = vratiBrojStudenataPoOcjenama(records);
		Map<Boolean, List<StudentRecord>> prolazNeprolaz = razvrstajProlazPad(records);
	}
	/**
	 * Returns list of the student that have grade 5.
	 * @param records list of the students
	 * @return list of all students with the grade 5
	 */
	public static List<StudentRecord> vratiListuOdlikasa(List<StudentRecord> records) {
		return records.stream()
				      .filter((t) -> t.getOcjena() == 5)
				      .collect(Collectors.toList());
	}
	/**
	 * Returns map in which separates students, by if they fail or pass the course.
	 * @param records list of students
	 * @return map, if they pass or fail course to the list of that students
	 */
	public static Map<Boolean, List<StudentRecord>> razvrstajProlazPad(List<StudentRecord> records) {
		return records.stream()
				      .collect(Collectors.partitioningBy(
				    		  StudentRecord::getIfStudentPass,
				    		  Collectors.toList()));
		
				      
	}
	/**
	 * Returns map, grade to the number to students with that grade.
	 * @param records list of students
	 * @return map, grade to the number to students with that grade
	 */
	public static Map<Integer, Integer> vratiBrojStudenataPoOcjenama(List<StudentRecord> records) {
		return records.stream()
				      .collect(Collectors.toMap(
				    		  StudentRecord::getOcjena,
				    		  (x)->1,
				    		  (oldValue, newValue)->oldValue + newValue
				    		  ));
	}
	/**
	 * Returns map in which separates students by its grades to separate lists.  
	 * @param records list of students
	 * @return map in which student's grade maps to list of the students with it grade
	 */
	public static Map<Integer, List<StudentRecord>> razvrstajStudentePoOcjenama(List<StudentRecord> records) {
		return records.stream()
			      .collect(Collectors.groupingBy(StudentRecord::getOcjena));
			      
	}
	/**
	 * Returns list of students's jmbag with the grade 1
	 * @param records list of students
	 * @return list of students jmbag which have grade 1
	 */
	public static List<String> vratiPopisNepolozenih(List<StudentRecord> records) {
		return records.stream()
			      .filter((t) -> !t.getIfStudentPass())
			      .map((t)->t.getJmbag())
			      .sorted()
			      .collect(Collectors.toList());
	}
	/**
	 * Returns sorted list of students with the grade 5
	 * @param records list of students
	 * @return sorted list of students with the grade 5
	 */
	public static List<StudentRecord> vratiSortiranuListuOdlikasa(List<StudentRecord> records) {
		return records.stream()
			      .filter((t) -> t.getOcjena() == 5)
			      .sorted(
			    		  Collections.reverseOrder(
			    				  (x,y)->Double.valueOf(x.getTotalPoints()).compareTo(y.getTotalPoints())
			    		  ))
			      .collect(Collectors.toList());
	}
	/**
	 * Returns number of the students that have grade 5.
	 * @param records list of students
	 * @return number of students with grade 5
	 */
	public static long vratiBrojOdlikasa(List<StudentRecord> records) {
		return records.stream()
			      .filter((t) -> t.getOcjena() == 5)
			      .count();
	}
	/**
	 * Returns number of the students that have total points higher then 25.
	 * @param records list of students
	 * @return number of students with the total points higher then 25
	 */
	public static long vratiBodovaViseOd25(List<StudentRecord> records) {
		return records.stream()
			      .filter((t)->t.getTotalPoints() > 25)
			      .count();
	}
	/**
	 * Creates and returns list of {@link StudentRecord} from the list of {@link String}.
	 * @param lines lines from which we will parse into student's records 
	 * @return list of student's records
	 */
	public static List<StudentRecord> convert(List<String> lines) {
		List<StudentRecord> list = new ArrayList<>();
		for(String line : lines) {
			String[] sep = line.split(SEPARATOR_IN_DOCUMENT);
			list.add(new StudentRecord(sep[0], 
					                   sep[1], 
					                   sep[2], 
					                   Double.parseDouble(sep[3]), 
					                   Double.parseDouble(sep[4]), 
					                   Double.parseDouble(sep[5]), 
					                   Integer.parseInt(sep[6]))); 
		}
		return list;
	}
}
