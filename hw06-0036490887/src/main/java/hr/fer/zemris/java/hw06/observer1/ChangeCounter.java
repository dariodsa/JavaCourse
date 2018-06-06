package hr.fer.zemris.java.hw06.observer1;

/**
 * Class {@link ChangeCounter} observers object and when that object is changed
 * it prints to the standard output number of the modifications.
 * 
 * @author dario
 *
 */
public class ChangeCounter implements IntegerStorageObserver {

	/**
	 * number of modifications made on the object that I observe
	 */
	private int modificationCount = 0;

	/**
	 * Prints how many time subject has been change his state.
	 */
	@Override
	public void valueChanged(IntegerStorage istorage) {
		System.out.printf("Number of value changes since tracking: %d%n", ++modificationCount);
	}

}
