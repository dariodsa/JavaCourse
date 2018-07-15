package hr.fer.zemris.java.custom.scripting.exec;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Class {@link ObjectMultistack} consists of basic stack operations like
 * {@link #push(String, ValueWrapper)}, {@link #pop(String)},
 * {@link #isEmpty(String)} and others. Basic different from other stack is that
 * this class can contains many stack, in which every stack is registered under
 * specific name and you can pop, push or peek from that specific stack.
 * 
 * @author dario
 * @version 1.0
 * @since 1.0
 */
public class ObjectMultistack {

	/**
	 * map of the String and {@link MultistackEntry} which means that for specific
	 * String there exist a {@link MultistackEntry}
	 */
	private Map<String, MultistackEntry> map = new HashMap<>();

	/**
	 * Push the new {@link MultistackEntry} in the stack under given name. The new
	 * entry consists of the value that is given under the arguments.
	 * 
	 * @param name
	 *            name of the stack
	 * @param valueWrapper
	 *            value that will be push on the stack
	 */
	public void push(String name, ValueWrapper valueWrapper) {
		if (!map.containsKey(name)) {
			map.put(name, new MultistackEntry(valueWrapper, null));
		} else {
			MultistackEntry peek = map.get(name);
			map.put(name, new MultistackEntry(valueWrapper, peek));
		}
	}

	/**
	 * Pops the {@link ValueWrapper} from the peek of the stack with the given name.
	 * 
	 * @param name
	 *            name of the specific stack
	 * @return {@link ValueWrapper} peek's value
	 * @throws NoSuchElementException
	 *             if the stack is empty.
	 */
	public ValueWrapper pop(String name) {
		if (!map.containsKey(name))
			throw new NoSuchElementException("Stack is empty.");
		MultistackEntry peek = map.get(name);
		map.put(name, peek.getNext());
		return peek.getValue();
	}

	/**
	 * Returns the peek of the stack that is registered under specified name
	 * 
	 * @param name
	 *            name of the stack
	 * @return peek of the stack with the given name
	 */
	public ValueWrapper peek(String name) {
		return map.get(name).getValue();
	}

	/**
	 * Returns true if the stack under given name is empty, false otherwise.
	 * 
	 * @param name
	 *            under which name is the stack registered
	 * @return true if the stack is empty, false otherwise
	 */
	public boolean isEmpty(String name) {
		if (!map.containsKey(name))
			return true;
		if (map.get(name) == null)
			return true;
		return false;
	}

	/**
	 * Static class {@link MultistackEntry} encapsulate one entry in the stack.
	 * Class has value as attribute and pointer to the next element in the linked
	 * list.
	 * 
	 * @author dario
	 *
	 */
	static class MultistackEntry {

		/**
		 * value of the object
		 */
		private ValueWrapper value;
		/**
		 * next {@link MultistackEntry} in the single linked list
		 */
		private MultistackEntry next;

		/**
		 * Constructs {@link MultistackEntry} with the value and with next element which
		 * should be after this on in the list. Both value and next can be null.
		 * 
		 * @param value
		 *            value of the object in the linked list
		 * @param next
		 *            next entry in the single linked list
		 */
		public MultistackEntry(ValueWrapper value, MultistackEntry next) {
			this.value = value;
			this.next = next;
		}

		/**
		 * Returns {@link MultistackEntry} value.
		 * 
		 * @return value of the object
		 */
		public ValueWrapper getValue() {
			return this.value;
		}

		/**
		 * Returns {@link MultistackEntry} next attribute.
		 * 
		 * @return next {@link MultistackEntry} in the linked list
		 */
		public MultistackEntry getNext() {
			return this.next;
		}
	}
}