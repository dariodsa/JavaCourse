package hr.fer.zemris.java.hw05.db;


/**
 * Contains static final variable which implements {@link IFieldValueGetter}
 * from every returns specific attribute from {@link StudentRecord}
 * @author dario
 *
 */
public class FieldValueGetters {
	/**
	 * Returns {@link StudentRecord#getFirstName()} of the given {@link StudentRecord}
	 */
	public static final IFieldValueGetter FIRST_NAME = ((t)->t.getFirstName());
	/**
	 * Returns {@link StudentRecord#getLastName()} of the given {@link StudentRecord}
	 */
	public static final IFieldValueGetter LAST_NAME = ((t)->t.getLastName());
	/**
	 * Returns {@link StudentRecord#getJmbag()} of the given {@link StudentRecord}
	 */
	public static final IFieldValueGetter JMBAG = ((t)->t.getJmbag());
	/**
	 * Enum representation of possible variable.
	 * @author dario
	 *
	 */
	public enum Enum {
		/**
		 * firstName variable
		 */
		FIRST_NAME ("firstName"),
		/**
		 * lastName variable
		 */
		LAST_NAME ("lastName"),
		/**
		 * jmbag variable
		 */
		JMBAG ("jmbag"),
		/**
		 * unknown variable
		 */
		UNKNOWN("");
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
			return Enum.UNKNOWN;
		}
	}
}
