package hr.fer.zemris.java.hw06.observer1;

/**
 * Class {@link SquareValue} squares the number given in the method
 * {@link #valueChanged(IntegerStorage) valueChanged} and prints in on the
 * standard output stream.
 * 
 * @author dario
 *
 */
public class SquareValue implements IntegerStorageObserver {

	/**
	 * Prints squared value of the new subject state to the standard output.
	 */
	@Override
	public void valueChanged(IntegerStorage istorage) {
		System.out.printf("Provided new value: %d, square is %d%n", istorage.getValue(),
				istorage.getValue() * istorage.getValue());
	}

}
