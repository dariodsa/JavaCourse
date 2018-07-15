package hr.fer.zemris.java.hw05.db;

import hr.fer.zemris.java.hw05.db.exceptions.LikeOperatorException;

/**
 * Class {@link ComparisonOperators} consist of static final {@link IComparisonOperator} attributes.
 * These attributes implements basic operations such as {@link #LESS}, {@link #GREATER}
 * , {@link #EQUALSQ} , {@link #LIKE} and many others.
 * @author dario
 *
 */
public class ComparisonOperators {
	/**
	 * Returns true if the first string is lexicographically lower than second one, false otherwise.
	 */
	public static final IComparisonOperator LESS = ((s1,s2)->s1.compareTo(s2) < 0);
	/**
	 * Returns true if the first string is lexicographically lower or equal than second one, false otherwise.
	 */
	public static final IComparisonOperator LESS_OR_EQUALS = ((s1,s2)->s1.compareTo(s2) <= 0);
	/**
	 * Returns true if the first string is lexicographically greater than second one, false otherwise.
	 */
	public static final IComparisonOperator GREATER = ((s1,s2)->s1.compareTo(s2) > 0);
	/**
	 * Returns true if the first string is lexicographically greater or equal than second one, false otherwise.
	 */
	public static final IComparisonOperator GREATER_OR_EQUALS = ((s1,s2)->s1.compareTo(s2) >= 0); 
	/**
	 * Returns true if the first string is lexicographically equals to  second one, false otherwise.
	 */
	public static final IComparisonOperator EQUALS = ((s1,s2)->s1.compareTo(s2) == 0);
	/**
	 * Returns true if the first string is lexicographically doesn't equal to the second one, false otherwise.
	 */
	public static final IComparisonOperator NOT_EQUALS = ((s1,s2)->s1.compareTo(s2) != 0);
	/**
	 * Returns true if the second string can be transformed into first string, false otherwise.
	 * Like operator will search for star char and replace star char with the possible match in
	 * the first string. If it fails it returns false, otherwise match succeed and return 
	 * value is true.
	 * <h3> Examples </h3>
	 * <ul>"Mirko" "Mi*" -> True</ul>
	 * <ul> "Darko" "Mi*" -> False</ul>
	 * <ul> "kreso" "kre*o" -> True</ul>
	 * <ul> "Mirko" "Mir*ko" -> True</ul>
	 */
	public static final IComparisonOperator LIKE = new IComparisonOperator() {
		
		@Override
		public boolean satisfied(String value1, String value2) {
			int numOfStar = 0;
			
			for(int i=0, len = value2.length(); i < len; ++i) {
				if(value2.charAt(i) == '*') {
					++numOfStar;
				}
			}
			if(numOfStar > 1) {
				throw new LikeOperatorException("More than one star.");
			}
			
			for(int index1 = value1.length()-1, index2 = value2.length()-1; index2>=0 && index1 >= 0 && value2.charAt(index2)!='*';--index1,--index2) {
				if(value1.charAt(index1) != value2.charAt(index2)) {
					return false;
				}
			}
			for(int index1 = 0, index2 = 0; index2<value2.length() && index1 < value1.length() && value2.charAt(index2)!='*';++index1,++index2) {
				if(value1.charAt(index1) != value2.charAt(index2)) {
					return false;
				}
			}
			if(value1.length() < value2.length() - numOfStar) return false;
			
			return true;
		}
	};
	/**
	 * Enum representation of possible operators.
	 * @author dario
	 *
	 */
	public enum Enum {
		/**
		 * less operator
		 */
		LESS ("<"),
		/**
		 * less or equal operator
		 */
		LESS_OR_EQUAL ("<="),
		/**
		 * greater operator
		 */
		GREATER (">"),
		/**
		 * greater or equal operator
		 */
		GREATER_OR_EQUAL (">="),
		/**
		 * equal operator
		 */
		EQUALS ("="),
		/**
		 * non equal operator
		 */
		NOT_EQUALS ("!="),
		/**
		 * like operator
		 */
		LIKE ("LIKE");
		/**
		 * string reprsentation of enum
		 */
		public final String value;
		/**
		 * Enum constructor with string as value.
		 * @param value
		 */
		private Enum(String value) {
			this.value = value;
		}
		@Override
		public String toString() {
			return value;
		}
		/**
		 * Returns {@link Enum} from string value.
		 * @param value
		 * @return Enum representation of string
		 */
		public static Enum fromString(String value) {
			for(Enum E : Enum.values()) {
				if(E.value.compareTo(value) == 0) {
					return E;
				}
			}
			return null;
		}
	}
}
