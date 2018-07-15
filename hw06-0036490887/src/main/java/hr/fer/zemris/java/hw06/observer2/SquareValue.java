package hr.fer.zemris.java.hw06.observer2;


/**
 * Class {@link SquareValue} squares the number given in the method
 * {@link #valueChanged(IntegerStorage) valueChanged} and prints in on the
 * standard output stream.
 * 
 * @author dario
 *
 */
public class SquareValue implements IntegerStorageObserver{

	/**
	 * Prints squared value of the new subject state to the standard output.
	 */
	@Override
	public void valueChanged(IntegerStorageChange istorage) {
		System.out.printf("Provided new value: %d, square is %d%n",
				istorage.getNewValue(),
				istorage.getNewValue()*istorage.getNewValue());
	}
	
}
