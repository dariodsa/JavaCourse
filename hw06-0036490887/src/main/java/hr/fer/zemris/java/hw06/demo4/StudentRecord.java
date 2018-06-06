package hr.fer.zemris.java.hw06.demo4;

/**
 * Class {@link StudentRecord} encapsulate data that are needed for representing
 * student's data, like jmbag as their id, first name, last name and others.
 * Default getters are offered and all attributes are read-only so there are set
 * methods. This class implements {@link Comparable} interface.
 * 
 * @author dario
 * @version 1.0
 * @since 1.0
 *
 */
public class StudentRecord implements Comparable<StudentRecord> {
	/**
	 * student's jmbag
	 */
	private String jmbag;
	/**
	 * student's last name
	 */
	private String lastName;
	/**
	 * student's first name
	 */
	private String firstName;
	/**
	 * student's points from middterm exam
	 */
	private double meduIspitBodovi;
	/**
	 * student's points from final exam
	 */
	private double zavrsniIspitBodovi;
	/**
	 * student's points from laboratory exam
	 */
	private double labosBodovi;
	/**
	 * student's final grade
	 */
	private int ocjena;

	/**
	 * Constructs {@link StudentRecord} with the specified parameters.
	 * 
	 * @param jmbag
	 *            student's jmbag
	 * @param lastName
	 *            student's last name
	 * @param firstName
	 *            student's first name
	 * @param meduIspitBodovi
	 *            student's midterm points
	 * @param zavrsniIspitBodovi
	 *            student's final exam points
	 * @param labosBodovi
	 *            student's points from laboartory exam
	 * @param ocjena
	 *            student's final grade
	 */
	public StudentRecord(String jmbag, String lastName, String firstName, double meduIspitBodovi,
			double zavrsniIspitBodovi, double labosBodovi, int ocjena) {
		super();
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.meduIspitBodovi = meduIspitBodovi;
		this.zavrsniIspitBodovi = zavrsniIspitBodovi;
		this.labosBodovi = labosBodovi;
		this.ocjena = ocjena;
	}

	/**
	 * Returns student's jmbag.
	 * 
	 * @return {@link String} jmbag of student
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * Returns student's last name.
	 * 
	 * @return {@link String} last name of student
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Returns student's first name.
	 * 
	 * @return {@link String} first name of student
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Returns student's points from the midterm exam.
	 * 
	 * @return points from middterm exam
	 */
	public double getMeduIspitBodovi() {
		return meduIspitBodovi;
	}

	/**
	 * Returns student's points from the final exam.
	 * 
	 * @return double points from final exam
	 */
	public double getZavrsniIspitBodovi() {
		return zavrsniIspitBodovi;
	}

	/**
	 * Returns student's points from the laboratory exam.
	 * 
	 * @return double points from laboratory exam
	 */
	public double getLabosBodovi() {
		return labosBodovi;
	}

	/**
	 * Returns student's grade.
	 * 
	 * @return int grade of student
	 */
	public int getOcjena() {
		return ocjena;
	}

	/**
	 * Returns total number of points of the student.
	 * 
	 * @return sum of the points from lab exams, midterm and final exam
	 */
	public double getTotalPoints() {
		return this.labosBodovi + this.zavrsniIspitBodovi + this.meduIspitBodovi;
	}

	/**
	 * Returns true if student pass the exam, or if the final grade is greater then
	 * one
	 * 
	 * @return true if the student's grade is greater then one, false otherwise
	 */
	public boolean getIfStudentPass() {
		return this.getOcjena() > 1;
	}

	/**
	 * Compares two students by comparing their jmbag's. In comparing the jmbag's we
	 * use natural order.
	 * 
	 * @return int zer0 if they are equal, negative number if first is lower then
	 *         second and positive if first is greater then second
	 */
	@Override
	public int compareTo(StudentRecord other) {
		return this.getJmbag().compareTo(other.getJmbag());
	}

	/**
	 * Returns hashcode of {@link StudentRecord} by using only their jmbag hashcode.
	 * 
	 * @return int hash code of student record
	 * @see java.lang.String#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jmbag == null) ? 0 : jmbag.hashCode());
		return result;
	}

	/**
	 * Returns true if the given student record is equal to the object, false
	 * otherwise. They are compared by looking at their jmbags.
	 * 
	 * @return true if the two objects are equal, false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudentRecord other = (StudentRecord) obj;
		if (jmbag == null) {
			if (other.jmbag != null)
				return false;
		} else if (!jmbag.equals(other.jmbag))
			return false;
		return true;
	}

}
