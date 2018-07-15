package hr.fer.zemris.java.hw06.observer1;

/**
 * {@link ObserverExample} is example class with main method that runs example
 * task.
 * 
 * @author dario
 *
 */
public class ObserverExample {
	/**
	 * Method main doesn't take any args, it only runs example from the task
	 * instructions.
	 * 
	 * @param args
	 *            not used
	 */
	public static void main(String[] args) {
		IntegerStorage istorage = new IntegerStorage(20);

		IntegerStorageObserver observer = new SquareValue();

		istorage.addObserver(observer);
		istorage.setValue(5);
		istorage.setValue(2);
		istorage.setValue(25);

		// istorage.removeObserver(observer);

		// istorage.addObserver(new ChangeCounter());
		// istorage.addObserver(new DoubleValue(5));
		istorage.removeObserver(observer);
		istorage.addObserver(new ChangeCounter());
		istorage.addObserver(new DoubleValue(1));
		istorage.addObserver(new DoubleValue(2));
		istorage.addObserver(new DoubleValue(2));

		istorage.setValue(13);
		istorage.setValue(22);
		istorage.setValue(15);
	}
}