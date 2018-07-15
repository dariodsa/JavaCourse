package hr.fer.zemris.java.hw06.observer1;

/**
 * Interface {@link IntegerStorageObserver} specifies which method, will subject
 * call when he changes its state.
 * 
 * @author dario
 *
 */
public interface IntegerStorageObserver {
	/**
	 * Method which will be called when the subject changes his state.
	 * 
	 * @param istorage
	 *            reference to the new state
	 */
	public void valueChanged(IntegerStorage istorage);
}
