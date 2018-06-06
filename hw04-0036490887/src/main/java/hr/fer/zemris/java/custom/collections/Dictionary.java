package hr.fer.zemris.java.custom.collections;

/**
 * Class {@link Dictionary} offers you to insert new {@link DictonaryEntry} to
 * the {@link Dictionary} by method {@link #put(Object, Object)}, or to get by
 * their key value with {@link #get(Object)}. You can see size of the collection
 * with {@link #size()} or {@link #isEmpty()}.
 * 
 * @author dario
 *
 */
public class Dictionary {
	/**
	 * Array in which the {@link DictonaryEntry} will be inserted
	 */
	private ArrayIndexedCollection array;

	/**
	 * It constructs the new {@link Dictionary} by initi the
	 * {@link ArrayIndexedCollection} as the private atribute method.
	 */
	public Dictionary() {
		this.array = new ArrayIndexedCollection();
	}

	/**
	 * Returns true if the {@link Dictionary} is empty or if the {@link #size()} is
	 * equal to 0.
	 * 
	 * @return true if the {@link Dictionary} is empty, otherwise false
	 * @see {@link #size()}
	 */
	public boolean isEmpty() {
		return array.isEmpty();
	}

	/**
	 * Returns the number of the entries in the dictonary.
	 * 
	 * @return number of entries in the dictionary
	 */
	public int size() {
		return array.size();
	}

	/**
	 * It sets the size to null and removes the pointer from the array so that it
	 * can be deleted by the machine
	 */
	public void clear() {
		array.clear();
	}

	/**
	 * It adds the new entry to the {@link Dictionary}. It will create
	 * {@link DictonaryEntry} with the given parameters key and value. If there is
	 * entry wuth the same key, the value will be overwritten and the size will not
	 * be changed.
	 * 
	 * @param key
	 *            {@link Object} key value of the entry
	 * @param value
	 *            {@link Object} value of the entry
	 */
	public void put(Object key, Object value) {
		if (key == null)
			throw new IllegalArgumentException("Key can't be null in put method.");
		if (value == null)
			throw new IllegalArgumentException("Value can't be null in put method.");
		for (int i = 0, len = array.size(); i < len; ++i) {
			DictonaryEntry entry = (DictonaryEntry) array.get(i);
			if (entry.getKey().equals(key)) {
				entry.setValue(value);
				return;
			}
		}
		array.add(new DictonaryEntry(key, value));
	}

	/**
	 * It will return the {@link Object} value of the entry which was added together
	 * with the given key in the {@link Dictionary}. <b>It can return null</b> if
	 * there is no such value with the given key.
	 * 
	 * @param key
	 *            - key value of the entry
	 * @return value will was added together with the key in the dictionary
	 */
	public Object get(Object key) {
		if (key == null)
			throw new IllegalArgumentException("I can't search in get method, with null as key.");
		for (int i = 0, len = array.size(); i < len; ++i) {
			DictonaryEntry entry = (DictonaryEntry) array.get(i);
			if (entry.getKey().equals(key)) {
				return entry.getValue();
			}
		}
		return null;
	}

	/**
	 * Class {@link DictonaryEntry} will be used as the entries in the class
	 * {@link Dictionary}. It will be added in dictionary and also updated. You can
	 * generate new {@link DictonaryEntry} with constructor
	 * {@link #Dictionary(Object, Object)}, and be careful, value can be null, but
	 * key musn't be.
	 * 
	 * @author dario
	 *
	 */
	private static class DictonaryEntry {
		/**
		 * key value of the {@link DictonaryEntry}
		 */
		private Object key;
		/**
		 * value of the {@link DictonaryEntry}
		 */
		private Object value;

		/**
		 * It constructs the new {@link DictonaryEntry} object with the given parameters
		 * key and value. Key can't be null, <b>but value can be null</b>.
		 * 
		 * @param key
		 *            of the {@link DictonaryEntry}
		 * @param value
		 *            value of the {@link DictonaryEntry}
		 */
		public DictonaryEntry(Object key, Object value) {
			if (key == null)
				throw new IllegalArgumentException("You can't create entry with null as the key.");
			this.key = key;
			this.value = value;
		}

		/**
		 * It returns the value of the {@link DictonaryEntry}
		 * 
		 * @return {@link Object} value of the entry
		 */
		public Object getValue() {
			return value;
		}

		/**
		 * It sets the new value of the {@link DictonaryEntry}.
		 * 
		 * @param value
		 *            - new value of the {@link DictionaryEntry}
		 */
		public void setValue(Object value) {
			this.value = value;
		}

		/**
		 * It returns the key of the {@link DictonaryEntry}
		 * 
		 * @return {@link Object} key value of the entry
		 */
		public Object getKey() {
			return key;
		}

	}
}
