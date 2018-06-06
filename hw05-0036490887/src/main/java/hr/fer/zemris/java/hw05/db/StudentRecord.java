package hr.fer.zemris.java.hw05.db;
/**
 * {@link StudentRecord} offers you to create record which will be saved in the {@link StudentDatabase}. 
 * It offers you to access given attributes, and also to have string respresentation of it. 
 * Method {@link #equals(Object)} compares jmbag, and {@link #hashCode()} returns hash code of jmbag also.
 * 
 * @author dario
 *
 */
public class StudentRecord {
	
	/**
	 * student's jmbag
	 */
	private String jmbag;
	/**
	 * student's first name
	 */
	private String firstName;
	/**
	 * student's last name
	 */
	private String lastName;
	/**
	 * student's final grade
	 */
	private String finalGrade;
	
	/**
	 * Constructs {@link StudentRecord} with {@link String} jmbag, first name, lastname and 
	 * final grade as parameters. 
	 * @param jmbag student's jmbag
	 * @param firstName student's first name
	 * @param lastName student's last name
	 * @param finalGrade student's final grade
	 */
	public StudentRecord(String jmbag, String lastName, String firstName, String finalGrade) {
		this.jmbag = jmbag;
		this.firstName = firstName;
		this.lastName = lastName;
		this.finalGrade = finalGrade;
	}
	/**
	 * Returns student's jmbag
	 * @return {@link String} jmbag of student
	 */
	public String getJmbag() {
		return jmbag;
	}
	/**
	 * Returns student's first name
	 * @return {@link String} first name of a student
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * Returns student's last name
	 * @return {@link String} last name of a student
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * Returns student's final grade
	 * @return {@link String} final grade of a student
	 */
	public String getFinalGrade() {
		return finalGrade;
	}
	/**
	 * Compares two {@link StudentRecord} by their jmbag property.
	 * @return boolean true if two {@link StudentRecord} have equal jmbag property
	 */
	@Override
	public boolean equals(Object o) {
		if(o instanceof StudentRecord) {
			return ((StudentRecord)o).jmbag.compareTo(jmbag) == 0;
		} else { 
			throw new ClassCastException("Couldn't cast it to StudentRecord.");
		}
	}
	/**
	 * Returns the hashcode of {@link StudentRecord} which is 
	 * actually hashcode of jmbag property. 
	 * @return int hashcode of jmbag property
	 */
	@Override
	public int hashCode() {
		return jmbag.hashCode();
	}
	/**
	 * Returns string representation of the {@link StudentRecord} 
	 * @param jmbag maximum size of jmbag field
	 * @param lastName maximum size of lastName field
	 * @param firstName maximum size of firstName field
	 * @param grade maximum size of grade field
	 * @return {@link String} with jmbag with given size, and also others mentioned above 
	 */
	public String toString(int jmbag, int lastName, int firstName, int grade) {
		StringBuilder builder = new StringBuilder();
		builder.append("| ");
		builder.append(getJmbag());
		builder.append(spaces(jmbag - getJmbag().length()));
		
		builder.append("| ");
		builder.append(getLastName());
		builder.append(spaces(lastName - getLastName().length()));
		
		builder.append("| ");
		builder.append(getFirstName());
		builder.append(spaces(firstName - getFirstName().length()));
		
		builder.append("| ");
		builder.append(getFinalGrade());
		builder.append(spaces(grade - getFinalGrade().length()));
		
		builder.append("|");
		return builder.toString();
	}
	/**
	 * Returns spaces n time
	 * @param n how many spaces we want
	 * @return string n times space
	 */
	private String spaces(int n) {
		StringBuilder builder = new StringBuilder();
		while(n-->0) builder.append(" ");
		return builder.toString();
	}
}
