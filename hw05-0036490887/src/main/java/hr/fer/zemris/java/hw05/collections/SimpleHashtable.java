package hr.fer.zemris.java.hw05.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * {@link SimpleHashtable} offers you insertion, deleting, and updating in <sup>O(1)</sup>. 
 * It can be iterated so it offers method {@link #iterator()}.
 * Keys can't be null reference, but value can be null reference.
 * @author dario
 * 
 * @version 1.0
 * @since 1.0
 *
 * @param <K> parameterized key
 * @param <V> parameterized value
 */

public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K,V>>{

	/**
	 * array of slot in the hash collection
	 */
	private TableEntry<K, V>[] table;
	/**
	 * current number of pairs in the hash collection
	 */
	private int size;
	/**
	 * number of modifications made in hash table
	 */
	private int modificationCount;
	
	/**
	 * initial capacity of slots in hash collection
	 */
	private final static int INIT_CAPACITY = 16;
	/**
	 * overflow constant
	 */
	private final double OVERFLOW_CONSTANT = 0.75;
	
	
	
	/**
	 * Creats {@link SimpleHashtable} with 16 slots.
	 */
	public SimpleHashtable() {
		this(INIT_CAPACITY);
	}

	/**
	 * 
	 * @param capacity
	 * @throws IllegalArgumentException
	 *             capacity must be greater than 0
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int capacity) {

		if (capacity <= 0) {
			throw new IllegalArgumentException("Capacity in SimpleHashTable must be greater than 0.");
		}
		int newCapacity = 1;
		for (;newCapacity < capacity; newCapacity *= 2);

		this.table = new TableEntry[newCapacity];
		this.size = 0;
		this.modificationCount = 0;
	}

	/**
	 * Method put insert the new entry if the key value is not already present in
	 * the {@link SimpleHashtable}. If is already present, it simple updates the
	 * value property. Otherwise it makes new {@link TableEntry} with key and value
	 * and inserts to the {@link SimpleHashtable}.
	 * 
	 * @param key
	 *            - key value of the entry
	 * @param value
	 *            value of the entry
	 * @throw {@link NullPointerException} key can't be null, value can be
	 */
	public void put(K key, V value) {
		if (key == null)
			throw new NullPointerException("Key must not be null.");
		
		int slot = getSlot(key);
		TableEntry<K, V> entry = table[slot];
		
		while(entry != null) {
			
			if(entry.key.equals(key)) {
				entry.value = value;
				return;
			}
			if(entry.next == null) {
				entry.next = new TableEntry<>(key, value, null);
				++size;
				++modificationCount;
				checkSize();
				return;
			}
			entry = entry.next;
		}
		table[slot] = new TableEntry<K, V>(key,value,null);
		++size;
		++modificationCount;
		checkSize();
		return;
	}
	/**
	 * Puts the {@link TableEntry} with given key and value in the given table.
	 * This will be used during rehashing, so attribute size will not be changed. 
	 * @param key key of the entry
	 * @param value value of the entry
	 * @param table table in which we will added entry
	 */
	private void put(K key, V value, TableEntry<K, V>[] table) {
		
		if (key == null)
			throw new IllegalArgumentException("Key must not be null.");
		
		int slot = getSlot(key, table);
		TableEntry<K, V> entry = table[slot];
		
		while(entry != null) {
			
			if(entry.key.equals(key)) {
				entry.value = value;
				return;
			}
			if(entry.next == null) {
				entry.next = new TableEntry<>(key, value, null);
			
				return;
			}
			entry = entry.next;
		}
		table[slot] = new TableEntry<K, V>(key,value,null);
		return;
		
	}
	/**
	 * Checks if the number of entries in the {@link SimpleHashtable} is higher than {@link #OVERFLOW_CONSTANT} times number of slots.
	 * If it is, then number of slots is doubled and everything is rehashed.
	 */
	@SuppressWarnings("unchecked")
	private void checkSize() {
		
		if(size() * OVERFLOW_CONSTANT > table.length) {
			//increase capacity by 2
			++modificationCount;
			int newCapacity = table.length * 2;
			
			TableEntry<K, V>[] newTable = new TableEntry[newCapacity];
			
			
			for(TableEntry<K, V> entry : this) {
				put(entry.getKey(), entry.getValue(), newTable);
			}
			
			this.table = newTable;
		}
		
	}
	/**
	 * It returns the value in the {@link SimpleHashtable} which was added together
	 * with the present key value. It <b>returns null</b> if there is no such value
	 * with that key present.
	 * 
	 * @param key
	 *            - {@link Object} with which we are trying to find value in table
	 * @return value with that key in the {@link SimpleHashtable}
	 */
	public V get(Object key) {
		
		if(key == null) return null;
		
		int slot = getSlot(key);
		TableEntry<K, V> entry = table[slot];
		
		while(entry != null) {
			if(entry.key.equals(key)) {
				return entry.value;
			}
			entry = entry.next;
		}
		return null;
	}

	/**
	 * Returns the number of the {@link TableEntry} in the {@link SimpleHashtable}.
	 * 
	 * @return int - number of entries in the hash table
	 */
	public int size() {
		return this.size;
	}

	/**
	 * Returns true if the key is already present in the {@link SimpleHashtable}. 
	 * Otherwise it will return false.
	 * @param key which we are trying to find in the hash table
	 *            , can be null
	 * @return true if the key is in the {@link SimpleHashtable}, false otherwise
	 */
	public boolean containsKey(Object key) {
		if(key == null) return false;
		
		int slot = getSlot(key);
		TableEntry<K, V> entry = table[slot];
		while(entry != null) {
			if(entry.key.equals(key)) return true;
			entry = entry.next;
		}
		
		return false;
	}

	/**
	 * Returns true if the value is present in the {@link SimpleHashtable}. It doesn't 
	 * matter with which key is in pair, it only looks if there is value in the {@link SimpleHashtable}.
	 * @param value  which we want to find in the hash table
	 *            , can be null
	 * @return true if the value is present in the hashtable, false otherwise
	 */
	public boolean containsValue(Object value) {
		
		for (int slot = 0; slot < table.length; ++slot) {

			TableEntry<K, V> entry = table[slot];
			
			while (entry != null) {
				if(entry.getValue() == null && value == null) return true;
				if(entry.getValue() == null) {
					entry = entry.next;
					continue;
				}
				if(entry.getValue().equals(value)) {
					return true;
				}
				entry = entry.next;
			}
		}
		return false;
	}
	/**
	 * Removes the pair with the given key. If there is no such element, method
	 * will do nothing.
	 * @param key
	 */
	public void remove(Object key) {
		if(key == null) return;
		
		int slot = getSlot(key);
		TableEntry<K, V> entry = table[slot];
		TableEntry<K, V> previous = null;
		
		while(entry != null) {
			if(entry.key.equals(key)) {
				--size;
				++modificationCount;
				if(previous == null) {
					//head of the slot
					table[slot] = entry.next;
				} else {
					previous.next = entry.next;
				}
			}
			previous = entry;
			entry = entry.next;
		}
		
		return;
	}

	/**
	 * Returns true if there is not {@link TableEntry} in the
	 * {@link SimpleHashtable}, false otherwise.
	 * 
	 * @return true if there is no pairs in the hashTable, false otherwise
	 * @see {@link #size()}
	 */
	public boolean isEmpty() {
		return size() == 0;
	}
	/**
	 * Returns the number of the slot on which position the key can or is already insert.
	 * It calculates the hashcode of the key and returns the module of the avaliable slots.
	 * @param key - key of which we want a slot
	 * @return number of the slot
	 */
	private int getSlot(Object key) {
		return getSlot(key, table);
	}
	/**
	 * Returns the slot of the key in the given table.
	 * @param key key of which we want slot
	 * @param table table in which we will find slot, only important length of it
	 * @return number of slot for key in the given table
	 */
	private int getSlot(Object key, TableEntry<K, V>[] table) {
		int slot = key.hashCode() % table.length;
		while(slot < 0) slot += table.length;
		return slot;
	}
	
	/**
	 * It returns the {@link String} representation of the {@link SimpleHashtable}.
	 * They will be printed from first slot to the last one, and from the first entry in the slot to 
	 * the last one.
	 * @return {@link String} representation of the {@link SimpleHashtable}
	 */
	@Override
	public String toString() {

		StringBuilder builder = new StringBuilder("[");
		boolean firstEntry = true;
		for (int slot = 0; slot < table.length; ++slot) {

			TableEntry<K, V> entry = table[slot];
			if (entry == null)
				continue;

			while (entry != null) {
				if (!firstEntry)
					builder.append(",");
				builder.append(entry.toString()+ "= "+slot + "  ");
			
				firstEntry = false;
				entry = entry.next;
			}
		}

		builder.append("]");
		return builder.toString();
	}
	/**
	 * It will erase all elements in the {@link SimpleHashtable} but it will not
	 * change the capacity of it.
	 */
	public void clear() {
		for(int i=0;i<table.length;++i) {
			table[i] = null;
		}
		size = 0;
	}
	/**
	 * Class {@link TableEntry} pairs key and value object together so that 
	 * they can be stored in {@link SimpleHashtable}. It also has {@link #next} as attribute, when 
	 * there is many object with the same hash value.
	 * @author dario
	 *
	 * @param <K> - parameter value of key
	 * @param <V> - parameter value of value
	 */
	public static class TableEntry<K, V> {
		/**
		 * key of the table entry
		 */
		private K key;
		/**
		 * value of the table entry
		 */
		private V value;
		/**
		 * Next entry of the current one in the linked list
		 */
		private TableEntry<K, V> next;
		
		/**
		 * Constructs new {@link TableEntry} with key, value and next one in the list. 
		 * @param key key of the object
		 * @param value value of the object
		 * @param next next entry in the linked list, usually it is null, because it will be added later
		 * @throws NullPointerException key can't be null
		 */
		public TableEntry(K key, V value, TableEntry<K, V> next) {
			if(key == null) throw new NullPointerException("Key can't be null.");
			this.key = key;
			this.value = value;
			this.next = next;
		}
		
		/**
		 * Returns the key value of the {@link TableEntry}
		 * 
		 * @return key value of the {@link TableEntry} which is parameterized with K
		 */
		public K getKey() {
			return this.key;
		}

		/**
		 * Returns the value of the {@link TableEntry}
		 * 
		 * @return value of the {@link TableEntry} which is parameterized with V
		 */
		public V getValue() {
			return this.value;
		}

		/**
		 * Sets the value properity of the {@link TableEntry}
		 * 
		 * @param value
		 *            - new value of the {@link TableEntry}
		 */
		public void setValue(V value) {
			this.value = value;
		}
		/**
		 * Returns string representation of the {@link TableEntry}.
		 * Format which is used inside is "key value".  
		 * @return String string value
		 */
		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append(key.toString());
			builder.append(" ");
			if (value == null) {
				builder.append("null");
			} else {
				builder.append(value.toString());
			}
			return builder.toString();
		}
	}

	@Override
	public Iterator<TableEntry<K, V>> iterator() {
		return new IteratorImpl();
	}
	/**
	 * An iterator over a collection of {@link SimpleHashtable}. 
	 * It offers method like {@link #remove()} , {@link #next()} and {@link #hasNext()}
	 * @author dario
	 *
	 */
	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K, V>> {

		/**
		 * current slot position
		 */
		private int slotPosition = -1;
		/**
		 * current {@link TableEntry}
		 */
		private TableEntry<K, V> entry = null;
		/**
		 * how many did we visited
		 */
		private int cnt = 0;
		/**
		 * size of the hash table
		 */
		private int size = SimpleHashtable.this.size;
		/**
		 * modification counter, needed for {@link ConcurrentModificationException}
		 */
		private int initModification = modificationCount;
		/**
		 * Returns true if the iteration has more elements. (In other words, 
		 * returns true if next() would return an element rather than throwing an exception.)
		 * @return true if the iteration has more elements
		 */
		@Override
		public boolean hasNext() {
			checkConcurrent();
			
			return cnt < size;
		}
		/**
		 * Returns the next element in the iteration.
		 * @return the next element in the iteration
		 * @throws NoSuchElementException
		 */
		@Override
		public TableEntry<K, V> next() {
			checkConcurrent();
			if(entry == null || entry.next == null) {
				++slotPosition;
				while(slotPosition < table.length  && table[slotPosition] == null) {
					slotPosition++;
				}
				if(slotPosition >= table.length) throw new NoSuchElementException();
				
				entry = table[slotPosition];
				++cnt;
				return entry;
			}
			entry = entry.next;
			++cnt;
			return entry;
		}
		/**
		 * Removes the current elements from the list. It can be called only once per element.
		 */
		public void remove() {
			checkConcurrent();
			if(entry == null) throw new IllegalStateException("In remove simple hashTable iterator");
			int slot = getSlot(entry.key);
			TableEntry<K, V> entryInTable = table[slot];
			TableEntry<K, V> previous = null;
			
			while(entryInTable != null) {
				if(entryInTable.key.equals(entry.key)) {
					SimpleHashtable.this.size--;
					++modificationCount;
					if(previous == null) {
						//head of the slot
						table[slot] = entryInTable.next;
					} else {
						previous.next = entryInTable.next;
					}
					initModification++;
					return;
				}
				previous = entryInTable;
				entryInTable = entryInTable.next;
			}
			throw new IllegalStateException("In remove simple hashTable iterator");
			
			//entry = null;
		}
		/**
		 * It checks if the {@link SimpleHashtable} was changed during the iterations. 
		 * If it is, it will throw new {@link ConcurrentModificationException}.
		 */
		private void checkConcurrent() {
			if(initModification != modificationCount)
				throw new ConcurrentModificationException("Concurrent exception in simpleHashTable.");
		}
	}
}
