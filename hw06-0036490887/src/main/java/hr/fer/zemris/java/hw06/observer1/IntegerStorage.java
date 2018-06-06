package hr.fer.zemris.java.hw06.observer1;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw06.observer1.IntegerStorageObserver;

/**
 * Class {@link IntegerStorage} allows you to
 * {@link #addObserver(IntegerStorageObserver) add observers} or to
 * {@link #removeObserver(IntegerStorageObserver)remove them}. You can set the
 * int value of this class in the {@link #IntegerStorage(int) constructor} or by
 * calling the method {@link #setValue(int)}. By calling method set, all
 * registered observers will be notified if the value has been changed.
 * 
 * @author dario
 *
 */
public class IntegerStorage {

	/**
	 * integer value of IntegerStorage
	 */
	private int value;
	/**
	 * list of observers to the this instance of IntegerStorage
	 */
	private List<IntegerStorageObserver> observers;

	/**
	 * Constructs {@link IntegerStorage} with the initial value and sets up the list
	 * of observers on this object.
	 * 
	 * @param initialValue
	 *            initial value of the object
	 */
	public IntegerStorage(int initialValue) {
		this.value = initialValue;
		this.observers = new ArrayList<>();
	}

	/**
	 * Adds observer to this instance of object. If the given observer is already
	 * observing this object, it will not be added to the list.
	 * 
	 * @param observer
	 *            {@link IntegerStorageObserver} observer
	 * @throw {@link NullPointerException} Observer can't be null
	 */
	public void addObserver(IntegerStorageObserver observer) {
		if (observer == null)
			throw new NullPointerException("Observer can't be null.");
		if (!observers.contains(observer)) {
			observers.add(observer);
		}
	}

	/**
	 * Removes the observer from the {@link #observers list of observers}. If the
	 * object is not the observer, nothing will happen to the list.
	 * 
	 * @param observer
	 *            {@link IntegerStorageObserver} observer
	 */
	public void removeObserver(IntegerStorageObserver observer) {
		if (observer == null)
			throw new NullPointerException("Observer can't be null.");
		this.observers = new ArrayList<>(observers);
		observers.remove(observer);
	}

	/**
	 * Removes all observers on this object.
	 */
	public void clearObservers() {
		observers.clear();
	}

	/**
	 * Returns the value of the {@link IntegerStorage}.
	 * 
	 * @return value of the object
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Sets new value and notify all observers if the new value is different from
	 * the old one.
	 * 
	 * @param value
	 *            new value
	 */
	public void setValue(int value) {
		// Only if new value is different than the current value:
		if (this.value != value) {
			// Update current value
			this.value = value;
			// Notify all registered observers
			if (observers != null) {

				for (IntegerStorageObserver observer : observers) {
					observer.valueChanged(this);
				}
			}
		}
	}

	/**
	 * Returns the list of the
	 * {@link hr.fer.zemris.java.hw06.observer1.IntegerStorageObserver} that are
	 * registered on modification of the subject's state
	 * 
	 * @return list of observers
	 */
	public List<IntegerStorageObserver> getObservers() {
		return this.observers;
	}
}
