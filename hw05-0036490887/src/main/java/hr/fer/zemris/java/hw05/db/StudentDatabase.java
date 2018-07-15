package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw05.collections.SimpleHashtable;
import hr.fer.zemris.java.hw05.db.exceptions.LikeOperatorException;

/**
 * Class {@link StudentDatabase} offers you to {@link #filter(IFilter) filter} 
 * all {@link StudentRecord students records} inside, and also to access specific 
 * with given jmbag {@link #forJMBAG(String)}.
 * 
 * @author dario
 * @version 1.0
 * @since 1.0
 *
 */
public class StudentDatabase {
	
	/**
	 * hash table of students records
	 */
	private SimpleHashtable<String, StudentRecord> hashTable;
	/**
	 * List of students records
	 */
	private StudentRecord[] records;
	/**
	 * Constructs {@link StudentDatabase} with the String[] as parameter.
	 * @param lines entries to the databases, which will be transfered to {@link StudentRecord}
	 */
	public StudentDatabase(String[] lines) {
		
		records = new StudentRecord[lines.length];
		hashTable = new SimpleHashtable<>();
		int cnt = 0;
		for(String line : lines) {
			String[] sep = split(line);
			StudentRecord record = new StudentRecord(
					sep[0], 
					sep[1], 
					sep[2], 
					sep[3]
			);
			hashTable.put(sep[0], record);
			records[cnt++] = record;
		}
	}
	/**
	 * Returns {@link StudentRecord} with the given jmbag.
	 * @param jmbag value by which we will find {@link StudentRecord}
	 * @return {@link StudentRecord} with the given jmbag
	 */
	public StudentRecord forJMBAG(String jmbag) {
		return (StudentRecord) hashTable.get(jmbag);
	}
	/**
	 * Returns list of {@link StudentRecord} by which {@link IFilter} says true.
	 * @param filter filter option 
	 * @return new list of {@link StudentRecord} filtered 
	 * @throws LikeOperatorException 
	 */
	public List<StudentRecord> filter(IFilter filter) throws LikeOperatorException {
		List<StudentRecord> result = new ArrayList<>();
		for(StudentRecord record : records) {
			if(filter.accepts(record)) {
				result.add(record);
			}
		}
		return result;
	}
	/**
	 * Splits string with the blanks as the separators.
	 * @param line which will be splited
	 * @return array as the result of split process
	 */
	private String[] split(String line) {
		List<String> list = new ArrayList<>();
		StringBuilder pom = new StringBuilder();
		for(int i = 0, len = line.length(); i < len; ++i) {
			if(isTab(line.charAt(i)) && pom.length()!=0) {
				list.add(pom.toString());
				pom = new StringBuilder();
			} else {
				pom.append(line.charAt(i));
			}
		}
		if(pom.length() != 0) {
			list.add(pom.toString());
		}
		return list.toArray(new String[0]);
	}
	/**
	 * Returns true if the character is tab character
	 * @param ch character which will be tested
	 * @return true if the ch is tab char, false otherwise
	 */
	private boolean isTab(char ch) {
		return ch == '\t';
	}
}
