package hr.fer.zemris.java.hw06.observer2;

/**
 * Class {@link IntegerStorageChange} stores previous and 
 * current state of the {@link IntegerStorage}. 
 * 
 * 
 * @see IntegerStorage
 * @author dario
 *
 */
public class IntegerStorageChange {
	
	/**
	 * current subject states 
	 */
	private IntegerStorage storage;
	/**
	 * previously value of {@link IntegerStorage} storage
	 */
	private int previousValue;
	/**
	 * Constructs {@link IntegerStorageChange} with the current storage and
	 * value which was there previously. 
	 * @param storage current subject's state
	 * @param previousValue previously value
	 */
	public IntegerStorageChange(IntegerStorage storage, int previousValue) {
		this.storage = storage;
		this.previousValue = previousValue;
	}
	/**
	 * Returns {@link IntegerStorage}, or new subject state.
	 * @return observed subject state
	 */
	public IntegerStorage getStorage() {
		return this.storage;
	}
	/**
	 * Returns value that was previously, or before subject's update.
	 * @return previously value
	 */
	public int getPreviousValue() {
		return this.previousValue;
	}
	/**
	 * Returns new value of the {@link IntegerStorageChange} value.
	 * @return new value after subject's update
	 */
	public int getNewValue() {
		return this.storage.getValue();
	}
}
