package hr.fer.zemris.java.hw05.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import hr.fer.zemris.java.hw05.db.exceptions.LikeOperatorException;
import hr.fer.zemris.java.hw05.db.exceptions.MissingKeywordQuery;
import hr.fer.zemris.java.hw05.db.exceptions.ParserException;
/**
 * Class {@link StudentDB} offers you to make query to the database and get result
 * from it. Query begins with the query as the keyword and after that it comes expression.
 * If the expression is valid, the result will be printed, otherwise the error message will be printed.
 * When you want to exit you can simply type keyword exit. 
 * @author dario
 * @version 1.0
 * @since 1.0
 *
 */
public class StudentDB {
	/**
	 * final string query, used as keyword in queries
	 */
	private static final String QUERY = "query";
	/**
	 * final string, used in exiting application
	 */
	private static final String GOODBYE = "Goodbye!";
	/**
	 * location of the database
	 */
	private static final String DATABASE = "./database.txt";
	/**
	 * keyword for exiting the application
	 */
	private static final String EXIT = "exit";
	/**
	 * Method main reads line from the standard input, process it and output the result of the query. 
	 * @param args not used in this method
	 */
	public static void main(String[] args) {
		if(args.length != 0) {
			System.out.println("You shouldn't use arguments.\nPlease run it without it.");
			System.exit(1);
		}
		Scanner scanner = new Scanner(System.in);
		List<StudentRecord> records = new ArrayList<>();
		String[] lines = null;
		try {
			lines = Files.readAllLines(
					Paths.get(DATABASE),
					StandardCharsets.UTF_8
					).toArray(new String[0]);
		} catch (IOException e) {e.printStackTrace();}
		
		StudentDatabase db = new StudentDatabase(lines);
		
		while(true) {
			System.out.printf("> ");
			String line = scanner.nextLine();
			if(line.compareTo(EXIT) == 0) {
				System.out.println(GOODBYE);
				scanner.close();
				return;
			}
			try {
				line = removeQuery(line);
			} catch(MissingKeywordQuery e) {
				System.out.println(e.getMessage());
				continue;
			}
			QueryParser parser = null;
			try {
				parser = new QueryParser(line);
			} catch(ParserException ex) {
				System.out.println(ex.getMessage());
				continue;
			}
			
			if(parser.isDirectQuery()) {
				StudentRecord record = db.forJMBAG(parser.getQueriedJMBAG());
				records.add(record);
				
			} else {
				try {
					for(StudentRecord record : db.filter(new QueryFilter(parser.getQuery()))) {
						records.add(record);
					}
				} catch (LikeOperatorException e) {
					System.out.println(e.getMessage());
					continue;
				}
			}
			int jmbag = 0;
			int firstName = 0;
			int lastName = 0;
			int finalGrade = 0;
			for(StudentRecord record : records) {
				jmbag = Math.max(jmbag, record.getJmbag().length());
				firstName = Math.max(firstName, record.getFirstName().length());
				lastName = Math.max(lastName, record.getLastName().length());
				finalGrade = Math.max(finalGrade, record.getFinalGrade().length());
			}
			System.out.println(getHrLine(jmbag+1, firstName+1, lastName+1, finalGrade+1));
			for(StudentRecord record : records) {
				System.out.println(record.toString(jmbag+1,lastName+1,firstName+1,finalGrade+1));
			}
			System.out.println(getHrLine(jmbag+1, firstName+1, lastName+1, finalGrade+1));
			System.out.printf("Records selected: %d%n",records.size());
			System.out.printf("%n");
			records.clear();
			
		}
		
	}
	/**
	 * Prints horizontal rule with the specific size given as parameters
	 * @param jmbag size of jmbag section
	 * @param firstName size of firstName section
	 * @param lastName size of lastName section
	 * @param finalGrade size of finalGrade section
	 * @return string horizontal rule with specific rules mentioned above
	 */
	private static String getHrLine(int jmbag, int firstName, int lastName, int finalGrade) {
		StringBuilder builder = new StringBuilder();
		builder.append("+");
		for(int i=0;i<jmbag+1;++i) builder.append('-');
		builder.append("+");
		for(int i=0;i<lastName+1;++i) builder.append('-');
		builder.append("+");
		for(int i=0;i<firstName+1;++i) builder.append('-');
		builder.append("+");
		for(int i=0;i<finalGrade+1;++i) builder.append('-');
		builder.append("+");
		return builder.toString();
	}
	/**
	 * Removes keyword query from the query
	 * @param line from which string will keyword query be removed
	 * @return new query without it
	 */
	private static String removeQuery(String line) {
		String result = "";
		boolean first = true;
		for(int i=0;i < line.length(); ++i) {

			if(first && i + QUERY.length() < line.length() && line.substring(i, i + QUERY.length()).compareTo(QUERY) == 0) {
				i+= QUERY.length();
				first = false;
			} else {
				result += line.charAt(i);
			}
		}
		if(first) throw new MissingKeywordQuery();
		return result;
	}
}